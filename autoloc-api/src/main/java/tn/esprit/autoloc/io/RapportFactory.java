package tn.esprit.autoloc.io;

public class RapportFactory {

    private RapportFactory() {
    }

    public static Rapport creer(Class<?> classe) {
        Rapport rapport = new RapportTexte();
        if (classe.isAnnotationPresent(AffichageConsole.class)) {
            rapport = new RapportConsoleDecorator(rapport);
        }
        return rapport;
    }
}
