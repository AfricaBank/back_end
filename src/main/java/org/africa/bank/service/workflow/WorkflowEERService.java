package org.africa.bank.service.workflow;

import jakarta.annotation.PostConstruct;
import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.constants.StatutDossier;
import org.africa.bank.entity.*;
import org.africa.bank.repository.DossierEERRepository;
import org.africa.bank.service.TiersService;
import org.africa.bank.service.PersonneLPhysiqueService;
import org.africa.bank.service.PersonneLMService;
import org.africa.bank.service.validation.ValidationEERService;
import org.africa.bank.service.workflow.states.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class WorkflowEERService {

    @Autowired
    private DossierEERRepository dossierRepository;


    @Autowired
    private TiersService tiersService;

    @Autowired
    private PersonneLPhysiqueService personnePhysiqueService;

    @Autowired
    private PersonneLMService personneLMService;

    @Autowired
    private ValidationEERService validationService;

    @Autowired
    private ApplicationContext context;

    private Map<EtapeProcessus, EtapeState> stateRegistry = new HashMap<>();

    @PostConstruct
    public void init() {
        // Initialiser les états du workflow
        stateRegistry.put(EtapeProcessus.INITIATION,
                context.getBean(InitiationState.class));
        stateRegistry.put(EtapeProcessus.RECHERCHE_PERSONNE,
                context.getBean(RecherchePersonneState.class));
        stateRegistry.put(EtapeProcessus.AJOUT_TITULAIRE,
                context.getBean(AjoutTitulaireState.class));
        stateRegistry.put(EtapeProcessus.AJOUT_PERSONNES_LIEES,
                context.getBean(AjoutPersonnesLieesState.class));
        stateRegistry.put(EtapeProcessus.TERMINE,
                context.getBean(TermineState.class));
    }

    // ==================== GESTION DU WORKFLOW ====================

    @Transactional
    public DossierEER initierDossier(Map<String, Object> params) {
        EtapeState etatInitiation = stateRegistry.get(EtapeProcessus.INITIATION);
        DossierEER dossier = etatInitiation.initierDossier(params);
        return dossierRepository.save(dossier);
    }

    @Transactional
    public List<Tiers> rechercherTiers(Long dossierId, Map<String, Object> criteres) {
        DossierEER dossier = getDossierValide(dossierId, EtapeProcessus.RECHERCHE_PERSONNE);
        EtapeState etatActuel = stateRegistry.get(dossier.getEtapeActuelle());

        // Utiliser le service Tiers pour la recherche
        List<Tiers> resultats = tiersService.searchEntities(criteres);

        // Enregistrer l'action dans l'historique
        if (!resultats.isEmpty()) {
            HistoriqueEtape historique = new HistoriqueEtape();
            historique.setEtape(EtapeProcessus.RECHERCHE_PERSONNE);
            historique.setDatePassage(LocalDateTime.now());
            historique.setUtilisateur(dossier.getCreateur());
            historique.setCommentaire("Recherche effectuée - " + resultats.size() + " résultats");
            historique.setActionEffectuee("RECHERCHE_TIERS");
            historique.setDossierEER(dossier);
            dossier.getHistoriqueEtapes().add(historique);
            dossierRepository.save(dossier);
        }

        return resultats;
    }

    @Transactional
    public DossierEER ajouterTitulaire(Long dossierId, Long tiersId, boolean estCoTitulaire) {
        DossierEER dossier = getDossierValide(dossierId, EtapeProcessus.AJOUT_TITULAIRE);
        Tiers tiers = tiersService.getEntityById(tiersId);

        // Validation métier avant ajout
        validationService.validerAjoutTitulaire(dossier, tiers, estCoTitulaire);

        EtapeState etatActuel = stateRegistry.get(dossier.getEtapeActuelle());
        dossier = etatActuel.ajouterTitulaire(dossier, tiers, estCoTitulaire);
        dossier.setDateModification(LocalDateTime.now());

        return dossierRepository.save(dossier);
    }

    @Transactional
    public DossierEER ajouterPersonnePhysique(Long dossierId, PersonneLPhysique personne) {
        DossierEER dossier = getDossierValide(dossierId, EtapeProcessus.AJOUT_PERSONNES_LIEES);

        // Vérifier que le dossier a un titulaire
        if (!dossier.aUnTitulaire()) {
            throw new RuntimeException("Le dossier doit avoir au moins un titulaire avant d'ajouter des personnes liées");
        }

        EtapeState etatActuel = stateRegistry.get(dossier.getEtapeActuelle());

        // Lier la personne au dossier
        personne.setDossierEER(dossier);
        dossier = etatActuel.ajouterPersonnePhysique(dossier, personne);
        dossier.setDateModification(LocalDateTime.now());

        // Sauvegarder la personne via le service
        personnePhysiqueService.createPersonne(personnePhysiqueService.convertToDTO(personne));
        return dossierRepository.save(dossier);
    }

    @Transactional
    public DossierEER ajouterPersonneMorale(Long dossierId, PersonneLM personne) {
        DossierEER dossier = getDossierValide(dossierId, EtapeProcessus.AJOUT_PERSONNES_LIEES);

        // Vérifier que le dossier a un titulaire
        if (!dossier.aUnTitulaire()) {
            throw new RuntimeException("Le dossier doit avoir au moins un titulaire avant d'ajouter des personnes morales");
        }

        EtapeState etatActuel = stateRegistry.get(dossier.getEtapeActuelle());

        // Lier la personne morale au dossier
        personne.setDossierEER(dossier);
        dossier = etatActuel.ajouterPersonneMorale(dossier, personne);
        dossier.setDateModification(LocalDateTime.now());

        // Sauvegarder la personne morale via le service
        personneLMService.createPersonneMorale(personneLMService.convertToDTO(personne));
        return dossierRepository.save(dossier);
    }

    @Transactional
    public DossierEER passerEtapeSuivante(Long dossierId) {
        DossierEER dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé avec l'id: " + dossierId));

        EtapeState etatActuel = stateRegistry.get(dossier.getEtapeActuelle());

        // Vérifier si l'étape actuelle est complète
        if (!etatActuel.estEtapeValide(dossier)) {
            throw new RuntimeException("L'étape actuelle n'est pas complète. Veuillez compléter toutes les actions requises.");
        }

        // Passer à l'étape suivante
        dossier = etatActuel.passerEtapeSuivante(dossier);
        dossier.setDateModification(LocalDateTime.now());

        return dossierRepository.save(dossier);
    }

    @Transactional
    public DossierEER validerDossier(Long dossierId) {
        DossierEER dossier = getDossierValide(dossierId, EtapeProcessus.TERMINE);
        EtapeState etatActuel = stateRegistry.get(dossier.getEtapeActuelle());

        // Validation métier finale
        validationService.validerDossierComplet(dossier);

        dossier = etatActuel.validerDossier(dossier);
        dossier.setDateTerminaison(LocalDateTime.now());
        dossier.setStatut(StatutDossier.VALIDE);
        dossier.setDateModification(LocalDateTime.now());

        return dossierRepository.save(dossier);
    }

    @Transactional
    public DossierEER annulerDossier(Long dossierId, String raison) {
        DossierEER dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé avec l'id: " + dossierId));

        dossier.setStatut(StatutDossier.ANNULE);
        dossier.setDateModification(LocalDateTime.now());

        // Enregistrer l'annulation dans l'historique
        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(dossier.getEtapeActuelle());
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Dossier annulé - Raison: " + raison);
        historique.setActionEffectuee("ANNULATION");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossierRepository.save(dossier);
    }

    // ==================== MÉTHODES UTILITAIRES ====================

    @Transactional(readOnly = true)
    public DossierEER getDossierById(Long dossierId) {
        return dossierRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé avec l'id: " + dossierId));
    }

    @Transactional(readOnly = true)
    public DossierEER getDossierByReference(String reference) {
        return dossierRepository.findByReferenceDossier(reference)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé avec la référence: " + reference));
    }

    @Transactional(readOnly = true)
    public List<DossierEER> getDossiersEnCours() {
        return dossierRepository.findDossiersEnCours();
    }

    @Transactional(readOnly = true)
    public List<DossierEER> getDossiersByCreateur(String createur) {
        return dossierRepository.findByCreateur(createur);
    }

    @Transactional(readOnly = true)
    public List<DossierEER> getDossiersByTiers(Long tiersId) {
        return dossierRepository.findByTiersId(tiersId);
    }

    @Transactional(readOnly = true)
    public List<HistoriqueEtape> getHistoriqueDossier(Long dossierId) {
        DossierEER dossier = getDossierById(dossierId);
        return dossier.getHistoriqueEtapes();
    }

    @Transactional
    public DossierEER mettreAJourCommentaire(Long dossierId, String commentaire) {
        DossierEER dossier = getDossierById(dossierId);
        dossier.setCommentaireGeneral(commentaire);
        dossier.setDateModification(LocalDateTime.now());
        return dossierRepository.save(dossier);
    }

    @Transactional
    public DossierEER reinitialiserWorkflow(Long dossierId) {
        DossierEER dossier = getDossierById(dossierId);

        // Réinitialiser à l'étape INITIATION
        dossier.setEtapeActuelle(EtapeProcessus.INITIATION);
        dossier.setStatut(StatutDossier.EN_COURS);
        dossier.setDateModification(LocalDateTime.now());

        // Historique
        HistoriqueEtape historique = new HistoriqueEtape();
        historique.setEtape(EtapeProcessus.INITIATION);
        historique.setDatePassage(LocalDateTime.now());
        historique.setUtilisateur(dossier.getCreateur());
        historique.setCommentaire("Workflow réinitialisé");
        historique.setActionEffectuee("REINITIALISATION");
        historique.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(historique);

        return dossierRepository.save(dossier);
    }

    // ==================== VALIDATION DES RÈGLES MÉTIER ====================

    @Transactional(readOnly = true)
    public boolean verifierEtapeValide(Long dossierId) {
        DossierEER dossier = getDossierById(dossierId);
        EtapeState etatActuel = stateRegistry.get(dossier.getEtapeActuelle());
        return etatActuel.estEtapeValide(dossier);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStatutWorkflow(Long dossierId) {
        DossierEER dossier = getDossierById(dossierId);
        Map<String, Object> statut = new HashMap<>();

        statut.put("dossierId", dossier.getId());
        statut.put("reference", dossier.getReferenceDossier());
        statut.put("etapeActuelle", dossier.getEtapeActuelle());
        statut.put("statut", dossier.getStatut());
        statut.put("etapeValide", verifierEtapeValide(dossierId));
        statut.put("dateCreation", dossier.getDateCreation());
        statut.put("dateModification", dossier.getDateModification());
        statut.put("createur", dossier.getCreateur());
        statut.put("nombreTitulaires", dossier.aUnTitulaire() ?
                (dossier.getTitulairePrincipal() != null ? 1 : 0) + dossier.getCoTitulaires().size() : 0);
        statut.put("nombrePersonnesPhysiques", dossier.getPersonnesPhysiques().size());
        statut.put("nombrePersonnesMorales", dossier.getPersonnesMorales().size());

        return statut;
    }

    // ==================== MÉTHODES DE VALIDATION INTERNES ====================

    private DossierEER getDossierValide(Long dossierId, EtapeProcessus etapeAttendue) {
        DossierEER dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé avec l'id: " + dossierId));

        if (dossier.getEtapeActuelle() != etapeAttendue) {
            throw new RuntimeException(
                    String.format("Dossier à l'étape %s, mais %s attendue",
                            dossier.getEtapeActuelle(), etapeAttendue)
            );
        }

        if (dossier.getStatut() == StatutDossier.ANNULE) {
            throw new RuntimeException("Le dossier a été annulé");
        }

        if (dossier.getStatut() == StatutDossier.VALIDE) {
            throw new RuntimeException("Le dossier a déjà été validé");
        }

        return dossier;
    }

    // ==================== MÉTHODES DE RAPPORT ====================

    @Transactional(readOnly = true)
    public Map<String, Object> genererRapportDossier(Long dossierId) {
        DossierEER dossier = getDossierById(dossierId);
        Map<String, Object> rapport = new HashMap<>();

        // Informations générales
        rapport.put("informationsGenerales", Map.of(
                "reference", dossier.getReferenceDossier(),
                "statut", dossier.getStatut(),
                "etape", dossier.getEtapeActuelle(),
                "createur", dossier.getCreateur(),
                "dateCreation", dossier.getDateCreation(),
                "dateModification", dossier.getDateModification(),
                "dateTerminaison", dossier.getDateTerminaison(),
                "commentaire", dossier.getCommentaireGeneral()
        ));

        // Titulaires
        List<Map<String, Object>> titulaires = new ArrayList<>();
        if (dossier.getTitulairePrincipal() != null) {
            titulaires.add(creerMapTitulaire(dossier.getTitulairePrincipal(), "TITULAIRE_PRINCIPAL"));
        }
        for (Tiers coTitulaire : dossier.getCoTitulaires()) {
            titulaires.add(creerMapTitulaire(coTitulaire, "CO_TITULAIRE"));
        }
        rapport.put("titulaires", titulaires);

        // Personnes physiques liées
        List<Map<String, Object>> personnesPhysiques = new ArrayList<>();
        for (PersonneLPhysique personne : dossier.getPersonnesPhysiques()) {
            personnesPhysiques.add(creerMapPersonnePhysique(personne));
        }
        rapport.put("personnesPhysiques", personnesPhysiques);

        // Personnes morales liées
        List<Map<String, Object>> personnesMorales = new ArrayList<>();
        for (PersonneLM personne : dossier.getPersonnesMorales()) {
            personnesMorales.add(creerMapPersonneMorale(personne));
        }
        rapport.put("personnesMorales", personnesMorales);

        // Historique
        List<Map<String, Object>> historique = new ArrayList<>();
        for (HistoriqueEtape hist : dossier.getHistoriqueEtapes()) {
            historique.add(Map.of(
                    "etape", hist.getEtape(),
                    "date", hist.getDatePassage(),
                    "utilisateur", hist.getUtilisateur(),
                    "action", hist.getActionEffectuee(),
                    "commentaire", hist.getCommentaire()
            ));
        }
        rapport.put("historique", historique);

        return rapport;
    }

    private Map<String, Object> creerMapTitulaire(Tiers tiers, String type) {
        return Map.of(
                "type", type,
                "id", tiers.getId(),
                "nom", tiers.getNom(),
                "prenom", tiers.getPrenom(),
                "typeTiers", tiers.getTypeTiers(),
                "email", tiers.getEmail(),
                "mobile", tiers.getMobile()
        );
    }

    private Map<String, Object> creerMapPersonnePhysique(PersonneLPhysique personne) {
        return Map.of(
                "id", personne.getId(),
                "nomFamille", personne.getNomFamille(),
                "prenom", personne.getPrenom(),
                //"typeRelation", personne.getTypeRelation(),
                "dateNaissance", personne.getDatedeNaissance(),
                "nationalityPays", personne.getNationalityPays()
        );
    }

    private Map<String, Object> creerMapPersonneMorale(PersonneLM personne) {
        return Map.of(
                "id", personne.getIdPLM(),
                "raisonSociale", personne.getRaisonSociale(),
                "formeJuridique", personne.getFormeJuridique(),
                //"typeRelationMorale", personne.getTypeRelationMorale(),
                "email", personne.getEmail(),
                "telFixe", personne.getTelFixe()
        );
    }

    // ==================== MÉTHODES DE GESTION D'ÉTAT ====================

    @Transactional(readOnly = true)
    public EtapeProcessus getEtapeSuivante(EtapeProcessus etapeActuelle) {
        switch (etapeActuelle) {
            case INITIATION:
                return EtapeProcessus.RECHERCHE_PERSONNE;
            case RECHERCHE_PERSONNE:
                return EtapeProcessus.AJOUT_TITULAIRE;
            case AJOUT_TITULAIRE:
                return EtapeProcessus.AJOUT_PERSONNES_LIEES;
            case AJOUT_PERSONNES_LIEES:
                return EtapeProcessus.TERMINE;
            case TERMINE:
                return EtapeProcessus.TERMINE;
            default:
                throw new IllegalArgumentException("Étape inconnue: " + etapeActuelle);
        }
    }

    @Transactional(readOnly = true)
    public boolean peutPasserEtapeSuivante(Long dossierId) {
        try {
            DossierEER dossier = getDossierById(dossierId);
            return verifierEtapeValide(dossierId) &&
                    dossier.getEtapeActuelle() != EtapeProcessus.TERMINE &&
                    dossier.getStatut() == StatutDossier.EN_COURS;
        } catch (Exception e) {
            return false;
        }
    }
}