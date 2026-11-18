package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    private String marque;
    private String modele;
    private String categorie;
    private String statut;
    private Double tarifJournalier;

    @ManyToOne
    @JsonBackReference("agence-vehicules")
    private Agence agence;

    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "vehicule")
    @JsonIgnore
    private List<Maintenance> maintenances;

    @ManyToMany
    private List<Equipement> equipements;

    @PreRemove
    private void verifierAvantSuppression() {
        if (equipements != null && !equipements.isEmpty()) {
            throw new IllegalStateException(
                    "Suppression impossible : la véhicule " + idVehicule + " a encore des équipements affectés.");
        }
    }
}