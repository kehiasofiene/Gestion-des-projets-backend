package tn.esprit.gestiondesmanagers.Validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse> errors = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            ValidationErrorResponse error = new ValidationErrorResponse(fieldError.getField(), fieldError.getDefaultMessage());
            errors.add(error);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleBindExceptions(BindException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createValidationErrors(ex.getBindingResult()));
    }

    private List<ValidationErrorResponse> createValidationErrors(BindingResult bindingResult) {
        List<ValidationErrorResponse> validationErrors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error ->
                validationErrors.add(new ValidationErrorResponse(error.getField(), error.getDefaultMessage())));
        return validationErrors;
    }
}
