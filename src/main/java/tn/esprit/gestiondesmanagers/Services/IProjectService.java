package tn.esprit.gestiondesmanagers.Services;

import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Project_status;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.IGenericService;

import java.util.Date;
import java.util.List;

public interface IProjectService extends IGenericService<Project,Integer>  {
    public List<Project> getProjectsBetweenDates(Date startDate, Date endDate);
    public Project updateProject(Project project);

    public List<Project> getProjectsApproachingDeadline();

    //public List<Project> searchProjects(String title,Date startDate,Date endDate,Project_status projectStatus);
}
