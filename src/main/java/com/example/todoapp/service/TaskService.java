package com.example.todoapp.service;

import com.example.todoapp.entity.Task;
import com.example.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setCompleted(task.isCompleted());
            if (task.getDeadline() != null) {
                existingTask.setDeadline(task.getDeadline());
            }
            if (task.getCategory() != null) {
                existingTask.setCategory(task.getCategory());
            }
            return taskRepository.save(existingTask);
        }
        return null;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public String getTaskStatus(LocalDate deadline) {
        if (deadline == null) return "NORMAL";
        LocalDate today = LocalDate.now();

        if (deadline.isBefore(today)) {
            return "OVERDUE";
        } else if (!deadline.isAfter(today.plusDays(3))) {
            return "UPCOMING";
        } else {
            return "NORMAL";
        }
    }
}
