package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl extends GenericServiceImpl<Paiement, Long> implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    protected JpaRepository<Paiement, Long> getRepository() {
        return paiementRepository;
    }

    @Override
    protected String getEntityName() {
        return "Paiement";
    }

    @Override
    protected Long getId(Paiement entity) {
        return entity.getIdPaiement();
    }

    @Override
    protected void setFields(Paiement source, Paiement target) {
        throw new UnsupportedOperationException("Le paiement est en lecture seule.");
    }

    @Override
    public boolean check(Paiement entity, boolean isNew) {
        return entity != null && entity.getMontant() != null;
    }

    @Override
    public Paiement create(Paiement entity) {
        throw new UnsupportedOperationException("Création de paiement interdite (lecture seule).");
    }

    @Override
    public Paiement update(Long id, Paiement entity) {
        throw new UnsupportedOperationException("Mise à jour de paiement interdite (lecture seule).");
    }
}
