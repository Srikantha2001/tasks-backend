package io.sriki.tasks_backend.repository;

import io.sriki.tasks_backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
