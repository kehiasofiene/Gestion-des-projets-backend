package tn.esprit.gestiondesmanagers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestiondesmanagers.entities.Project;
import tn.esprit.gestiondesmanagers.entities.Under_Comment;

public interface Under_CommentRepository extends JpaRepository<Under_Comment, Integer> {

}
