package com.todojira.ServiceTask.Services.Impl;

import com.todojira.ServiceTask.DTO.StatusDTO;
import com.todojira.ServiceTask.Models.Status;
import com.todojira.ServiceTask.Repositories.StatusRepository;
import com.todojira.ServiceTask.Services.StatusService;
import com.todojira.ServiceTask.Utils.TransformNameUtils;
import com.todojira.ServiceTask.Validation.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    private final ObjectValidator objectValidator;

    @Override
    public List<StatusDTO> getAllStatus() {
        return StatusDTO.transformToDTO(this.statusRepository.findAll());
    }

    @Override
    public List<StatusDTO> findStatusByNameContainingIgnoreCase(String name) {
        this.objectValidator.validate(name);
        return StatusDTO.transformToDTO(this.statusRepository.findStatusByNameContainingIgnoreCase(name));
    }

    @Override
    public String addStatus(StatusDTO statusDTO) {

        this.objectValidator.validate(statusDTO);

        Status status = StatusDTO.transformToEntity(statusDTO);

        status.setName(TransformNameUtils.transformToUpperCase((statusDTO.getName())));

        this.statusRepository.save(status);

        return "Status " + status.getName() + " added successfully";

    }

    @Override
    public String updateStatus(StatusDTO statusDTO) {
        try {
            this.objectValidator.validate(statusDTO);

            Optional<Status> statusOptional = this.statusRepository.findById(statusDTO.getId());

            if (statusOptional.isEmpty()) {
                throw new EntityNotFoundException("Status not found");
            }

            statusOptional.get().setName(TransformNameUtils.transformToUpperCase(statusDTO.getName()));
            this.statusRepository.save(statusOptional.get());

            return  "Status " + statusOptional.get().getName()+" updated successfully";

        }catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public String deleteStatus(Long id) {
        try {
            this.objectValidator.validate(id);

            Optional<Status> statusOptional = this.statusRepository.findById(id);

            if (statusOptional.isEmpty()) {
                throw new EntityNotFoundException("Status not found");
            }

            this.statusRepository.delete(statusOptional.get());

            return "Status "+ statusOptional.get().getName()+" deleted successfully";

        }catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public String eraseStatus() {
        this.statusRepository.deleteAll();
        return "Status erased successfully";
    }

}
