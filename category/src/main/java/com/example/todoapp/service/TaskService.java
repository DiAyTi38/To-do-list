package com.example.todoapp.service;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.Category;
import com.example.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> getAllTasks() {
        return taskRepository.findAllByOrderByDeadlineAsc();
    }
    
    public List<Task> getTasksByCategory(Category category) {
        return taskRepository.findByCategory(category);
    }
    
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }
    
    public List<Task> getTasksByCategoryAndStatus(Category category, String status) {
        return taskRepository.findByCategoryAndStatus(category, status);
    }
    
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    
    public Task updateTask(Long id, Task taskDetails) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setDeadline(taskDetails.getDeadline());
            task.setCategory(taskDetails.getCategory());
            return taskRepository.save(task);
        }
        return null;
    }
    
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<Task> searchTasks(String keyword) {
        return getAllTasks().stream()
            .filter(task -> task.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                           (task.getDescription() != null && 
                            task.getDescription().toLowerCase().contains(keyword.toLowerCase())))
            .toList();
    }
}
