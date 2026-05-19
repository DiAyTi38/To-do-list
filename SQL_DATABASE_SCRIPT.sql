-- ==========================================
-- TO-DO LIST DATABASE - SQL SERVER SCRIPT
-- ==========================================
-- Chạy script này trong SQL Server Management Studio

-- 1. TẠO DATABASE
CREATE DATABASE todoapp;
GO

-- 2. SỬ DỤNG DATABASE
USE todoapp;
GO

-- 3. TẠO TABLE TASKS
CREATE TABLE tasks (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    status NVARCHAR(50) NOT NULL, -- pending, in-progress, completed
    deadline DATETIME2 NOT NULL,
    category NVARCHAR(50) NOT NULL, -- Work, Study, Personal
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2 NOT NULL DEFAULT GETDATE()
);

-- 4. TẠO INDEX CHO CÁC TRƯỜNG TÌM KIẾM
CREATE INDEX idx_title ON tasks(title);
CREATE INDEX idx_status ON tasks(status);
CREATE INDEX idx_category ON tasks(category);
CREATE INDEX idx_deadline ON tasks(deadline);

-- 5. CHÈN DỮ LIỆU MẪU
INSERT INTO tasks (title, description, status, deadline, category, created_at, updated_at)
VALUES
(
    N'Buy groceries',
    N'Milk, bread, eggs, vegetables',
    'pending',
    '2024-05-15 10:00:00',
    'Personal',
    GETDATE(),
    GETDATE()
),
(
    N'Finish project proposal',
    N'Q2 quarterly project proposal',
    'in-progress',
    '2024-05-10 14:30:00',
    'Work',
    GETDATE(),
    GETDATE()
),
(
    N'Study Java Spring Boot',
    N'Review Spring Data JPA and REST API',
    'pending',
    '2024-05-20 18:00:00',
    'Study',
    GETDATE(),
    GETDATE()
),
(
    N'Call client',
    N'Follow up on project status',
    'completed',
    '2024-05-08 11:00:00',
    'Work',
    GETDATE(),
    GETDATE()
),
(
    N'Prepare presentation',
    N'Create slides for meeting',
    'in-progress',
    '2024-05-12 16:00:00',
    'Work',
    GETDATE(),
    GETDATE()
),
(
    N'Exercise',
    N'30 minutes jogging',
    'pending',
    '2024-05-14 07:00:00',
    'Personal',
    GETDATE(),
    GETDATE()
);

-- 6. XEM DỮ LIỆU
SELECT * FROM tasks;

-- ==========================================
-- THÔNG TIN KẾT NỐI (cập nhật vào application.properties)
-- ==========================================
-- Server: localhost\SQLEXPRESS (hoặc tên server của bạn)
-- Database: todoapp
-- Port: 1433
-- Username: sa (hoặc user của bạn)
-- Password: (password của bạn)

-- ==========================================
-- CÁC QUERY HỮU DỤNG
-- ==========================================

-- Tìm kiếm theo title
SELECT * FROM tasks WHERE title LIKE N'%buy%';

-- Lọc theo status
SELECT * FROM tasks WHERE status = 'pending';

-- Lọc theo category
SELECT * FROM tasks WHERE category = 'Work';

-- Kết hợp search + filter
SELECT * FROM tasks 
WHERE title LIKE N'%task%' 
  AND status = 'pending' 
  AND category = 'Work';

-- Lấy tasks sắp hết hạn
SELECT * FROM tasks 
WHERE deadline BETWEEN GETDATE() AND DATEADD(DAY, 3, GETDATE())
ORDER BY deadline ASC;

-- Đếm tasks theo status
SELECT status, COUNT(*) as count 
FROM tasks 
GROUP BY status;

-- Đếm tasks theo category
SELECT category, COUNT(*) as count 
FROM tasks 
GROUP BY category;

-- Xoá tất cả tasks (cẩn thận!)
-- DELETE FROM tasks;

-- Xoá database (cẩn thận!)
-- DROP DATABASE todoapp;
