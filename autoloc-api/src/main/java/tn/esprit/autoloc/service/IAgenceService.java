package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

public interface IAgenceService extends IGenericService<Agence, Long>, IAgenceSimulationService {
    boolean hasVehicules(Long id);

    Agence ajouterEmployes(Long id, Agence agence);
}
