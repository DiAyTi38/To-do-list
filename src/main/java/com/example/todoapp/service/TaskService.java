package com.example.todoapp.service;

import com.example.todoapp.entity.Task;
import com.example.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Create task
    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    // Update task
    public Task updateTask(Long id, Task taskDetails) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task existingTask = task.get();
            existingTask.setTitle(taskDetails.getTitle());
            existingTask.setDescription(taskDetails.getDescription());
            existingTask.setStatus(taskDetails.getStatus());
            existingTask.setDeadline(taskDetails.getDeadline());
            existingTask.setCategory(taskDetails.getCategory());
            existingTask.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(existingTask);
        }
        return null;
    }

    // Delete task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Search by title
    public List<Task> searchByTitle(String title) {
        return taskRepository.searchByTitle(title);
    }

    // Filter by status
    public List<Task> filterByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    // Filter by category
    public List<Task> filterByCategory(String category) {
        return taskRepository.findByCategory(category);
    }

    // Filter by status and category
    public List<Task> filterByStatusAndCategory(String status, String category) {
        return taskRepository.findByStatusAndCategory(status, category);
    }

    // Search and filter combined
    public List<Task> searchAndFilter(String title, String status, String category) {
        // If only title search
        if ((status == null || status.isEmpty()) && (category == null || category.isEmpty())) {
            return taskRepository.searchByTitle(title);
        }
        // If title + status
        if (category == null || category.isEmpty()) {
            return taskRepository.searchByTitleAndStatus(title, status);
        }
        // If title + category
        if (status == null || status.isEmpty()) {
            return taskRepository.searchByTitleAndCategory(title, category);
        }
        // If title + status + category
        return taskRepository.searchAndFilter(title, status, category);
    }
}
