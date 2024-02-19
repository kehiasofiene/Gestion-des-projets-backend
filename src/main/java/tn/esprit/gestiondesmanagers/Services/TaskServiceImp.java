package tn.esprit.gestiondesmanagers.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Task;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.IGenericServiceImp;
import tn.esprit.gestiondesmanagers.repositories.ProjectRepository;
import tn.esprit.gestiondesmanagers.repositories.TaskRepository;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class TaskServiceImp extends IGenericServiceImp<Task,Integer> implements ITaskService{

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Override
    public Task addandaffecttasktoproject(Task task, Integer project_id,Integer user_id) {
        User user=userRepository.findById(user_id).orElse(null);
        Project project=projectRepository.findById(project_id).orElse(null);
        if(task!=null && project!=null && user!=null){
            task.setProject(project);
            task.setUsertask(user);
            taskRepository.save(task);
            return  task;
        }
        return null;
    }
}
