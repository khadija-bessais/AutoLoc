package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl extends GenericServiceImpl<Employe, Long> implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    protected JpaRepository<Employe, Long> getRepository() {
        return employeRepository;
    }

    @Override
    protected String getEntityName() {
        return "Employe";
    }

    @Override
    protected Long getId(Employe entity) {
        return entity.getIdEmploye();
    }

    @Override
    protected void setFields(Employe source, Employe target) {
        target.setNom(source.getNom());
        target.setPrenom(source.getPrenom());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Employe entity, boolean isNew) {
        if (entity == null) return false;
        if (Utils.isNotEmpty(entity.getNom())) return false;
        if (Utils.isNotEmpty(entity.getPrenom())) return false;
        return true;
    }
}