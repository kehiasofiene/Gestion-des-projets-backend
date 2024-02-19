package tn.esprit.gestiondesmanagers.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gestiondesmanagers.entities.Comment;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Under_Comment;
import tn.esprit.gestiondesmanagers.entities.User;
import tn.esprit.gestiondesmanagers.generic.IGenericServiceImp;
import tn.esprit.gestiondesmanagers.repositories.CommentRepository;
import tn.esprit.gestiondesmanagers.repositories.Under_CommentRepository;
import tn.esprit.gestiondesmanagers.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UnderCommentServiceImp extends IGenericServiceImp<Under_Comment,Integer> implements IUnderCommentService{
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final Under_CommentRepository under_commentRepository;

    @Override
    public Under_Comment addandaffectundercommenttocommentanduser(Under_Comment under_comment, Integer user_id, Integer comment_id) {
        User user=userRepository.findById(user_id).orElse(null);
        Comment comment=commentRepository.findById(comment_id).orElse(null);
        if(user!=null && comment!=null && under_comment!=null){
            under_comment.setComment(comment);
            under_comment.setUser_under_comment(user);
            under_commentRepository.save(under_comment);
            return under_comment;
        }
        return null;
    }
}
