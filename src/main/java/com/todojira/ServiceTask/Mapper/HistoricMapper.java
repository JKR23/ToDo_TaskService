package com.todojira.ServiceTask.Mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HistoricMapper {

    private final TaskMapper taskMapper;

    public HistoricDTO transformToDTO(Historic historic) {
        return HistoricDTO.builder()
                .id(historic.getId())
                .actionType(historic.getActionType())
                .taskDTO(taskMapper.transformToDTO(historic.getTask()))
                .build();
    }

    public Historic transformToEntity(HistoricDTO historicDTO) {
        return Historic.builder()
                .id(historicDTO.getId())
                .actionType(historicDTO.getActionType())
                .task(taskMapper.transformToEntity(historicDTO.getTaskDTO()))
                .build();
    }

    public List<HistoricDTO> transformToDTO(List<Historic> historicList) {
        return historicList.stream().map(this::transformToDTO).collect(Collectors.toList());
    }

    public List<Historic> transformToEntity(List<HistoricDTO> historicDTOList) {
        return historicDTOList.stream().map(this::transformToEntity).collect(Collectors.toList());
    }
}
