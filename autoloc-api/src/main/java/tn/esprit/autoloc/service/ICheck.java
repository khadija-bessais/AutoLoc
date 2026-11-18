// ICheck.java
package tn.esprit.autoloc.service;

public interface ICheck<T> {
    boolean check(T entity, boolean isNew);
}