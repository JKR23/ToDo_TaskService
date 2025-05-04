package com.todojira.ServiceTask.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todojira.ServiceTask.DTO.TaskDTO;
import com.todojira.ServiceTask.Mapper.TaskMapper;
import com.todojira.ServiceTask.Services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findAll());
    }

    @GetMapping(path = "/getAllByUserConnected")
    public ResponseEntity<?> getAllByUserConnected(){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findAllByUserId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/getByName")
    public ResponseEntity<?> getByName(@RequestParam String name){

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findTaskByTitle(name));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/getByStatus/id/{statusId}")
    public ResponseEntity<?> getByStatus(@PathVariable Long statusId){

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findTasksByStatus_Id(statusId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/getByPriority/id/{priorityId}")
    public ResponseEntity<?> getByPriority(@PathVariable Long priorityId){

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findTasksByPriority_Id(priorityId));
    }

    @PostMapping(path = "/addTask")
    public ResponseEntity<?> addTask(@Valid @RequestBody TaskDTO taskDTO, BindingResult result){

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
                .body(taskService.addTask(taskDTO));
    }

    @PostMapping(path = "/uploadTasks")
    public ResponseEntity<?> uploadTasks(@RequestParam("file") MultipartFile file){

        if (file.getSize() > 5_000_000) { // 5 MB
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("File too large. Max allowed size is 1MB.");
        }

        try{
            /// create reader file
            ObjectMapper mapperReaderFile = new ObjectMapper();

            /// for helping in deserialization LocalDateTime
            mapperReaderFile.registerModule(new JavaTimeModule());
            mapperReaderFile.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            /// fetch tasks from file
            List<TaskDTO> taskDTOList = mapperReaderFile.readValue(file.getInputStream(), new TypeReference<>() {
            });

            /// recover the result of that operation
            String result = this.taskService.addTaskFromJsonFile(this.taskMapper.transformToEntity(taskDTOList));

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .body(result);
        }catch (IOException e){
            throw new RuntimeException("Error while reading tasks");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping(path = "/updateTask")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskDTO taskDTO, BindingResult result){

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
                .body(taskService.updateTask(taskDTO));
    }

    @DeleteMapping(path = "/deleteTask/id/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.deleteTask(id));
    }

    @GetMapping(path = "/current-user/getTaskByName")
    public ResponseEntity<?> findTasksByTitleContainingIgnoreCaseAndCreatedBy(@RequestParam String name){

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findTasksByTitleContainingIgnoreCaseAndCreatedBy(name));
    }

    @GetMapping(path = "/current-user/getTaskByStatus/id/{statusId}")
    public ResponseEntity<?> findTaskByStatus_IdAndCreatedBy(@PathVariable Long statusId){

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findTasksByStatusIdAndCreatedBy(statusId));
    }

    @GetMapping(path = "/current-user/getTaskByPriority/id/{priorityId}")
    public ResponseEntity<?> findTasksByPriority_IdAndCreatedBy(@PathVariable Long priorityId){

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findTasksByPriorityIdAndCreatedBy(priorityId));
    }

    @DeleteMapping(path = "/eraseTasks")
    public ResponseEntity<?> eraseTasks(){

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.eraseAllTasks());
    }
}
