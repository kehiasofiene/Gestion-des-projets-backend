package tn.esprit.gestiondesmanagers.Services;

import tn.esprit.gestiondesmanagers.entities.Task;
import tn.esprit.gestiondesmanagers.generic.IGenericService;

public interface ITaskService extends IGenericService<Task,Integer>  {
    public Task addandaffecttasktoproject(Task task,Integer project_id,Integer user_id);
}
