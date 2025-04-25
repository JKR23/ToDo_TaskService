package com.todojira.ServiceTask.Repositories;

import com.todojira.ServiceTask.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTasksByTitleContainingIgnoreCase(String title);

    List<Task> findTasksByStatus_Id(Long statusId);

    List<Task> findTasksByPriority_Id(Long priorityId);
}
