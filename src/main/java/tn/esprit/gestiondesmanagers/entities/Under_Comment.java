package tn.esprit.gestiondesmanagers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Under_Comment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @Temporal(TemporalType.DATE)
    private Date publishedAt;
    @JsonIgnore
    @ManyToOne
    private Comment comment;
    @JsonIgnore
    @ManyToOne
    private User user_under_comment;
}
