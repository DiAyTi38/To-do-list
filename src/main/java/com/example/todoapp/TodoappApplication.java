package com.example.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.todoapp.entity.Task;
import com.example.todoapp.repository.TaskRepository;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TodoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

	@Bean
	CommandLineRunner initDb(TaskRepository taskRepository) {
		return args -> {
			// Check if database already has data
			if (taskRepository.count() > 0) {
				System.out.println("Database already initialized. Skipping seed data.");
				return;
			}

			System.out.println("Seeding sample tasks...");

			// Sample tasks
			List<Task> tasks = Arrays.asList(
					new Task("Buy groceries", "Milk, bread, eggs, vegetables", false),
					new Task("Finish project proposal", "Q2 quarterly project proposal", false),
					new Task("Study Java Spring Boot", "Review Spring Data JPA and REST API", false),
					new Task("Call client", "Follow up on project status", true),
					new Task("Prepare presentation", "Create slides for meeting", false),
					new Task("Exercise", "30 minutes jogging", false));

			// Save all tasks
			taskRepository.saveAll(tasks);

			System.out.println("✅ Sample tasks created successfully!");
			System.out.println("📍 Visit: http://localhost:8080");
		};
	}

}
