package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Question 16 : Relation vers Vehicule
    @ManyToOne
    private Vehicule vehicule;

    // Question 16 : Relation vers Client
    @ManyToOne
    private Client client;

    // Question 20 : Relation vers Contrat (clé étrangère dans reservation)
    @OneToOne
    private Contrat contrat;
}