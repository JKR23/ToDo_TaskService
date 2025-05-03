package com.todojira.ServiceTask.DTO;

import com.todojira.ServiceTask.Models.Historic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HistoricDTO implements Serializable {

    private Long id;

    @NotNull(message = "actionType cannot be null")
    @NotBlank(message = "actionType cannot be blank")
    @Size(max = 16, message = "actionType must have max 16")
    private String actionType;  // ex: TASK_CREATED, STATUS_CHANGED, PRIORITY_CHANGED

    private TaskDTO taskDTO;
}
