package com.todojira.ServiceTask.Services;

import com.todojira.ServiceTask.DTO.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> findAll();
    List<TaskDTO> findTaskByTitle(String title);
    String addTask(TaskDTO taskDTO);
    String addTaskFromJsonFile(List<TaskDTO> taskDTOs);
    String updateTask(TaskDTO taskDTO);
    String deleteTask(Long id);
}
