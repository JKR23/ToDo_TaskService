package com.todojira.ServiceTask.Exceptions.GlobalHandlerException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RepresentationException {
    private String error;
    private String source;
}
