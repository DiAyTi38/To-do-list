package com.example.todoapp.controller;

import com.example.todoapp.entity.Task;
import com.example.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Search by title
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(taskService.searchByTitle(title));
    }

    // Filter by status
    @GetMapping("/filter/status")
    public ResponseEntity<List<Task>> filterByStatus(@RequestParam String status) {
        return ResponseEntity.ok(taskService.filterByStatus(status));
    }

    // Filter by category
    @GetMapping("/filter/category")
    public ResponseEntity<List<Task>> filterByCategory(@RequestParam String category) {
        return ResponseEntity.ok(taskService.filterByCategory(category));
    }

    // Advanced search and filter
    @GetMapping("/search-filter")
    public ResponseEntity<List<Task>> searchAndFilter(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category) {
        if (title == null || title.isEmpty()) {
            title = "";
        }
        return ResponseEntity.ok(taskService.searchAndFilter(title, status, category));
    }

    // Create task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    // Update task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build();
    }

    // Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
