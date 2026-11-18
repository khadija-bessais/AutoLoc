package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl extends GenericServiceImpl<Reservation, Long> implements IReservationService {

    private final IReservationRepository reservationRepository;

    @Override
    protected JpaRepository<Reservation, Long> getRepository() {
        return reservationRepository;
    }

    @Override
    protected String getEntityName() {
        return "Reservation";
    }

    @Override
    protected Long getId(Reservation entity) {
        return entity.getIdReservation();
    }

    @Override
    protected void setFields(Reservation source, Reservation target) {
        target.setDateDebut(source.getDateDebut());
        target.setDateFin(source.getDateFin());
        target.setVehicule(source.getVehicule());
        target.setClient(source.getClient());
        target.setContrat(source.getContrat());
    }

    @Override
    public boolean check(Reservation entity, boolean isNew) {
        if (entity == null) return false;
        if (entity.getDateDebut() == null || entity.getDateFin() == null) return false;
        if (entity.getDateFin().isBefore(entity.getDateDebut())) return false;
        return true;
    }
}