package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository; // Import ajouté

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl extends GenericServiceImpl<Equipement, Long> implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    protected JpaRepository<Equipement, Long> getRepository() {
        return equipementRepository;
    }

    @Override
    protected String getEntityName() {
        return "Equipement";
    }

    @Override
    protected Long getId(Equipement entity) {
        return entity.getIdEquipement();
    }

    @Override
    protected void setFields(Equipement source, Equipement target) {
        target.setNom(source.getNom());
        target.setVehicules(source.getVehicules());
    }

    @Override
    public boolean check(Equipement entity, boolean isNew) {
        if (entity == null) return false;
        if (Utils.isNotEmpty(entity.getNom())) return false;
        return true;
    }
}