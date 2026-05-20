package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.Category;
import com.example.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping
    public String index(Model model, 
                       @RequestParam(required = false) String category,
                       @RequestParam(required = false) String status) {
        List<Task> tasks;
        
        if (category != null && !category.isEmpty()) {
            try {
                Category cat = Category.valueOf(category.toUpperCase());
                if (status != null && !status.isEmpty()) {
                    tasks = taskService.getTasksByCategoryAndStatus(cat, status);
                } else {
                    tasks = taskService.getTasksByCategory(cat);
                }
            } catch (IllegalArgumentException e) {
                tasks = taskService.getAllTasks();
            }
        } else if (status != null && !status.isEmpty()) {
            tasks = taskService.getTasksByStatus(status);
        } else {
            tasks = taskService.getAllTasks();
        }
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("categories", Arrays.asList(Category.values()));
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedStatus", status);
        
        return "index";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("categories", Arrays.asList(Category.values()));
        return "add-task";
    }
    
    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskService.createTask(task);
        return "redirect:/";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id).orElse(null);
        if (task != null) {
            model.addAttribute("task", task);
            model.addAttribute("categories", Arrays.asList(Category.values()));
            return "edit-task";
        }
        return "redirect:/";
    }
    
    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, @ModelAttribute Task task) {
        taskService.updateTask(id, task);
        return "redirect:/";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }
    
    @PostMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id, @RequestParam(required = false) String category) {
        Task task = taskService.getTaskById(id).orElse(null);
        if (task != null) {
            task.setStatus(task.getStatus().equals("DONE") ? "PENDING" : "DONE");
            taskService.updateTask(id, task);
        }
        
        String redirect = "redirect:/";
        if (category != null && !category.isEmpty()) {
            redirect += "?category=" + category;
        }
        return redirect;
    }
}
