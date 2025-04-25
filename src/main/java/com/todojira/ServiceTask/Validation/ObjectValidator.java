package com.todojira.ServiceTask.Validation;

import com.todojira.ServiceTask.Exceptions.ObjectValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator {

    ///build ValidatorFactory
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    /// create validator from ValidatorFactory
    private final Validator validator = factory.getValidator();

    public <T> void validate(T objectToValidate) {

        /// fetch all violations
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(objectToValidate);

        if (!constraintViolations.isEmpty()) {
            /// fetch message violation
            Set<String> violations = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());

            throw new ObjectValidationException(violations, objectToValidate.getClass().getName());
        }
    }
}
