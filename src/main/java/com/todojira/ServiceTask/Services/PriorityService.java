package com.todojira.ServiceTask.Services;

import com.todojira.ServiceTask.DTO.PriorityDTO;

import java.util.List;

public interface PriorityService {
    List<PriorityDTO> getAllPriority();
    List<PriorityDTO> findPriorityByNameContainingIgnoreCase(String name);
    String addPriority(PriorityDTO priorityDTO);
    String updatePriority(PriorityDTO priorityDTO);
    String deletePriority(Long id);
}
