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
import java.util.List;
import java.util.stream.Collectors;

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

    public static HistoricDTO transformToDTO(Historic historic) {
        return HistoricDTO.builder()
                .id(historic.getId())
                .actionType(historic.getActionType())
                .taskDTO(TaskDTO.transformToDTO(historic.getTask()))
                .build();
    }

    public static Historic transformToEntity(HistoricDTO historicDTO) {
        return Historic.builder()
                .id(historicDTO.getId())
                .actionType(historicDTO.getActionType())
                .task(TaskDTO.transformToEntity(historicDTO.getTaskDTO()))
                .build();
    }

    public static List<HistoricDTO> transformToDTO(List<Historic> historicList) {
        return historicList.stream().map(HistoricDTO::transformToDTO).collect(Collectors.toList());
    }

    public static List<Historic> transformToEntity(List<HistoricDTO> historicDTOList) {
        return historicDTOList.stream().map(HistoricDTO::transformToEntity).collect(Collectors.toList());
    }
}
