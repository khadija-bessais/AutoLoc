package tn.esprit.autoloc.service;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

import java.util.List;

public abstract class GenericServiceImpl<T, ID> implements IGenericService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();
    protected abstract String getEntityName();
    protected abstract ID getId(T entity);
    protected abstract void setFields(T source, T target);

    @Override
    public T create(T entity) {
        if (getId(entity) != null) {
            throw new IllegalArgumentException("L'identifiant doit être nul lors de la création.");
        }
        if (!check(entity, true)) {
            throw new IllegalArgumentException("Données de l'entité non valides.");
        }
        return getRepository().save(entity);
    }

    @Override
    public T findById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityName(), String.valueOf(id)));
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public T update(ID id, T entity) {
        T existingEntity = findById(id);
        if (!check(entity, false)) {
            throw new IllegalArgumentException("Données invalides pour la mise à jour.");
        }
        setFields(entity, existingEntity);
        return getRepository().save(existingEntity);
    }

    @Override
    public void deleteById(ID id) {
        if (!getRepository().existsById(id)) {
            throw new ResourceNotFoundException(getEntityName(), String.valueOf(id));
        }
        getRepository().deleteById(id);
    }
}
