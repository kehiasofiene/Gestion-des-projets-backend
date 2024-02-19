package tn.esprit.gestiondesmanagers.Services;

import tn.esprit.gestiondesmanagers.entities.Comment;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Task;
import tn.esprit.gestiondesmanagers.generic.IGenericService;

public interface ICommentService extends IGenericService<Comment,Integer> {
    public Comment addandaffectcommenttotask(Comment comment,Integer task_id);
}
