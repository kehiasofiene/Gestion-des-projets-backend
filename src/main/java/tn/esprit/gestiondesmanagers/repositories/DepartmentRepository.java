package tn.esprit.gestiondesmanagers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestiondesmanagers.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>  {

}
