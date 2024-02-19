package tn.esprit.gestiondesmanagers.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondesmanagers.Services.ITaskService;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Task;
import tn.esprit.gestiondesmanagers.entities.Task_Status;
import tn.esprit.gestiondesmanagers.generic.GenericController;
import tn.esprit.gestiondesmanagers.repositories.TaskRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/Task")
public class TaskController extends GenericController<Task,Integer> {
  private final ITaskService taskService;

  private final TaskRepository taskRepository;

  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  Validator validator = factory.getValidator();

  @PostMapping("/add/{project_id}/{user_id}")
  public String addandaffecttasktoproject(@RequestBody Task task,@PathVariable Integer project_id,@PathVariable Integer user_id){
    StringBuilder message;
    Set<ConstraintViolation<Task>> constraintViolations = validator.validate(task);
    if (constraintViolations.size() > 0 ) {
      message = new StringBuilder("Erreur lors de l'ajout : \n");
      for (ConstraintViolation<Task> contraintes : constraintViolations) {
        message.append("Champ :").append(contraintes.getPropertyPath()).append(": ").append(contraintes.getMessage()).append("\n");
      }}else {
      taskService.addandaffecttasktoproject(task, project_id, user_id);
      message = new StringBuilder("L'ajout de la tache a été effectué avec succès.");
    }
    return message.toString();
  }

  @GetMapping("/Taskdate")
  public List<Task> TasksBetweenDates(@RequestParam("project_id") Integer project_id,  @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                       @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    return taskRepository.findTaskssBetweenDates(project_id,startDate,endDate);
  }

  @GetMapping("Tasks/{project_id}")
  public List<Task> gettaskbyproject(@PathVariable Integer project_id){
    return taskRepository.getTasksByProject(project_id);
  }

  @GetMapping("Task/{task_status}")
   public List<Task> getTasksBystatus(@PathVariable Task_Status task_status){
    return  taskRepository.getTasksBystatus(task_status);
  }
}
