package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends GenericServiceImpl<Client, Long> implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    protected JpaRepository<Client, Long> getRepository() {
        return clientRepository;
    }

    @Override
    protected String getEntityName() {
        return "Client";
    }

    @Override
    protected Long getId(Client entity) {
        return entity.getIdClient();
    }

    @Override
    protected void setFields(Client source, Client target) {
        target.setNom(source.getNom());
        target.setPrenom(source.getPrenom());
    }

    @Override
    public boolean check(Client entity, boolean isNew) {
        if (entity == null) return false;
        if (Utils.isNotEmpty(entity.getNom())) return false;
        if (Utils.isNotEmpty(entity.getPrenom())) return false;
        return true;
    }
}