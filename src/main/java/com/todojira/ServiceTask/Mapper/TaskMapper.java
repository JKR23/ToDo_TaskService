package com.todojira.ServiceTask.Mapper;

import com.todojira.ServiceTask.DTO.TaskDTO;
import com.todojira.ServiceTask.Models.Priority;
import com.todojira.ServiceTask.Models.Status;
import com.todojira.ServiceTask.Models.Task;
import com.todojira.ServiceTask.Repositories.PriorityRepository;
import com.todojira.ServiceTask.Repositories.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final Logger logger = LoggerFactory.getLogger(TaskMapper.class);

    public TaskDTO transformToDTO(Task task) {

        logger.info("transforming task to DTO");
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priorityId(task.getPriority() != null ? task.getPriority().getId() : null)
                .statusId(task.getStatus() != null ? task.getStatus().getId() : null)
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .idUser(task.getCreatedBy())
                .build();
    }

    public Task transformToEntity(TaskDTO dto) {

        logger.info("fetching priority from the id {}", dto.getId());
        Priority priority = priorityRepository.findById(dto.getPriorityId())
                .orElseThrow(() -> new EntityNotFoundException("Priority with id " + dto.getPriorityId() + " not found"));

        logger.info("fetching status from the id {}", dto.getStatusId());
        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new EntityNotFoundException("Status with id " + dto.getStatusId() + " not found"));

        logger.info("transforming dto to entity");
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(priority)
                .status(status)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .createdBy(dto.getIdUser())
                .build();

    }

    public List<TaskDTO> transformToDTO(List<Task> taskList) {
        return taskList.stream().map(this::transformToDTO).collect(Collectors.toList());
    }

    public List<Task> transformToEntity(List<TaskDTO> taskDTOList) {
        return taskDTOList.stream().map(this::transformToEntity).collect(Collectors.toList());
    }
}

