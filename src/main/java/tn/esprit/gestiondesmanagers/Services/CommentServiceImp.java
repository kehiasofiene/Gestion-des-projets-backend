package tn.esprit.gestiondesmanagers.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondesmanagers.entities.Comment;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Task;
import tn.esprit.gestiondesmanagers.generic.IGenericServiceImp;
import tn.esprit.gestiondesmanagers.repositories.CommentRepository;
import tn.esprit.gestiondesmanagers.repositories.TaskRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImp extends IGenericServiceImp<Comment,Integer> implements ICommentService{

    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    @Override
    public Comment addandaffectcommenttotask(Comment comment, Integer task_id) {
        Task task=taskRepository.findById(task_id).orElse(null);
        if(comment!=null && task!=null){
            comment.setTask_comment(task);
            commentRepository.save(comment);
            return comment;
        }
        return null;
    }
}
