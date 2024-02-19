package tn.esprit.gestiondesmanagers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Task;
import tn.esprit.gestiondesmanagers.entities.Task_Status;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    @Query("SELECT t FROM Task t WHERE t.start_date >= :startDate AND t.end_date <= :endDate and t.project.id=:project_id")
    List<Task> findTaskssBetweenDates(Integer project_id,@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("Select t from Task t where t.project.id=:project_id")
    List<Task> getTasksByProject(Integer project_id);

    @Query("Select t from Task t where t.task_status=:task_status")
    List<Task> getTasksBystatus(Task_Status task_status);


}
