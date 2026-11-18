// IGenericService.java
package tn.esprit.autoloc.service;

public interface IGenericService<T, ID>
        extends IRead<T, ID>, IWrite<T, ID>, ICheck<T> {
}