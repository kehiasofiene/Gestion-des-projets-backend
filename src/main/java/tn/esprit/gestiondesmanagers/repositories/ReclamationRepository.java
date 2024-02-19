package tn.esprit.gestiondesmanagers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Reclamation;
import tn.esprit.gestiondesmanagers.entities.Reclamation_status;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Integer> {

    @Query("select distinct r from Reclamation r where r.user_reclamation.id=:user_id")
    public List<Reclamation> reclamationsofuser(Integer user_id);

    @Query("select r from Reclamation r where r.reclamation_status=:reclamation_status")
    public List<Reclamation> findBystatus(Reclamation_status reclamation_status);

    @Query("select r from Reclamation r where r.projectreclamation.id=:project_id")
    public List<Reclamation> getReclamationsByProjectreclamation(Integer project_id);

}
