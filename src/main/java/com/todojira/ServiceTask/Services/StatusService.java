package com.todojira.ServiceTask.Services;

import com.todojira.ServiceTask.DTO.StatusDTO;

import java.util.List;

public interface StatusService {
    List<StatusDTO> getAllStatus();
    List<StatusDTO> findStatusByNameContainingIgnoreCase(String name);
    String addStatus(StatusDTO statusDTO);
    String updateStatus(StatusDTO statusDTO);
    String deleteStatus(Long id);
    String eraseStatus();
}
