package com.todojira.ServiceTask.Controllers;

import com.todojira.ServiceTask.DTO.PriorityDTO;
import com.todojira.ServiceTask.Services.PriorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("api/priority/")
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityService priorityService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAllStatus(){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(priorityService.getAllPriority());
    }

    @GetMapping(path = "/getByName")
    public ResponseEntity<?> getByName(@RequestParam String name, BindingResult result){

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(404))
                    .body(errors);
        }

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(priorityService.findPriorityByNameContainingIgnoreCase(name));
    }

    @PostMapping(path = "/addPriority")
    public ResponseEntity<?> addPriority(@Valid @RequestBody PriorityDTO priorityDTO, BindingResult result){

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(404))
                    .body(errors);
        }

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(priorityService.addPriority(priorityDTO));
    }

    @PutMapping(path = "/updatePriority")
    public ResponseEntity<?> updatePriority(@Valid @RequestBody PriorityDTO priorityDTO, BindingResult result){

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(404))
                    .body(errors);
        }

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(priorityService.updatePriority(priorityDTO));
    }

    @DeleteMapping(path = "/deletePriority/id/{id}")
    public ResponseEntity<?> deletePriority(@PathVariable Long id, BindingResult result){

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(404))
                    .body(errors);
        }

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(priorityService.deletePriority(id));
    }
}
