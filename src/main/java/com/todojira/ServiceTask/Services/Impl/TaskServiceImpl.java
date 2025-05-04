package com.todojira.ServiceTask.Services.Impl;

import com.todojira.ServiceTask.Configuration.Utils.SecurityUtils;
import com.todojira.ServiceTask.DTO.TaskDTO;
import com.todojira.ServiceTask.Mapper.TaskMapper;
import com.todojira.ServiceTask.Models.Task;
import com.todojira.ServiceTask.Repositories.PriorityRepository;
import com.todojira.ServiceTask.Repositories.StatusRepository;
import com.todojira.ServiceTask.Repositories.TaskRepository;
import com.todojira.ServiceTask.Services.TaskService;
import com.todojira.ServiceTask.Validation.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ObjectValidator objectValidator;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDTO> findAll() {
        return taskMapper.transformToDTO(this.taskRepository.findAll());
    }

    @Override
    public List<TaskDTO> findAllByUserId() {
        Long createdBy = SecurityUtils.getCurrentUserId();
        return taskMapper.transformToDTO(taskRepository.findTasksByCreatedBy(createdBy));
    }

    /**
     * @param title the title of the task to find*/
    @Override
    public List<TaskDTO> findTaskByTitle(String title) {
        return taskMapper.transformToDTO(this.taskRepository.findTasksByTitleContainingIgnoreCase(title));
    }

    @Override
    public List<TaskDTO> findTasksByStatus_Id(Long statusId) {
        return taskMapper.transformToDTO(this.taskRepository.findTasksByStatus_Id(statusId));
    }

    @Override
    public List<TaskDTO> findTasksByPriority_Id(Long priorityId) {
        return taskMapper.transformToDTO(this.taskRepository.findTasksByPriority_Id(priorityId));
    }

    /**
     * @param taskDTO the new Task to persist*/
    @Override
    @Transactional
    public String addTask(TaskDTO taskDTO) {
        try{
            this.objectValidator.validate(taskDTO);

            Long idUser = SecurityUtils.getCurrentUserId();

            this.objectValidator.validate(idUser);

            taskDTO.setIdUser(idUser);

            Task task = taskMapper.transformToEntity(taskDTO);

            if (task.getEndDate()!=null || task.getStartDate()!=null){
                if (!isEndDateSuperiorToStartDate(task)){
                    throw new IllegalArgumentException("End date should be before start date");
                }
            }

            this.taskRepository.save(treatPriorityAndStatusTask(task));

            return "Task "+ taskDTO.getTitle()+ " added successfully";
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param tasks the tasks fetch from json file*/
    @Override
    @Transactional
    public String addTaskFromJsonFile(List<Task> tasks) {
        this.objectValidator.validate(tasks);

        /// retains jus tasks endDate > startDate
        List<Task> taskList = tasks.stream()
                .filter(this::isEndDateSuperiorToStartDate)
                .toList();

        this.taskRepository.saveAll(taskList);

        return "List task added successfully";
    }

    /**
     * @param taskDTO contains the new values to update in the task*/
    @Override
    public String updateTask(TaskDTO taskDTO) {
        try {

            this.objectValidator.validate(taskDTO);

            Long createdBy = SecurityUtils.getCurrentUserId();

            /// if task isValid we fetch it
            Optional<Task> task = taskRepository.findTaskByIdAndCreatedBy(taskDTO.getId(),createdBy);

            if (task.isEmpty()){
                throw new EntityNotFoundException("Task not found or user not connected");
            }

            /// updating task
            task.get().setTitle(taskDTO.getTitle());
            task.get().setDescription(taskDTO.getDescription());
            task.get().setPriority(
                    this.priorityRepository.findById(taskDTO.getPriorityId())
                            .orElseThrow(() ->
                                    new EntityNotFoundException("Priority with id " + taskDTO.getPriorityId() + " not found")
                            )
            );
            task.get().setStatus(this.statusRepository.findById(taskDTO.getStatusId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Status with id " +  taskDTO.getStatusId() + " not found")
                    )
            );
            task.get().setStartDate(taskDTO.getStartDate());
            task.get().setEndDate(taskDTO.getEndDate());

            if (!isEndDateSuperiorToStartDate(task.get())){
                throw new IllegalArgumentException("endDate must be superior to startDate");
            }

            this.taskRepository.save(treatPriorityAndStatusTask(task.get()));

            return "Task "+ taskDTO.getTitle()+ " updated successfully";

        }catch (EntityNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String deleteTask(Long id) {
        try {
            this.objectValidator.validate(id);

            Long createdBy = SecurityUtils.getCurrentUserId();

            /// if task isValid we fetch it
            Optional<Task> task = taskRepository.findTaskByIdAndCreatedBy(id,createdBy);

            if (task.isEmpty()){
                throw new EntityNotFoundException("Task not found or user not connected");
            }

            this.taskRepository.delete(task.get());

            return "Task "+ task.get().getTitle()+ " deleted successfully";
        }catch (EntityNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String eraseAllTasks() {
        List<Task> tasks = this.taskRepository.findTasksByCreatedBy(SecurityUtils.getCurrentUserId());
        taskRepository.deleteAll(tasks);
        return "All tasks deleted successfully";
    }

    @Override
    public List<Task> findTasksByPriorityIdAndCreatedBy(Long priorityId) {
        this.objectValidator.validate(priorityId);

        Long createdBy = SecurityUtils.getCurrentUserId();

        this.objectValidator.validate(createdBy);

        return this.taskRepository.findTasksByPriority_IdAndCreatedBy(priorityId,createdBy);
    }

    @Override
    public List<Task> findTasksByStatusIdAndCreatedBy(Long statusId) {
        this.objectValidator.validate(statusId);

        Long createdBy = SecurityUtils.getCurrentUserId();

        this.objectValidator.validate(createdBy);

        return this.taskRepository.findTaskByStatus_IdAndCreatedBy(statusId,createdBy);
    }

    @Override
    public List<Task> findTasksByTitleContainingIgnoreCaseAndCreatedBy(String title) {
        this.objectValidator.validate(title);

        Long createdBy = SecurityUtils.getCurrentUserId();

        this.objectValidator.validate(createdBy);

        return this.taskRepository.findTasksByTitleContainingIgnoreCaseAndCreatedBy(title,createdBy);
    }

    private Task treatPriorityAndStatusTask(Task task){

        if (task.getStatus() == null){
            task.setStatus(this.statusRepository.findStatusByName("TODO"));

            if (task.getStatus()==null){
                throw new EntityNotFoundException("Status TODO doesn't exist");
            }
        }

        if (task.getPriority() == null){
            task.setPriority(this.priorityRepository.findPriorityByName("Medium"));

            if (task.getPriority()==null){
                throw new EntityNotFoundException("Priority Medium doesn't exist");
            }
        }

        return task;
    }

    private boolean isEndDateSuperiorToStartDate(Task task){
        return task.getEndDate().isAfter(task.getStartDate());
    }
}
