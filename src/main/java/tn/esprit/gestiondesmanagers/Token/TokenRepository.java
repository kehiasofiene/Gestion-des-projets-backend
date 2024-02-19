package tn.esprit.gestiondesmanagers.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gestiondesmanagers.entities.Project;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    @Query("select t from Token t inner join User u on t.user.id = u.id where u.id=:user_id and (t.expired = false or t.revoked =false)")
    List<Token> findAllValidTokensByUser(Integer user_id);

    Optional<Token> findByToken(String token);
}
