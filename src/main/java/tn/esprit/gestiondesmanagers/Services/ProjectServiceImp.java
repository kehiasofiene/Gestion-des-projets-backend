package tn.esprit.gestiondesmanagers.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondesmanagers.SMS.SMSService;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Project_status;
import tn.esprit.gestiondesmanagers.generic.IGenericServiceImp;
import tn.esprit.gestiondesmanagers.repositories.ProjectRepository;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectServiceImp extends IGenericServiceImp<Project,Integer> implements IProjectService  {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    @Autowired
    private SMSService smsService;

    @Override
    public List<Project> getProjectsBetweenDates(Date startDate, Date endDate) {
            return projectRepository.findProjectsBetweenDates(startDate, endDate);
        }


    public Project updateProject(Project project) {
        // Perform the update logic for the project
        Project updatedProject = projectRepository.save(project);

        // Send SMS notification to the user
        String userPhoneNumber = getUserPhoneNumberFromProject(project);
        String smsMessage = "Your project  " + project.getTitle()+" has been updated! and the project_status is "+project.getProject_status();
        smsService.sendSMSNotification(userPhoneNumber, smsMessage);

        return updatedProject;
    }

    public List<Project> getProjectsApproachingDeadline() {
        Date currentdate=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date thirtyDaysFromNow = calendar.getTime();
        return projectRepository.findByend_dateBetween(currentdate,thirtyDaysFromNow);
    }

    @Scheduled(fixedRate = 86400000) //86400000 ms = 24 hours
    public void sendDeadlineNotifications() {
        List<Project> projectsApproachingDeadline = getProjectsApproachingDeadline();

        for (Project project : projectsApproachingDeadline) {
            if(project.getProject_status()!= Project_status.Completed){
            String userPhoneNumber = getUserPhoneNumberFromProject(project);
            String smsMessage = "The project '" + project.getTitle() + "' is approaching its deadline.";
            smsService.sendSMSNotification(userPhoneNumber, smsMessage);}
            else{
                System.out.println("the project is completed");
            }
        }
    }

    private String getUserPhoneNumberFromProject(Project project) {

        return userRepository.getUserPhoneNumberFromProject(project);
    }
   /* @Override
    public List<Project> searchProjects(String title, Date startDate, Date endDate, Project_status projectStatus) {
        Specification<Project> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null) {
                predicates.add(criteriaBuilder.equal(root.get("title"), title));
            }

            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("start_date"), startDate));
            }

            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("end_date"), endDate));
            }

            if (projectStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("project_status"), projectStatus));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return projectRepository.findAll((Sort) spec);
    }*/
    }


