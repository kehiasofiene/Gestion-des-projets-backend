package tn.esprit.gestiondesmanagers.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondesmanagers.Services.ICommentService;
import tn.esprit.gestiondesmanagers.entities.Comment;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Task;
import tn.esprit.gestiondesmanagers.generic.GenericController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/Comment")
public class CommentController extends GenericController<Comment,Integer> {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final ICommentService commentService;

    @PostMapping("addandaffect/{task_id}")
    public String addandaffectcommenttotask(@RequestBody Comment comment,@PathVariable Integer task_id){
        StringBuilder message;
        Set<ConstraintViolation<Comment>> constraintViolations = validator.validate(comment);
        if (constraintViolations.size() > 0 ) {
            message = new StringBuilder("Erreur lors de l'ajout : \n");
            for (ConstraintViolation<Comment> contraintes : constraintViolations) {
                message.append("Champ :").append(contraintes.getPropertyPath()).append(": ").append(contraintes.getMessage()).append("\n");
            }}else {
         commentService.addandaffectcommenttotask(comment, task_id);
            message = new StringBuilder("L'ajout de la commentaire a été effectué avec succès.");
        }
        return message.toString();
    }
}
