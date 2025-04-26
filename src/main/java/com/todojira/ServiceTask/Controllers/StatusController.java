package com.todojira.ServiceTask.Controllers;

import com.todojira.ServiceTask.DTO.StatusDTO;
import com.todojira.ServiceTask.Services.StatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("api/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;
    private final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAllStatus(){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(statusService.getAllStatus());
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
                .body(statusService.findStatusByNameContainingIgnoreCase(name));
    }

    @PostMapping(path = "/addStatus")
    public ResponseEntity<?> addStatus(@Valid @RequestBody StatusDTO statusDTO, BindingResult result){

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
                .body(statusService.addStatus(statusDTO));
    }

    @PutMapping(path = "/updateStatus")
    public ResponseEntity<?> updateStatus(@Valid @RequestBody StatusDTO statusDTO, BindingResult result){

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
                .body(statusService.updateStatus(statusDTO));
    }

    @DeleteMapping(path = "/deleteStatus/id/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable Long id, BindingResult result){

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
                .body(statusService.deleteStatus(id));
    }

}
