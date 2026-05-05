# Person 4 - Search & Filter Feature

## 📋 Overview
Phần này chịu trách nhiệm:
- Backend API: search by title, filter by status/category
- Frontend: search box, filter dropdowns
- JavaScript: gọi API và render danh sách tasks

---

## 🔧 Backend Implementation

### 1. **Entity - Task.java**
Định nghĩa cấu trúc task:
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "...",
  "status": "pending|in-progress|completed",
  "deadline": "2024-05-15T10:00:00",
  "category": "Work|Study|Personal"
}
```

### 2. **Repository - TaskRepository.java**
Các method:
- `searchByTitle(String title)` - Tìm task theo title (case-insensitive)
- `findByStatus(String status)` - Lọc theo status
- `findByCategory(String category)` - Lọc theo category
- `searchAndFilter(...)` - Kết hợp search + filter

### 3. **Service - TaskService.java**
Logic nghiệp vụ:
- `searchAndFilter()` - Xử lý logic khi search/filter có nhiều điều kiện

### 4. **API Endpoints - TaskController.java**
```
GET    /api/tasks                    - Lấy tất cả tasks
GET    /api/tasks/{id}               - Lấy task theo ID
GET    /api/tasks/search?title=...   - Tìm kiếm theo title
GET    /api/tasks/filter/status?status=... - Lọc theo status
GET    /api/tasks/filter/category?category=... - Lọc theo category
GET    /api/tasks/search-filter?title=...&status=...&category=... - Tìm + Lọc
POST   /api/tasks                    - Tạo task mới
PUT    /api/tasks/{id}               - Cập nhật task
DELETE /api/tasks/{id}               - Xoá task
```

---

## 🎨 Frontend Implementation

### 1. **HTML - index.html**
- Search box: `<input id="searchInput">`
- Filter dropdowns: `<select id="statusFilter">`, `<select id="categoryFilter">`
- Tasks list container: `<div id="tasksList">`

### 2. **CSS - style.css**
- Gradient background
- Responsive design (mobile-friendly)
- Task cards với status/category badges
- Hover effects

### 3. **JavaScript - search-filter.js**
Các function chính:
```javascript
loadAllTasks()       // Load tất cả tasks
applyFilters()      // Áp dụng search + filter
search()           // Trigger search
resetFilters()     // Reset tất cả filters
renderTasks(tasks) // Render tasks lên UI
deleteTask(id)     // Xoá task
editTask(id)       // Placeholder cho edit (Person 1 sẽ làm)
```

---

## 🚀 Cách Sử Dụng

### Setup Database
1. Tạo SQL Server database: `todoapp`
2. Cập nhật `application.properties`:
   ```properties
   spring.datasource.url=jdbc:sqlserver://YOUR_SERVER:1433;databaseName=todoapp
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```

### Build & Run
```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Hoặc chạy JAR
java -jar target/todoapp-0.0.1-SNAPSHOT.jar
```

### Truy cập ứng dụng
```
http://localhost:8080
```

---

## 📝 API Usage Examples

### 1. Search by title
```bash
curl http://localhost:8080/api/tasks/search?title=buy
```

### 2. Filter by status
```bash
curl http://localhost:8080/api/tasks/filter/status?status=pending
```

### 3. Filter by category
```bash
curl http://localhost:8080/api/tasks/filter/category?category=Work
```

### 4. Search + Filter combined
```bash
curl "http://localhost:8080/api/tasks/search-filter?title=buy&status=pending&category=Work"
```

### 5. Create task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Buy groceries",
    "status": "pending",
    "deadline": "2024-05-15T10:00:00",
    "category": "Personal"
  }'
```

### 6. Delete task
```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

---

## 🔗 Integration Points

### Từ Person 1 (CRUD):
- ✅ `POST /api/tasks` - Tạo task mới
- ✅ `PUT /api/tasks/{id}` - Cập nhật task
- ✅ `DELETE /api/tasks/{id}` - Xoá task
- ⚠️ `editTask(id)` trong JS - Sẽ được implement bởi Person 1

### Từ Person 2 (Deadline):
- Trường `deadline` trong Entity
- CSS để highlight overdue/upcoming

### Từ Person 3 (Category):
- Trường `category` được include
- Filter dropdown hoạt động

### Từ Person 5 (UI/Dark Mode):
- CSS có thể được cải thiện với dark mode toggle
- Nếu Person 5 thay đổi layout, chỉ cần update `index.html`

---

## ✅ Testing

### Manual Testing
1. Mở browser: `http://localhost:8080`
2. Nhập text trong search box → nhấn Search
3. Chọn Status/Category từ dropdown
4. Nhấn Reset để clear filters
5. Click Delete để xoá task (có confirm)

### API Testing (Postman/curl)
Sử dụng các endpoints trên

---

## 📦 File Structure
```
src/
  main/
    java/
      com/example/todoapp/
        entity/
          ├─ Task.java                    ← Data model
        repository/
          ├─ TaskRepository.java          ← Database queries
        service/
          ├─ TaskService.java             ← Business logic
        controller/
          ├─ TaskController.java          ← REST API
          ├─ PageController.java          ← Page routing
    resources/
      templates/
        ├─ index.html                     ← Frontend UI
      static/
        css/
          ├─ style.css                    ← Styling
        js/
          ├─ search-filter.js             ← Frontend logic
      ├─ application.properties           ← Configuration
```

---

## 🐛 Troubleshooting

### Database Connection Error
- Kiểm tra SQL Server có chạy không
- Kiểm tra `application.properties` có đúng server/username/password
- Tạo database `todoapp` nếu chưa có

### API returns 404
- Kiểm tra server có chạy không (port 8080)
- Kiểm tra endpoint URL có đúng không

### Frontend không load tasks
- Mở browser console (F12) xem error gì
- Kiểm tra API endpoints có respond không
- CORS đã được enable (@CrossOrigin)

---

## 📝 Notes
- Tất cả search là **case-insensitive**
- Filter có thể combine (search title + filter status + filter category)
- Nếu không input search, mặc định là "" (match all)
- Format deadline: ISO 8601 (YYYY-MM-DDTHH:mm:ss)

---

## 🎯 Next Steps
Khi Person 1 hoàn thành CRUD, có thể:
- Thêm trang create/edit task
- Link editTask() function với trang form
- Validate input data ở frontend/backend
