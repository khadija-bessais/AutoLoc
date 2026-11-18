package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl extends GenericServiceImpl<Contrat, Long> implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    protected JpaRepository<Contrat, Long> getRepository() {
        return contratRepository;
    }

    @Override
    protected String getEntityName() {
        return "Contrat";
    }

    @Override
    protected Long getId(Contrat entity) {
        return entity.getIdContrat();
    }

    @Override
    protected void setFields(Contrat source, Contrat target) {
        target.setDateSignature(source.getDateSignature());
        target.setMontantTotal(source.getMontantTotal());
        target.setValide(source.isValide());
        target.setPaiements(source.getPaiements());
    }

    @Override
    public boolean check(Contrat entity, boolean isNew) {
        if (entity == null) return false;
        if (entity.getDateSignature() == null) return false;
        if (entity.getMontantTotal() == null) return false;
        return true;
    }
}
