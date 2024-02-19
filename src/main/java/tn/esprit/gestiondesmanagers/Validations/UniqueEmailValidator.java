package tn.esprit.gestiondesmanagers.Validations;

import tn.esprit.gestiondesmanagers.Services.UserServiceImp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private UserServiceImp userService; // Injectez votre service utilisateur ici

    public UniqueEmailValidator(UserServiceImp userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !userService.emailExists(email);
    }
}
