package tn.esprit.gestiondesmanagers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.gestiondesmanagers.Token.Token;
import tn.esprit.gestiondesmanagers.Validations.UniqueEmail;
//import tn.esprit.gestiondesmanagers.Validations.PasswordMatches;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

import static java.util.List.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@PasswordMatches
public class User extends Throwable implements Serializable ,UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "lastname is required")
    private String lastname;
    @NotEmpty(message = "firstname is required")
    private String firstname;
    @NotNull(message = "cin is required")
    @Digits(integer = 8, fraction = 0, message = "cin must be an 8-digit number")
    private int cin;
    @Temporal(TemporalType.DATE)
    private Date birth_date;
    //@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Pattern(regexp = ".+@gmail\\.com", message = "Email must be a valid Gmail address")
    @Column(unique = true)
   // @UniqueEmail
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotBlank(message = "work_post is required")
    private String work_post;
    @NotBlank(message = "phone_number is required")
    //@Digits(integer = 12, fraction = 0, message = "phone_number must be an 8-digit number")
    private String phone_number;
    @NotBlank(message = "nationnality is required")
    private String nationality;
    @NotBlank(message = " login is required")
    private String login;
    private String image;
    @NotBlank(message = "password is required")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;
    @NotBlank(message = "confirmpassword is required")
    @Size(min = 6, message = "confirmpassword should have at least 6 characters")
    private String confirmpassword;
    private String role;

    @OneToMany(mappedBy = "user_reclamation")
    private List<Reclamation> reclamations;

    @OneToMany(mappedBy = "user_under_comment")
    private List<Under_Comment> under_comments;


    @JsonIgnore
    @ManyToOne
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "usertask")
    private List<Task> tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Token> tokens;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(Arrays.asList(new SimpleGrantedAuthority(role)));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

   // @AssertTrue(message = "Password and confirm password must match")
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(confirmpassword);
    }

}
