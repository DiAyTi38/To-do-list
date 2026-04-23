package com.example.todoapp.controller;

import com.example.todoapp.entity.Task;
import com.example.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    // ── Trang chính ──────────────────────────────────────────────────────────
    @GetMapping("/")
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "taskmanager";
    }

    // ── Thêm task ─────────────────────────────────────────────────────────────
    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskService.createTask(task);
        return "redirect:/";
    }

    // ── Sửa task ──────────────────────────────────────────────────────────────
    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id,
                           @ModelAttribute Task task,
                           @RequestParam(name = "completed", required = false, defaultValue = "false") String completedStr) {
        // Checkbox HTML chỉ gửi giá trị khi được tick -> cần xử lý riêng
        task.setCompleted("true".equalsIgnoreCase(completedStr) || "on".equalsIgnoreCase(completedStr));
        taskService.updateTask(id, task);
        return "redirect:/";
    }

    // ── Xóa task ──────────────────────────────────────────────────────────────
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }

    // ── Toggle hoàn thành ─────────────────────────────────────────────────────
    @GetMapping("/toggle/{id}")
    public String toggleTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            task.setCompleted(!task.isCompleted());
            taskService.updateTask(id, task);
        }
        return "redirect:/";
    }
}
