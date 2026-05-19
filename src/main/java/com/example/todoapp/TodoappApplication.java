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
				Task.builder().title("Buy groceries").description("Milk, bread, eggs, vegetables").completed(false).build(),
				Task.builder().title("Finish project proposal").description("Q2 quarterly project proposal").completed(false).build(),
				Task.builder().title("Study Java Spring Boot").description("Review Spring Data JPA and REST API").completed(false).build(),
				Task.builder().title("Call client").description("Follow up on project status").completed(true).build(),
				Task.builder().title("Prepare presentation").description("Create slides for meeting").completed(false).build(),
				Task.builder().title("Exercise").description("30 minutes jogging").completed(false).build()
			);

			// Save all tasks
			taskRepository.saveAll(tasks);

			System.out.println("✅ Sample tasks created successfully!");
			System.out.println("📍 Visit: http://localhost:8080");
		};
	}

}
