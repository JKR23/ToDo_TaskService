package com.todojira.ServiceTask.Services;

import com.todojira.ServiceTask.DTO.TaskDTO;
import com.todojira.ServiceTask.Models.Task;

import java.util.List;

public interface TaskService {
    List<TaskDTO> findAll();
    List<TaskDTO> findTaskByTitle(String title);
    List<TaskDTO> findTasksByStatus_Id(Long statusId);
    List<TaskDTO> findTasksByPriority_Id(Long priorityId);
    String addTask(TaskDTO taskDTO);
    String addTaskFromJsonFile(List<Task> tasks);
    String updateTask(TaskDTO taskDTO);
    String deleteTask(Long id);
}
