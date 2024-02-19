package tn.esprit.gestiondesmanagers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestiondesmanagers.entities.Comment;
import tn.esprit.gestiondesmanagers.entities.Project;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
