// IRead.java
package tn.esprit.autoloc.service;

import java.util.List;

public interface IRead<T, ID> {
    T findById(ID id);
    List<T> findAll();
}