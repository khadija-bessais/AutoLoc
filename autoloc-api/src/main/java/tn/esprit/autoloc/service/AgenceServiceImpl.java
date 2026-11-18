package tn.esprit.autoloc.service;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.io.AffichageConsole;
import tn.esprit.autoloc.io.Rapport;
import tn.esprit.autoloc.io.RapportFactory;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Service
@Primary
@AffichageConsole
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long>
        implements IAgenceService, IAgenceSimulationService {

    private final IAgenceRepository agenceRepository;
    private final IVehiculeService vehiculeService;
    private final IEmployeService employeService;
    private final Rapport rapport = RapportFactory.creer(AgenceServiceImpl.class);

    public AgenceServiceImpl(IAgenceRepository agenceRepository,
                             IVehiculeService vehiculeService,
                             IEmployeService employeService) {
        this.agenceRepository = agenceRepository;
        this.vehiculeService = vehiculeService;
        this.employeService = employeService;
    }

    @Override
    protected JpaRepository<Agence, Long> getRepository() {
        return agenceRepository;
    }

    @Override
    protected String getEntityName() {
        return "Agence";
    }

    @Override
    protected Long getId(Agence entity) {
        return entity != null ? entity.getIdAgence() : null;
    }

    @Override
    protected void setFields(Agence source, Agence target) {
        target.setNom(source.getNom());
        target.setAdresse(source.getAdresse());
        target.setTelephone(source.getTelephone());
        target.setVille(source.getVille());
    }

    @Override
    public Agence create(Agence agence) {
        if (agence != null) {
            agence.setIdAgence(null);
        }
        return super.create(agence);
    }

    @Override
    public Agence update(Long id, Agence agence) {
        if (agence == null) {
            throw new IllegalArgumentException("Données invalides pour la mise à jour.");
        }

        agence.setIdAgence(id);

        if (!check(agence, false)) {
            throw new IllegalArgumentException("Données invalides pour la mise à jour.");
        }

        Agence existingAgence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", String.valueOf(id)));

        existingAgence.setNom(agence.getNom());
        existingAgence.setAdresse(agence.getAdresse());
        existingAgence.setTelephone(agence.getTelephone());
        existingAgence.setVille(agence.getVille());

        return agenceRepository.save(existingAgence);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", String.valueOf(id)));

        // Grâce à la configuration SQL (ON DELETE SET NULL),
        // MySQL détache automatiquement les véhicules et employés liés lors de la suppression.
        agenceRepository.delete(agence);
    }

    @Override
    public boolean check(Agence entity, boolean isNew) {
        if (entity == null) return false;

        if (entity.getNom() == null || entity.getNom().trim().isEmpty()) return false;
        if (entity.getAdresse() == null || entity.getAdresse().trim().isEmpty()) return false;
        if (entity.getTelephone() == null || entity.getTelephone().trim().isEmpty()) return false;
        if (entity.getVille() == null || entity.getVille().trim().isEmpty()) return false;

        return true;
    }

    @Override
    public boolean hasVehicules(Long id) {
        Agence agence = findById(id);
        return agence != null && agence.getVehicules() != null && !agence.getVehicules().isEmpty();
    }

    @Override
    public String simuler(Agence agence, int nbJours, VehiculeOffre offre) {
        double total = 0;
        for (Vehicule vehicule : agence.getVehicules()) {
            total += vehiculeService.calculTarif(vehicule, nbJours, offre);
        }
        return rapport.generer(agence, total);
    }

    @Override
    @Transactional
    public Agence ajouterEmployes(Long id, Agence agence) {
        // 1. Mettre à jour d'abord l'agence
        Agence existing = update(id, agence);

        // 2. Utiliser le service des employés pour insérer les employés
        List<Employe> nouveaux = agence.getEmployes() != null ? agence.getEmployes() : List.of();
        for (Employe employe : nouveaux) {
            existing.addEmploye(employe);      // symétrie des deux côtés
            employeService.create(employe);
        }

        // 3. Mettre à jour l'effectif avec la taille de la liste
        existing.setEffectif(nouveaux.size());
        return agenceRepository.save(existing);
    }
}