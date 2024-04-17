package pl.micede.personalapi.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<PasswordStrengthChecker, String> {

    private static final String NUMBER_PATTERN = ".*\\d.*";

    @Override
    public void initialize(PasswordStrengthChecker constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(NUMBER_PATTERN);

    }
}
