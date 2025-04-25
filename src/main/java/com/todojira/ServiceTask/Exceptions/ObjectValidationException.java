package com.todojira.ServiceTask.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class ObjectValidationException extends RuntimeException {

    private Set<String> messagesErrorsViolated;
    private String sourceClass;

    @Override
    public String getMessage() {
        return String.join("\n", messagesErrorsViolated);
    }
}
