package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicAgenceServiceImpl implements IGenericService<Agence, Long> {

    private final IAgenceRepository agenceRepository;

    @Override
    public boolean check(Agence agence, boolean isNew) {
        if (agence == null) return false;
        if (Utils.isNotEmpty(agence.getNom())) return false;
        if (Utils.isNotEmpty(agence.getAdresse())) return false;
        if (Utils.isNotEmpty(agence.getTelephone())) return false;
        if (Utils.isNotEmpty(agence.getVille())) return false;
        return true;
    }

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException("L'identifiant doit être nul pour une nouvelle création.");
        }
        if (!check(agence, true)) {
            throw new IllegalArgumentException("Données de l'agence non valides.");
        }
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", String.valueOf(id)));
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existingAgence = findById(id);
        if (!check(agence, false)) {
            throw new IllegalArgumentException("Données de mise à jour invalides.");
        }
        existingAgence.setNom(agence.getNom());
        existingAgence.setAdresse(agence.getAdresse());
        existingAgence.setTelephone(agence.getTelephone());
        existingAgence.setVille(agence.getVille());
        return agenceRepository.save(existingAgence);
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", String.valueOf(id));
        }
        agenceRepository.deleteById(id);
    }

    // Plus de @Override : hasVehicules appartient à IAgenceService, que cette classe n'implémente plus
    public boolean hasVehicules(Long id) {
        Agence agence = findById(id);
        return agence.getVehicules() != null && !agence.getVehicules().isEmpty();
    }
}