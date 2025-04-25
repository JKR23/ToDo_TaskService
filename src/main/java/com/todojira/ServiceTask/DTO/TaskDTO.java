package com.todojira.ServiceTask.DTO;

import com.todojira.ServiceTask.Models.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private PriorityDTO priority;

    private StatusDTO status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public static TaskDTO transformToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(PriorityDTO.transformToDTO(task.getPriority()))
                .status(StatusDTO.transformToDTO(task.getStatus()))
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .build();
    }

    public static Task transformToEntity(TaskDTO dto) {
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(PriorityDTO.transformToEntity(dto.getPriority()))
                .status(StatusDTO.transformToEntity(dto.getStatus()))
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    public static List<TaskDTO> transformToDTO(List<Task> tasks) {
        return tasks.stream().map(TaskDTO::transformToDTO).collect(Collectors.toList());
    }

    public static List<Task> transformToEntity(List<TaskDTO> dtoList) {
        return dtoList.stream().map(TaskDTO::transformToEntity).collect(Collectors.toList());
    }
}
