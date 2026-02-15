package org.africa.bank.service.workflow;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.constants.StatutDossier;
import org.africa.bank.dto.PersonneLMDTO;
import org.africa.bank.dto.PersonneLPhysiqueDTO;
import org.africa.bank.dto.TiersDTO;
import org.africa.bank.entity.*;
import org.africa.bank.repository.DossierEERRepository;
import org.africa.bank.repository.TiersRepository;
import org.africa.bank.service.DossierNumberService;
import org.africa.bank.service.PersonneLMService;
import org.africa.bank.service.PersonneLPhysiqueService;
import org.africa.bank.service.TiersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class WorkflowEERService {

    @Autowired
    private DossierEERRepository dossierRepository;

    @Autowired
    private TiersRepository tiersRepository;

    @Autowired
    private TiersService tiersService;

    @Autowired
    private PersonneLPhysiqueService personnePhysiqueService;

    @Autowired
    private PersonneLMService personneLMService;

    @Autowired
    private DossierNumberService dossierNumberService;

    /**
     * ÉTAPE 1 & 2 : Initialisation
     */
    public DossierEER initierDossier(Map<String, Object> params) {
        DossierEER dossier = new DossierEER();
        dossier.setReferenceDossier(dossierNumberService.generateReference());
        dossier.setStatut(StatutDossier.EN_COURS);
        dossier.setEtapeActuelle(EtapeProcessus.RECHERCHE_PERSONNE);
        dossier.setDateCreation(LocalDateTime.now());

        dossier.setCreateur((String) params.getOrDefault("createur", "SYSTEM"));
        dossier.setTypePersonne((String) params.get("typePersonne"));
        dossier.setCodeSiege((String) params.get("codeSiege"));
        dossier.setNatureRelation((String) params.get("natureRelation"));
        dossier.setCodeExploitant((String) params.get("codeExploitant"));
        dossier.setNomExploitant((String) params.get("nomExploitant"));
        dossier.setCiviliteCollectivite((String) params.get("civiliteCollectivite"));
        dossier.setNomCollectivite((String) params.get("nomCollectivite"));

        return forceInitialize(dossierRepository.save(dossier));
    }

    /**
     * ÉTAPE 3 : Ajout Titulaire
     */
    public DossierEER ajouterTitulaire(Long dossierId, TiersDTO tiersDTO, boolean estCoTitulaire) {
        DossierEER dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        Tiers tiers = (tiersDTO.getId() != null)
                ? tiersRepository.findById(tiersDTO.getId()).orElseThrow()
                : tiersRepository.save(tiersService.convertToEntity(tiersDTO));

        validerUniciteRole(dossier, tiers, estCoTitulaire);

        if (estCoTitulaire) {
            if (!dossier.getCoTitulaires().contains(tiers)) {
                dossier.getCoTitulaires().add(tiers);
                ajouterHistorique(dossier, "Co-titulaire ajouté : " + tiers.getNom());
            }
        } else {
            dossier.setTitulairePrincipal(tiers);
            ajouterHistorique(dossier, "Titulaire principal défini : " + tiers.getNom());
        }

        dossier.setEtapeActuelle(EtapeProcessus.AJOUT_PERSONNES_LIEES);
        return forceInitialize(dossierRepository.save(dossier));
    }

    /**
     * ÉTAPE 4 : Ajout PLP
     */
    public DossierEER ajouterLienPhysique(Long dossierId, PersonneLPhysiqueDTO dto) {
        DossierEER dossier = dossierRepository.findById(dossierId).orElseThrow();

        Tiers tiers = (dto.getTiersReferenceId() != null)
                ? tiersRepository.findById(dto.getTiersReferenceId()).orElseThrow()
                : tiersRepository.save(tiersService.convertToEntity(mapperTiersDepuisPlp(dto)));

        PersonneLPhysique plp = personnePhysiqueService.convertToEntity(dto);
        plp.setTiers(tiers);
        plp.setDossierEER(dossier);

        dossier.getPersonnesPhysiques().add(plp);
        ajouterHistorique(dossier, "Personne liée physique ajoutée : " + tiers.getNom());

        return forceInitialize(dossierRepository.save(dossier));
    }

    /**
     * ÉTAPE 4 : Ajout PLM
     */
    public DossierEER ajouterLienMoral(Long dossierId, PersonneLMDTO dto) {
        DossierEER dossier = dossierRepository.findById(dossierId).orElseThrow();

        Tiers tiers = (dto.getTiersReferenceId() != null)
                ? tiersRepository.findById(dto.getTiersReferenceId()).orElseThrow()
                : tiersRepository.save(tiersService.convertToEntity(mapperTiersDepuisPlm(dto)));

        PersonneLM plm = personneLMService.convertToEntity(dto);
        plm.setTiers(tiers);
        plm.setDossierEER(dossier);

        dossier.getPersonnesMorales().add(plm);
        ajouterHistorique(dossier, "Personne liée morale ajoutée : " + tiers.getNom());

        return forceInitialize(dossierRepository.save(dossier));
    }

    /**
     * ÉTAPE FINALE
     */
    public DossierEER finaliserDossier(Long dossierId) {
        DossierEER dossier = dossierRepository.findById(dossierId).orElseThrow();
        if (dossier.getTitulairePrincipal() == null) {
            throw new RuntimeException("Un titulaire principal est requis.");
        }
        dossier.setEtapeActuelle(EtapeProcessus.TERMINE);
        dossier.setStatut(StatutDossier.VALIDE);
        dossier.setDateTerminaison(LocalDateTime.now());
        ajouterHistorique(dossier, "Dossier finalisé avec succès.");

        return forceInitialize(dossierRepository.save(dossier));
    }

    /**
     * MÉTHODES DE RÉCUPÉRATION (Celles qui manquaient !)
     */
    @Transactional(readOnly = true)
    public DossierEER getDossierById(Long id) {
        return forceInitialize(dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable")));
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStatutWorkflow(Long dossierId) {
        DossierEER dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier introuvable"));

        Map<String, Object> statut = new HashMap<>();
        statut.put("dossierId", dossier.getId());
        statut.put("reference", dossier.getReferenceDossier());
        statut.put("etapeActuelle", dossier.getEtapeActuelle());
        statut.put("statut", dossier.getStatut());
        statut.put("createur", dossier.getCreateur());
        statut.put("dateCreation", dossier.getDateCreation());
        statut.put("hasTitulairePrincipal", dossier.getTitulairePrincipal() != null);
        statut.put("nbCoTitulaires", dossier.getCoTitulaires().size());
        statut.put("nbPersonnesPhysiques", dossier.getPersonnesPhysiques().size());
        statut.put("nbPersonnesMorales", dossier.getPersonnesMorales().size());

        return statut;
    }

    // --- UTILITAIRES ---

    private DossierEER forceInitialize(DossierEER dossier) {
        if (dossier == null) return null;

        // 1. Charger le Titulaire Principal et ses propres listes Lazy
        if (dossier.getTitulairePrincipal() != null) {
            dossier.getTitulairePrincipal().getNom(); // Charge le tiers
            if (dossier.getTitulairePrincipal().getAccounts() != null) {
                dossier.getTitulairePrincipal().getAccounts().size(); // <--- AJOUTÉ ICI
            }
            if (dossier.getTitulairePrincipal().getPersonnes() != null) {
                dossier.getTitulairePrincipal().getPersonnes().size(); // <--- AJOUTÉ ICI
            }
        }

        // 2. Charger les Co-titulaires et leurs listes
        if (dossier.getCoTitulaires() != null) {
            dossier.getCoTitulaires().forEach(ct -> {
                if (ct.getAccounts() != null) ct.getAccounts().size();
            });
        }

        // 3. Charger les autres collections du Dossier
        if (dossier.getPersonnesPhysiques() != null) dossier.getPersonnesPhysiques().size();
        if (dossier.getPersonnesMorales() != null) dossier.getPersonnesMorales().size();
        if (dossier.getHistoriqueEtapes() != null) dossier.getHistoriqueEtapes().size();

        return dossier;
    }

    private void ajouterHistorique(DossierEER dossier, String message) {
        if (dossier.getHistoriqueEtapes() == null) dossier.setHistoriqueEtapes(new ArrayList<>());
        HistoriqueEtape h = new HistoriqueEtape();
        h.setDatePassage(LocalDateTime.now());
        h.setCommentaire(message);
        h.setUtilisateur(dossier.getCreateur());
        h.setActionEffectuee("WORKFLOW_STEP");
        h.setDossierEER(dossier);
        dossier.getHistoriqueEtapes().add(h);
    }

    private void validerUniciteRole(DossierEER dossier, Tiers tiers, boolean estAjoutCoTitulaire) {
        if (estAjoutCoTitulaire && dossier.getTitulairePrincipal() != null && dossier.getTitulairePrincipal().getId().equals(tiers.getId())) {
            throw new RuntimeException("Le tiers est déjà titulaire principal.");
        }
    }

    private TiersDTO mapperTiersDepuisPlp(PersonneLPhysiqueDTO plp) {
        TiersDTO t = new TiersDTO();
        t.setNom(plp.getNomFamille());
        t.setPrenom(plp.getPrenom());
        t.setTypeTiers("PHYSIQUE");
        return t;
    }

    private TiersDTO mapperTiersDepuisPlm(PersonneLMDTO plm) {
        TiersDTO t = new TiersDTO();
        t.setNom(plm.getRaisonSociale());
        t.setTypeTiers("MORALE");
        return t;
    }
}