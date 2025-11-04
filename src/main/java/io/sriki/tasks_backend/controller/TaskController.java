package io.sriki.tasks_backend.controller;

import io.sriki.tasks_backend.model.Task;
import io.sriki.tasks_backend.model.TaskCreationRequest;
import io.sriki.tasks_backend.model.TaskStatus;
import io.sriki.tasks_backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<String> createNewTask(@RequestBody TaskCreationRequest taskCreationRequest) {
        log.info("Task creation request received : {}",taskCreationRequest.toString());
        try {
            taskService.createNewTask(taskCreationRequest);
            return ResponseEntity.ok("Task Created Successfully");
        }
        catch (Exception e) {
            log.error("Error while creating the task : ",e);
            return new ResponseEntity<>("Task Creation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskCreationRequest taskCreationRequest) {
        try {
            Task updatedTask = taskService.updateTask(taskId, taskCreationRequest);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Error while updating the task with id : {}", taskId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<Task> updateStatus(@PathVariable Long taskId, @RequestParam String status) {
        try {
            TaskStatus taskStatus = TaskStatus.valueOf(status);
            Task updatedTask = taskService.updateStatus(taskId, taskStatus);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }
        catch (Exception err) {
            log.error("Error while updating the status for task id : {}", taskId, err);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long taskId) {
        try {
            boolean taskDeletionStatus = taskService.deleteTask(taskId);
            return new ResponseEntity<>(taskDeletionStatus, HttpStatus.OK);
        }
        catch (Exception err) {
            log.error("Error while deleting the task : {}", taskId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
