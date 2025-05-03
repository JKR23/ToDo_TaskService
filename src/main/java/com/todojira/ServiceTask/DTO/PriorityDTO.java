package com.todojira.ServiceTask.DTO;

import com.todojira.ServiceTask.Models.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PriorityDTO implements Serializable {

    private Long id;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 15, message = "name must have min 3, max 15")
    private String name;

    public static PriorityDTO transformToDTO(Priority priority) {
        return PriorityDTO.builder()
                .id(priority.getId())
                .name(priority.getName())
                .build();
    }

    public static Priority transformToEntity(PriorityDTO dto) {
        return Priority.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static List<PriorityDTO> transformToDTO(List<Priority> priorities) {
        return priorities.stream().map(PriorityDTO::transformToDTO).collect(Collectors.toList());
    }

    public static List<Priority> transformToEntity(List<PriorityDTO> prioritiesDto) {
        return prioritiesDto.stream().map(PriorityDTO::transformToEntity).collect(Collectors.toList());
    }
}
