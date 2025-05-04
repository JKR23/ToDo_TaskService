package com.todojira.ServiceTask.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskDTO implements Serializable {
    private Long id;

    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be blank")
    @Size(min = 3, max = 100, message = "title must have min 3, max 100")
    private String title;

    private String description;

    private Long priorityId;

    private Long statusId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Setter
    private Long idUser;

}
