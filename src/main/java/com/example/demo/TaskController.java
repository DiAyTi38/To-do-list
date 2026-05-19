package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // GET all
    @GetMapping
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // CREATE task
    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // UPDATE task
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        return taskRepository.save(task);
    }

    // DELETE task
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

    // ===== PERSON 2 CORE LOGIC =====
    private String getStatus(LocalDate deadline) {
        LocalDate today = LocalDate.now();

        if (deadline.isBefore(today)) {
            return "OVERDUE";   // đỏ
        } else if (!deadline.isAfter(today.plusDays(3))) {
            return "UPCOMING";  // vàng
        } else {
            return "NORMAL";    // xanh
        }
    }

    // GET with status
    @GetMapping("/with-status")
    public List<Task> getWithStatus() {
        List<Task> tasks = taskRepository.findAll();
        for (Task t : tasks) {
            t.setStatus(getStatus(t.getDeadline()));
        }
        return tasks;
    }
}