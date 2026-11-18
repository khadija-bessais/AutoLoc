package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl extends GenericServiceImpl<Maintenance, Long> implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    protected JpaRepository<Maintenance, Long> getRepository() {
        return maintenanceRepository;
    }

    @Override
    protected String getEntityName() {
        return "Maintenance";
    }

    @Override
    protected Long getId(Maintenance entity) {
        return entity.getIdMaintenance();
    }

    @Override
    protected void setFields(Maintenance source, Maintenance target) {
        target.setDateMaintenance(source.getDateMaintenance());
        target.setDescription(source.getDescription());
        target.setCout(source.getCout());
        target.setVehicule(source.getVehicule());
    }

    @Override
    public boolean check(Maintenance entity, boolean isNew) {
        if (entity == null) return false;
        if (Utils.isNotEmpty(entity.getDescription())) return false;
        if (entity.getCout() == null || entity.getCout() < 0) return false;
        return true;
    }
}
