package com.todojira.ServiceTask.DTO;

import com.todojira.ServiceTask.Models.Status;
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
public class StatusDTO implements Serializable {

    private Long id;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 15, message = "name must have min 3, max 15")
    private String name;

    public static StatusDTO transformToDTO(Status status) {
        return StatusDTO.builder()
                .id(status.getId())
                .name(status.getName())
                .build();
    }

    public static Status transformToEntity(StatusDTO dto) {
        return Status.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static List<StatusDTO> transformToDTO(List<Status> statuses) {
        return statuses.stream().map(StatusDTO::transformToDTO).collect(Collectors.toList());
    }

    public static List<Status> transformToEntity(List<StatusDTO> dtoList) {
        return dtoList.stream().map(StatusDTO::transformToEntity).collect(Collectors.toList());
    }
}
