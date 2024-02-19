package tn.esprit.gestiondesmanagers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message="content is required")
    private String content;
    @Temporal(TemporalType.DATE)
    @NotNull(message="publishedAt is required")
    private Date publishedAt;
    @OneToMany(mappedBy = "comment",cascade = CascadeType.ALL)
    private List<Under_Comment> underComments;
    @ManyToOne
    @JsonIgnore
    private Task task_comment;
}
