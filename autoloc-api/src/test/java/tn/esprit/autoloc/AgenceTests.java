package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

import java.math.BigDecimal;
import java.util.Set;

@SpringBootTest
public class AgenceTests {

    @Autowired
    private AgenceRepositoryMock agenceRepository;

    @Test
    public void addAgence() {

        Agence agence = new Agence();

        agence.setNom("Agence ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        Vehicule vehicule1 = new Vehicule();
        vehicule1.setCategorie(CategorieVehicule.SUV);
        vehicule1.setImmatriculation("785414TU96");
        vehicule1.setMarque("Isuzu");
        vehicule1.setModele("DMax");
        vehicule1.setStatut(StatutVehicule.MAINTENANCE);
        vehicule1.setTarifJournalier(new BigDecimal("100"));
        vehicule1.setAgence(agence);

        Vehicule vehicule2 = new Vehicule();
        vehicule2.setCategorie(CategorieVehicule.UTILITAIRE);
        vehicule2.setImmatriculation("785414TU95");
        vehicule2.setMarque("Toyota");
        vehicule2.setModele("Yaris");
        vehicule2.setStatut(StatutVehicule.DISPONIBLE);
        vehicule2.setTarifJournalier(new BigDecimal("80"));
        vehicule2.setAgence(agence);

        agence.setVehicules(Set.of(vehicule1, vehicule2));

        agenceRepository.save(agence);
    }

    @Test
    public void loadAgence() {

        Iterable<Agence> agences = agenceRepository.findAll();

        StringBuilder result = new StringBuilder();

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


}

interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}