package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Set;

@SpringBootTest
public class AgenceTests {

    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;

    @Autowired
    private IAgenceRepository fullAgenceRepository;

    private void addAgence(CrudRepository<Agence, Long> repository) {

        Agence agence = new Agence();

        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        Vehicule vehicule1 = new Vehicule();
        vehicule1.setCategorie(CategorieVehicule.SUV);
        vehicule1.setImmatriculation("785414TU" + (int) System.currentTimeMillis());
        vehicule1.setMarque("Isuzu");
        vehicule1.setModele("DMax");
        vehicule1.setStatut(StatutVehicule.MAINTENANCE);
        vehicule1.setTarifJournalier(new BigDecimal("100"));
        vehicule1.setAgence(agence);

        Vehicule vehicule2 = new Vehicule();
        vehicule2.setCategorie(CategorieVehicule.UTILITAIRE);
        vehicule2.setImmatriculation("785414TU" + ((int) System.currentTimeMillis() + 1));
        vehicule2.setMarque("Toyota");
        vehicule2.setModele("Yaris");
        vehicule2.setStatut(StatutVehicule.DISPONIBLE);
        vehicule2.setTarifJournalier(new BigDecimal("80"));
        vehicule2.setAgence(agence);

        agence.setVehicules(Set.of(vehicule1, vehicule2));

        repository.save(agence);
    }

    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }


    private void loadAgence(CrudRepository<Agence, Long> repository, String repositoryType) {

        Iterable<Agence> agences = repository.findAll();

        StringBuilder result = new StringBuilder();

        result.append("Repository : ")
                .append(repositoryType)
                .append("\n\n");

        for (Agence agence : agences) {

            result.append(agence.getIdAgence())
                    .append(" | ")
                    .append(agence.getNom())
                    .append("\n");

            result.append("Vehicules Count : ")
                    .append(agence.getVehicules().size())
                    .append("\n");

            for (Vehicule vehicule : agence.getVehicules()) {

                result.append(vehicule.getIdVehicule())
                        .append(" | ")
                        .append(vehicule.getImmatriculation())
                        .append("\n");
            }
        }

        org.junit.jupiter.api.Assertions.fail(result.toString());
    }

    @Test
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "Basic Repository");
    }

    @Test
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "Full Repository");
    }

    @Test
    public void loadSortedAgences() {

        Iterable<Agence> agences =
                fullAgenceRepository.findAll(
                        Sort.by(Sort.Direction.DESC, "idAgence")
                );

        StringBuilder result = new StringBuilder();

        for (Agence agence : agences) {

            result.append(agence.getIdAgence())
                    .append(" | ")
                    .append(agence.getNom())
                    .append(" | ")
                    .append(agence.getVille())
                    .append(" | ")
                    .append(agence.getAdresse())
                    .append(" | ")
                    .append(agence.getTelephone())
                    .append("\n");
        }

        org.junit.jupiter.api.Assertions.fail(result.toString());
    }

    @Test
    public void loadPagedAgences() {

        Page<Agence> page =
                fullAgenceRepository.findAll(
                        PageRequest.of(
                                0,
                                2,
                                Sort.by(Sort.Direction.DESC, "idAgence")
                        )
                );

        StringBuilder result = new StringBuilder();

        result.append("Nombre total de pages : ")
                .append(page.getTotalPages())
                .append("\n");

        result.append("Page en cours : ")
                .append(page.getNumber() + 1)
                .append("\n\n");

        for (Agence agence : page.getContent()) {

            result.append(agence.getIdAgence())
                    .append(" | ")
                    .append(agence.getNom())
                    .append(" | ")
                    .append(agence.getVille())
                    .append(" | ")
                    .append(agence.getAdresse())
                    .append(" | ")
                    .append(agence.getTelephone())
                    .append("\n");
        }

        org.junit.jupiter.api.Assertions.fail(result.toString());
    }
}


@Repository
interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}