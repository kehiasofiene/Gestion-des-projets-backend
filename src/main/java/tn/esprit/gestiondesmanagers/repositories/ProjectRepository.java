package tn.esprit.gestiondesmanagers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Project_status;
import tn.esprit.gestiondesmanagers.entities.User;

import java.util.Date;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer>  {
    @Query("SELECT p FROM Project p WHERE p.start_date >= :startDate AND p.end_date <= :endDate")
    List<Project> findProjectsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT p from Project p where"  + " (:title is null or p.title = :title) and" + "(:startDate is null or p.start_date = :startDate) and " +"(:endDate is null or p.end_date = :endDate) and "+"(:projectStatus is null or p.project_status = :projectStatus)")
    List<Project> searchProjects(@Param("title")String title, @Param("startDate")Date startDate,@Param("endDate") Date endDate,@Param("projectStatus") Project_status projectStatus);

    @Query("select distinct t.project from Task t where t.usertask.id=:user_id ")
     List<Project> projectuser(@Param("user_id") Integer user_id);

    @Query("select distinct t.project from Task t where t.usertask.department.id_dept=:department_id")
    List<Project>projectdepartment(Integer department_id);

    @Query("select distinct t.project from Task t where t.project.end_date between :currentDate and :endDate")
    List<Project> findByend_dateBetween(@Param("currentDate") Date currentDate, @Param("endDate") Date endDate);
}
