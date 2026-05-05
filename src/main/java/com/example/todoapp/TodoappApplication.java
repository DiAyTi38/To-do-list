package com.example.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.todoapp.entity.Task;
import com.example.todoapp.repository.TaskRepository;
import java.time.LocalDateTime;

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
			Task task1 = new Task(
				"Buy groceries",
				"pending",
				LocalDateTime.of(2024, 5, 15, 10, 0),
				"Personal"
			);
			task1.setDescription("Milk, bread, eggs, vegetables");

			Task task2 = new Task(
				"Finish project proposal",
				"in-progress",
				LocalDateTime.of(2024, 5, 10, 14, 30),
				"Work"
			);
			task2.setDescription("Q2 quarterly project proposal");

			Task task3 = new Task(
				"Study Java Spring Boot",
				"pending",
				LocalDateTime.of(2024, 5, 20, 18, 0),
				"Study"
			);
			task3.setDescription("Review Spring Data JPA and REST API");

			Task task4 = new Task(
				"Call client",
				"completed",
				LocalDateTime.of(2024, 5, 8, 11, 0),
				"Work"
			);
			task4.setDescription("Follow up on project status");

			Task task5 = new Task(
				"Prepare presentation",
				"in-progress",
				LocalDateTime.of(2024, 5, 12, 16, 0),
				"Work"
			);
			task5.setDescription("Create slides for meeting");

			Task task6 = new Task(
				"Exercise",
				"pending",
				LocalDateTime.of(2024, 5, 14, 7, 0),
				"Personal"
			);
			task6.setDescription("30 minutes jogging");

			// Save all tasks
			taskRepository.save(task1);
			taskRepository.save(task2);
			taskRepository.save(task3);
			taskRepository.save(task4);
			taskRepository.save(task5);
			taskRepository.save(task6);

			System.out.println("✅ Sample tasks created successfully!");
			System.out.println("📍 Visit: http://localhost:8080");
		};
	}

}
