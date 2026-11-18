package tn.esprit.autoloc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AgenceTests {

    // Question 2
    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    @Autowired
    private IAgenceService agenceService;

    // Question 3
    @Autowired
    private IAgenceRepository fullAgenceRepository;

    // Questions 4, 5, 6
    private void addAgence(CrudRepository<Agence, Long> repository) {
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");
        agence.setVille("Tunis");

        // Question 5 : Conversion currentTimeMillis en int pour unicité
        int suffix = (int) System.currentTimeMillis();

        Vehicule v1 = new Vehicule();
        v1.setCategorie("SUV");
        v1.setImmatriculation("7854" + Math.abs(suffix) % 10000 + "TU");
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setStatut("MAINTENANCE");
        v1.setTarifJournalier(100.0);
        v1.setAgence(agence);

        Vehicule v2 = new Vehicule();
        v2.setCategorie("UTILITAIRE");
        v2.setImmatriculation("9876" + Math.abs(suffix) % 10000 + "TU");
        v2.setMarque("Toyota");
        v2.setModele("Yaris");
        v2.setStatut("DISPONIBLE");
        v2.setTarifJournalier(80.0);
        v2.setAgence(agence);

        List<Vehicule> vehicules = new ArrayList<>();
        vehicules.add(v1);
        vehicules.add(v2);
        agence.setVehicules(vehicules);

        repository.save(agence);
    }

    // Question 7 : Tests d'ajout
    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    @Test
    public void createAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence Tunis Central");
        agence.setAdresse("15 Avenue Habib Bourguiba");
        agence.setTelephone("71100200");
        agence.setVille("Tunis");

        Agence created = agenceService.create(agence);

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getIdAgence());
    }

    // Question 10 : Méthodes de chargement avec indication du dépôt utilisé
    @Test
    @Transactional
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "Basic Repository (CrudRepository)");
    }

    @Test
    @Transactional
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "Full Repository (JpaRepository)");
    }

    private void loadAgence(CrudRepository<Agence, Long> repository, String repoType) {
        Iterable<Agence> agences = repository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Dépôt utilisé : ").append(repoType).append(" ===\n");

        for (Agence agence : agences) {
            sb.append("\n").append(agence.getIdAgence()).append(" | ").append(agence.getNom()).append("\n");

            int count = (agence.getVehicules() != null) ? agence.getVehicules().size() : 0;
            sb.append("Vehicules Count : ").append(count).append("\n");

            if (agence.getVehicules() != null) {
                for (Vehicule v : agence.getVehicules()) {
                    sb.append("=== ").append(v.getIdVehicule()).append("|").append(v.getImmatriculation()).append("\n");
                }
            }
        }

        Assertions.fail(sb.toString());
    }

    // Questions 11, 12, 13, 14 : Chargement trié par ID décroissant
    @Test
    public void loadSortedAgences() {
        List<Agence> agences = fullAgenceRepository.findAll(Sort.by("idAgence").descending());

        StringBuilder sb = new StringBuilder();
        sb.append("=== AGENCES TRIÉES PAR ID DÉCROISSANT ===\n");
        for (Agence agence : agences) {
            sb.append(agence.getIdAgence()).append(" | ")
                    .append(agence.getNom()).append(" | ")
                    .append(agence.getVille()).append("\n");
        }

        Assertions.fail(sb.toString());
    }

    // Questions 15, 16, 17, 18 : Chargement paginé (taille = 2) et trié
    @Test
    public void loadPagedAgences() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("idAgence").descending());
        Page<Agence> page = fullAgenceRepository.findAll(pageable);

        StringBuilder sb = new StringBuilder();
        sb.append("=== PAGINATION DES AGENCES ===\n");
        sb.append("Nombre total de pages : ").append(page.getTotalPages()).append("\n");
        sb.append("Numéro de la page en cours : ").append(page.getNumber()).append("\n");
        sb.append("Nombre d'éléments sur cette page : ").append(page.getNumberOfElements()).append("\n");
        sb.append("=== CONTENU DE LA PAGE ===\n");

        for (Agence agence : page.getContent()) {
            sb.append(agence.getIdAgence()).append(" | ")
                    .append(agence.getNom()).append(" | ")
                    .append(agence.getVille()).append("\n");
        }

        Assertions.fail(sb.toString());
    }
}

@Repository
interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}