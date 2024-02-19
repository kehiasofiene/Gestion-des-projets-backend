package tn.esprit.gestiondesmanagers.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondesmanagers.Services.IProjectService;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Project_status;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.GenericController;
import tn.esprit.gestiondesmanagers.repositories.ProjectRepository;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Project")
@CrossOrigin("http://localhost:4200")
public class ProjectController extends GenericController<Project,Integer> {
    private final IProjectService projectService;
    private final ProjectRepository projectRepository;

    @GetMapping("/projectdate")
    public ResponseEntity<List<Project>> getProjectsBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Project> projects = projectService.getProjectsBetweenDates(startDate, endDate);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/projects")
    public List<Project> searchProjects(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) Project_status projectStatus
    ){
        return projectRepository.searchProjects(title, startDate, endDate, projectStatus);
    }

    @GetMapping("/userprojects/{user_id}")
    public List<Project> userProjects(@PathVariable Integer user_id){
        return projectRepository.projectuser(user_id);
    }

    @PutMapping("/update")
    public Project updateProject(@RequestBody Project project){
        return projectService.updateProject(project);
    }

    @GetMapping("/projectdepartment/{department_id}")
    public List<Project> projectbydepartment(@PathVariable Integer department_id){
      return projectRepository.projectdepartment(department_id);
    }

    @GetMapping("/findbyenddate")
   public List<Project> findByEnd_dateBetween( @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date currentDate,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate){
        return projectRepository.findByend_dateBetween(currentDate, endDate);
    }
}
