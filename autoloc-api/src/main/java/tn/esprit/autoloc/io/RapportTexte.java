package tn.esprit.autoloc.io;

import tn.esprit.autoloc.domain.Agence;

public class RapportTexte implements Rapport {

    @Override
    public String entete(Agence agence) {
        return "Id : " + agence.getIdAgence() + ", Nom : " + agence.getNom()
                + ", Tel : " + agence.getTelephone();
    }

    @Override
    public String generer(Agence agence, double total) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(entete(agence)).append("\n");
        stringBuilder.append("Total : " + total + "\n");
        return stringBuilder.toString();
    }
}
