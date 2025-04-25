package com.todojira.ServiceTask.Repositories;

import com.todojira.ServiceTask.Models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findStatusByNameContainingIgnoreCase(String name);
    Status findStatusByName(String name);
}
