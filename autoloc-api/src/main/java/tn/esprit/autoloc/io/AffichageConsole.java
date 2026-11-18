package tn.esprit.autoloc.io;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)   // lisible à l'exécution par la Factory
@Target(ElementType.TYPE)             // s'applique à une classe
public @interface AffichageConsole {
}