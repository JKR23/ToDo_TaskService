package com.todojira.ServiceTask.DTO;

import com.todojira.ServiceTask.Models.Priority;
import com.todojira.ServiceTask.Models.Status;
import com.todojira.ServiceTask.Models.Task;
import com.todojira.ServiceTask.Repositories.PriorityRepository;
import com.todojira.ServiceTask.Repositories.StatusRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskDTO implements Serializable {
    private Long id;

    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be blank")
    @Size(min = 3, max = 15, message = "title must have min 3, max 15")
    private String title;

    private String description;

    private Long priorityId;

    private Long statusId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
