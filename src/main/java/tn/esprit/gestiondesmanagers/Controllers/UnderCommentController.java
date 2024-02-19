package tn.esprit.gestiondesmanagers.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestiondesmanagers.Services.IUnderCommentService;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Under_Comment;
import tn.esprit.gestiondesmanagers.generic.GenericController;

@RestController
@AllArgsConstructor
@RequestMapping("/UnderComment")
public class UnderCommentController extends GenericController<Department,Integer> {
    private final IUnderCommentService underCommentService;
    @PostMapping("addandaffect/{user_id}/{comment_id}")
    public Under_Comment addandaffectundercommenttocommentanduser(@RequestBody Under_Comment under_comment, @PathVariable Integer user_id,@PathVariable Integer comment_id){
       return underCommentService.addandaffectundercommenttocommentanduser(under_comment, user_id, comment_id);
    }
}
