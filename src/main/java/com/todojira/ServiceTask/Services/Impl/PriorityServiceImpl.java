package com.todojira.ServiceTask.Services.Impl;

import com.todojira.ServiceTask.DTO.PriorityDTO;
import com.todojira.ServiceTask.Models.Priority;
import com.todojira.ServiceTask.Repositories.PriorityRepository;
import com.todojira.ServiceTask.Services.PriorityService;
import com.todojira.ServiceTask.Validation.ObjectValidator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriorityServiceImpl implements PriorityService {

    private final PriorityRepository priorityRepository;
    private final ObjectValidator objectValidator;

    @Override
    public List<PriorityDTO> getAllPriority() {
        return PriorityDTO.transformToDTO(this.priorityRepository.findAll());
    }

    @Override
    public List<PriorityDTO> findPriorityByNameContainingIgnoreCase(String name) {
        this.objectValidator.validate(name);
        return PriorityDTO.transformToDTO(this.priorityRepository.findPriorityByNameContainingIgnoreCase(name));
    }

    @Override
    public String addPriority(PriorityDTO priorityDTO) {
        try {
            this.objectValidator.validate(priorityDTO);

            Optional<Priority> priorityOptional = Optional
                    .ofNullable(this.priorityRepository.findPriorityByName(priorityDTO.getName()));

            if (priorityOptional.isPresent()) {
                throw new EntityExistsException("The priority already exists");
            }

            this.priorityRepository.save(PriorityDTO.transformToEntity(priorityDTO));

            return priorityDTO.getName()+" added successfully";

        }catch (EntityExistsException e) {
            throw new EntityExistsException(e.getMessage());
        }
    }

    @Override
    public String updatePriority(PriorityDTO priorityDTO) {
        try {
            this.objectValidator.validate(priorityDTO);

            Optional<Priority> priorityOptional = Optional
                    .ofNullable(this.priorityRepository.findPriorityByName(priorityDTO.getName()));

            if (priorityOptional.isEmpty()) {
                throw new EntityNotFoundException("The priority does not exist with id " + priorityDTO.getId());
            }

            priorityOptional.get().setName(priorityDTO.getName());

            this.priorityRepository.save(priorityOptional.get());

            return priorityDTO.getName()+" updated successfully";
        }catch (EntityExistsException e) {
            throw new EntityExistsException(e.getMessage());
        }
    }

    @Override
    public String deletePriority(Long id) {
        try {
            this.objectValidator.validate(id);

            Optional<Priority> priorityOptional = this.priorityRepository.findById(id);

            if (priorityOptional.isEmpty()) {
                throw new EntityNotFoundException("The priority does not exist with id " + id);
            }

            this.priorityRepository.delete(priorityOptional.get());

            return priorityOptional.get().getName()+" deleted successfully";

        }catch (EntityExistsException e) {
            throw new EntityExistsException(e.getMessage());
        }
    }
}
