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
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.DATE)
    private Date reclamation_date;
    private String description;
    @Enumerated(EnumType.STRING)
    private Reclamation_status reclamation_status;
    @ManyToOne
    @JsonIgnore
    private Project projectreclamation;
    @ManyToOne
    @JsonIgnore
    private User user_reclamation;
}
