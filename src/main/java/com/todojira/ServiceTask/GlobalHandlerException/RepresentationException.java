package com.todojira.ServiceTask.GlobalHandlerException;

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
}
