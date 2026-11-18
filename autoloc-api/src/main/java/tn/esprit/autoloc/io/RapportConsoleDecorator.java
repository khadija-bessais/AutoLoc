package tn.esprit.autoloc.io;

import tn.esprit.autoloc.domain.Agence;

public class RapportConsoleDecorator implements Rapport {

    private final Rapport rapport;

    public RapportConsoleDecorator(Rapport rapport) {
        this.rapport = rapport;
    }

    @Override
    public String entete(Agence agence) {
        return rapport.entete(agence);
    }

    @Override
    public String generer(Agence agence, double total) {
        System.out.println(rapport.entete(agence));   // en-tête affiché sur la console
        return rapport.generer(agence, total);        // le total est retourné
    }
}
