package io.sriki.tasks_backend.service;

import io.sriki.tasks_backend.model.Task;
import io.sriki.tasks_backend.model.TaskCreationRequest;
import io.sriki.tasks_backend.model.TaskStatus;
import io.sriki.tasks_backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public void createNewTask(TaskCreationRequest taskCreationRequest) {
        Task newTask = Task.builder()
                .title(taskCreationRequest.title())
                .description(taskCreationRequest.description())
                .taskStatus(TaskStatus.TODO)
                .createdTime(LocalDateTime.now())
                .build();
        taskRepository.save(newTask);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTask(Long taskId, TaskCreationRequest taskCreationRequest) {
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if(existingTaskOptional.isEmpty()) {
            log.error("Task with id {} not found for updating task", taskId);
            return null;
        }
        Task existingTask = existingTaskOptional.get();
        existingTask.setTitle(taskCreationRequest.title());
        existingTask.setDescription(taskCreationRequest.description());
        return taskRepository.save(existingTask);
    }


    public Task updateStatus(Long taskId, TaskStatus status) {
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if(existingTaskOptional.isEmpty()) {
            log.error("Task with id {} not found for updating status", taskId);
            return null;
        }
        Task existingTask = existingTaskOptional.get();
        existingTask.setTaskStatus(status);
        return taskRepository.save(existingTask);
    }

    public boolean deleteTask(Long taskId) {
        if(!taskRepository.existsById(taskId)) {
            log.info("Task with id {} not found for deletion", taskId);
            return false;
        }
        taskRepository.deleteById(taskId);
        return true;
    }
}
