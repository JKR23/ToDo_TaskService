package com.todojira.ServiceTask.Services.Impl;

import com.todojira.ServiceTask.DTO.PriorityDTO;
import com.todojira.ServiceTask.DTO.StatusDTO;
import com.todojira.ServiceTask.DTO.TaskDTO;
import com.todojira.ServiceTask.Models.Task;
import com.todojira.ServiceTask.Repositories.PriorityRepository;
import com.todojira.ServiceTask.Repositories.StatusRepository;
import com.todojira.ServiceTask.Repositories.TaskRepository;
import com.todojira.ServiceTask.Services.TaskService;
import com.todojira.ServiceTask.Validation.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ObjectValidator objectValidator;

    @Override
    public List<TaskDTO> findAll() {
        return TaskDTO.transformToDTO(this.taskRepository.findAll());
    }

    /**
     * @param title the title of the task to find*/
    @Override
    public List<TaskDTO> findTaskByTitle(String title) {
        return TaskDTO.transformToDTO(this.taskRepository.findTasksByTitleContainingIgnoreCase(title));
    }

    @Override
    public List<TaskDTO> findTasksByStatus_Id(Long statusId) {
        return TaskDTO.transformToDTO(this.taskRepository.findTasksByStatus_Id(statusId));
    }

    @Override
    public List<TaskDTO> findTasksByPriority_Id(Long priorityId) {
        return TaskDTO.transformToDTO(this.taskRepository.findTasksByPriority_Id(priorityId));
    }

    /**
     * @param taskDTO the new Task to persist*/
    @Override
    public String addTask(TaskDTO taskDTO) {
        try{
            this.objectValidator.validate(taskDTO);
            this.taskRepository.save(TaskDTO.transformToEntity(taskDTO));
            return "Task "+ taskDTO.getTitle()+ " added successfully";
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param taskDTOs the tasks fetch from json file*/
    @Override
    public String addTaskFromJsonFile(List<TaskDTO> taskDTOs) {
        this.objectValidator.validate(taskDTOs);
        this.taskRepository.saveAll(TaskDTO.transformToEntity(taskDTOs));
        return "List task added successfully";
    }

    /**
     * @param taskDTO contains the new values to update in the task*/
    @Override
    public String updateTask(TaskDTO taskDTO) {
        try {

            this.objectValidator.validate(taskDTO);

            /// if task isValid we fetch it
            Optional<Task> task = taskRepository.findById(taskDTO.getId());

            if (task.isEmpty()){
                throw new EntityNotFoundException("Task with id " + taskDTO.getId() + " not found");
            }

            /// updating task
            task.get().setTitle(taskDTO.getTitle());
            task.get().setDescription(taskDTO.getDescription());
            task.get().setPriority(PriorityDTO.transformToEntity(taskDTO.getPriority()));
            task.get().setStatus(StatusDTO.transformToEntity(taskDTO.getStatus()));
            task.get().setStartDate(taskDTO.getStartDate());
            task.get().setEndDate(taskDTO.getEndDate());

            this.taskRepository.save(task.get());

            return "Task "+ taskDTO.getTitle()+ " updated successfully";

        }catch (EntityNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String deleteTask(Long id) {
        try {
            this.objectValidator.validate(id);

            Optional<Task> task = this.taskRepository.findById(id);

            if (task.isEmpty()){
                throw new EntityNotFoundException("Task with id " + id + " not found");
            }

            this.taskRepository.delete(task.get());

            return "Task "+ task.get().getTitle()+ " deleted successfully";
        }catch (EntityNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
