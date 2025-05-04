package com.todojira.ServiceTask.Repositories;

import com.todojira.ServiceTask.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTasksByTitleContainingIgnoreCase(String title);

    List<Task> findTasksByStatus_Id(Long statusId);

    List<Task> findTasksByPriority_Id(Long priorityId);

    List<Task> findTasksByTitleContainingIgnoreCaseAndCreatedBy(String title, Long createdBy);

    List<Task> findTaskByStatus_IdAndCreatedBy(Long statusId, Long createdBy);

    List<Task> findTasksByPriority_IdAndCreatedBy(Long priorityId, Long createdBy);

    Optional<Task> findTaskByIdAndCreatedBy(Long id, Long createdBy);

    List<Task> findTasksByCreatedBy(Long id);
}
