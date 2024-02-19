package tn.esprit.gestiondesmanagers.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
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
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @NotEmpty(message="title is required")
    private String title;

    @NotNull(message="start_date is required.")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @NotNull(message="end_date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date end_date;

    @AssertTrue(message="La date de fin doit être postérieure à la date de début.")
    public boolean isEndDateAfterStartDate() {
        if (end_date == null || start_date == null) {
            return true;
        }
        return end_date.after(start_date);
    }


    @AssertTrue(message = "Project's start date must be before Task's start date.")
    public boolean isStartDateBeforeTaskStartDate() {
        if (start_date == null || tasks == null || tasks.isEmpty()) {
            return true;
        }
        return tasks.stream().allMatch(task -> task.getStart_date().after(start_date));
    }


    @AssertTrue(message = "Project's end date must be after Task's end date.")
    public boolean isEndDateAfterTaskEndDate() {
        if (end_date == null || tasks == null || tasks.isEmpty()) {
            return true;
        }
        return tasks.stream().allMatch(task -> task.getEnd_date().before(end_date));
    }
    @OneToMany(mappedBy = "projectreclamation")
    private List<Reclamation> reclamations;

    @Enumerated(EnumType.STRING)
    private Project_status project_status;
    @OneToMany(mappedBy ="project",cascade = CascadeType.ALL)
    private List<Task> tasks;

}
