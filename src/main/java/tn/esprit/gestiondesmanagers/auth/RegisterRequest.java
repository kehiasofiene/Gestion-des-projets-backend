package tn.esprit.gestiondesmanagers.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.gestiondesmanagers.entities.Department;
import tn.esprit.gestiondesmanagers.entities.Gender;

import java.math.BigInteger;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String role;

    private Date birth_date;

    private Integer cin;

    private String confirmpassword;

    private String nationality;

    private Department department;

    private Gender gender;

    private String phone_number;

    private String work_post;

    private String login;

    private String image;

}
