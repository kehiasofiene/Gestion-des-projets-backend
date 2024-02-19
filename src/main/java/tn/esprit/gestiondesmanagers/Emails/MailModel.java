package tn.esprit.gestiondesmanagers.Emails;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MailModel {
    @NotNull
    private String subject;
    @NotNull
    private String text;
    @NotNull
    private String from;
    @NotNull
    private String to;

    private String htmlContent;

    private boolean isHTML = false;
}
