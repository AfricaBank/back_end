package org.africa.bank.integration;

import org.africa.bank.constants.EtapeProcessus;
import org.africa.bank.entity.DossierEER;
import org.africa.bank.service.TiersService;
import org.africa.bank.service.workflow.WorkflowEERService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class WorkflowEERSimpleTests {

    @Autowired
    private WorkflowEERService workflowService;

    @Autowired
    private TiersService tiersService;

    @Test
    public void testContexteSpring() {
        System.out.println("🧪 TEST DU CONTEXTE SPRING");
        assertThat(workflowService).isNotNull();
        assertThat(tiersService).isNotNull();
        System.out.println("✅ Contexte Spring chargé avec succès");
    }

    @Test
    public void testInitiationSimple() {
        System.out.println("🧪 TEST D'INITIATION SIMPLE");

        Map<String, Object> params = new HashMap<>();
        params.put("createur", "TEST_SIMPLE");
        params.put("typeDossier", "TEST");

        DossierEER dossier = workflowService.initierDossier(params);

        System.out.println("📁 Dossier ID: " + dossier.getId());
        System.out.println("📊 Étape: " + dossier.getEtapeActuelle());
        System.out.println("📊 Statut: " + dossier.getStatut());

        assertThat(dossier).isNotNull();
        assertThat(dossier.getEtapeActuelle()).isEqualTo(EtapeProcessus.INITIATION);

        System.out.println("✅ Test d'initiation réussi");
    }

    @Test
    public void testCreateTiers() {
        System.out.println("🧪 TEST CRÉATION TIERS");

        org.africa.bank.dto.TiersDTO tiersDTO = new org.africa.bank.dto.TiersDTO();
        tiersDTO.setNom("TEST");
        tiersDTO.setPrenom("Simple");
        tiersDTO.setEmail("simple@test.com");
        tiersDTO.setMobile("0611111111");
        tiersDTO.setTypeTiers("PARTICULIER");
        tiersDTO.setDateNaissance(java.time.LocalDate.of(1990, 1, 1));

        var savedTiers = tiersService.createTiers(tiersDTO);

        assertThat(savedTiers).isNotNull();
        assertThat(savedTiers.getId()).isNotNull();
        assertThat(savedTiers.getNom()).isEqualTo("TEST");

        System.out.println("✅ Tiers créé avec ID: " + savedTiers.getId());
    }
}