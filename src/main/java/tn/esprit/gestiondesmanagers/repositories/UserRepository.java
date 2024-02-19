package tn.esprit.gestiondesmanagers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>  {

    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = ?1")
    public boolean existsByEmail(String email);

    @Query("select distinct t.usertask from Task t where t.project.id=:project_id")
    public List<User> userproject(Integer project_id);

    @Query("select distinct t.usertask.phone_number from Task t where t.project=:project")
    public String getUserPhoneNumberFromProject(Project project);

    @Query("select u from User  u where u.email= :email")
    User validEmail(String email);
}
