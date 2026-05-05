package com.example.todoapp.repository;

import com.example.todoapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Search by title (case-insensitive)
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Task> searchByTitle(@Param("title") String title);

    // Filter by status
    List<Task> findByStatus(String status);

    // Filter by category
    List<Task> findByCategory(String category);

    // Filter by status and category
    List<Task> findByStatusAndCategory(String status, String category);

    // Search by title AND filter by status
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) AND t.status = :status")
    List<Task> searchByTitleAndStatus(@Param("title") String title, @Param("status") String status);

    // Search by title AND filter by category
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) AND t.category = :category")
    List<Task> searchByTitleAndCategory(@Param("title") String title, @Param("category") String category);

    // Search by title AND filter by status AND category
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) AND t.status = :status AND t.category = :category")
    List<Task> searchAndFilter(@Param("title") String title, @Param("status") String status, @Param("category") String category);
}
