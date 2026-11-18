package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

@Component("LONGUE_DUREE")
public class LongueDureeOffre implements OffreStrategy {
    @Override
    public double calculer(double tarifJournalier, int nbJours) {
        double total = tarifJournalier * 0.75 * nbJours;  // -25 %
        if (total > 2000) {
            total *= 0.95;                                // -5 % si > 2000
        }
        return total;
    }
}
