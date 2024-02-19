package tn.esprit.gestiondesmanagers.Services;

import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Under_Comment;
import tn.esprit.gestiondesmanagers.generic.IGenericService;

public interface IUnderCommentService extends IGenericService<Under_Comment,Integer> {
    public Under_Comment addandaffectundercommenttocommentanduser(Under_Comment under_comment,Integer user_id,Integer comment_id);
}
