package com.todojira.ServiceTask.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todojira.ServiceTask.DTO.TaskDTO;
import com.todojira.ServiceTask.Models.Task;
import com.todojira.ServiceTask.Services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController("api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.findAll());
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
                .body(taskService.findTaskByTitle(name));
    }

    @GetMapping(path = "/getByStatus/id/{statusId}")
    public ResponseEntity<?> getByName(@PathVariable Long statusId, BindingResult result){

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
                .body(taskService.findTasksByStatus_Id(statusId));
    }

    @GetMapping(path = "/getByPriority/id/{priorityId}")
    public ResponseEntity<?> getByPriority(@PathVariable Long priorityId, BindingResult result){

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
        try{
            /// create reader file
            ObjectMapper mapper = new ObjectMapper();
            List<Task> taskList = mapper.readValue(file.getInputStream(), new TypeReference<>() {
            });

            String result = this.taskService.addTaskFromJsonFile(taskList);

            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .body(result);
        }catch (IOException e){
            throw new RuntimeException("Error while reading tasks");
        }catch (Exception e){
            throw new RuntimeException("Error while uploading tasks");
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
    public ResponseEntity<?> deleteTask(@PathVariable Long id, BindingResult result){

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
                .body(taskService.deleteTask(id));
    }
}
