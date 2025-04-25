package com.todojira.ServiceTask.Repositories;

import com.todojira.ServiceTask.Models.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    List<Priority> findPriorityByNameContainingIgnoreCase(String name);
    Priority findPriorityByName(String name);
}
