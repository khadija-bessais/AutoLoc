package tn.esprit.autoloc.io;

import tn.esprit.autoloc.domain.Agence;

public interface Rapport {
    String entete(Agence agence);
    String generer(Agence agence, double total);
}
