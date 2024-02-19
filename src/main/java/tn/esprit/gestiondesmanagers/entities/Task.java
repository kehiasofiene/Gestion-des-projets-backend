package tn.esprit.gestiondesmanagers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message="title is required")
    private String title;
    private String description;

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
            return true; // Null values are handled by @NotNull
        }
        return end_date.after(start_date);
    }

    @AssertTrue(message = "Task's start date must be after Project's start date.")
    public boolean isStartDateAfterProjectStartDate() {
        if (start_date == null || project == null || project.getStart_date() == null) {
            return true; // Null values are handled by @NotNull
        }
        return start_date.after(project.getStart_date());
    }


    @AssertTrue(message = "Task's end date must be before Project's end date.")
    public boolean isEndDateBeforeProjectEndDate() {
        if (end_date == null || project == null || project.getEnd_date() == null) {
            return true; // Null values are handled by @NotNull
        }
        return end_date.before(project.getEnd_date());
    }

    @NotNull(message = "Task_status is required")
    @Enumerated(EnumType.STRING)
    private Task_Status task_status;
    @JsonIgnore
    @ManyToOne
    private Project project;
    @JsonIgnore
    @ManyToOne
    private User usertask;
    @OneToMany(mappedBy = "task_comment",cascade = CascadeType.ALL)
    private List<Comment> comments;
}
