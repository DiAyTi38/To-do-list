const API_BASE = 'http://localhost:8080/api/tasks';

// Load all tasks on page load
document.addEventListener('DOMContentLoaded', () => {
    loadAllTasks();
});

// Search function
function search() {
    const searchInput = document.getElementById('searchInput').value.trim();
    if (!searchInput) {
        alert('Please enter a search term');
        return;
    }
    applyFilters();
}

// Apply filters and search
function applyFilters() {
    const searchInput = document.getElementById('searchInput').value.trim();
    const statusFilter = document.getElementById('statusFilter').value;
    const categoryFilter = document.getElementById('categoryFilter').value;

    // Build query string
    let query = '';
    if (searchInput) {
        query += `title=${encodeURIComponent(searchInput)}`;
    }
    if (statusFilter) {
        query += (query ? '&' : '') + `status=${encodeURIComponent(statusFilter)}`;
    }
    if (categoryFilter) {
        query += (query ? '&' : '') + `category=${encodeURIComponent(categoryFilter)}`;
    }

    if (!query) {
        loadAllTasks();
        return;
    }

    // Fetch filtered/searched tasks
    fetch(`${API_BASE}/search-filter?${query}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch tasks');
            }
            return response.json();
        })
        .then(tasks => {
            renderTasks(tasks);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error fetching tasks');
        });
}

// Reset filters
function resetFilters() {
    document.getElementById('searchInput').value = '';
    document.getElementById('statusFilter').value = '';
    document.getElementById('categoryFilter').value = '';
    loadAllTasks();
}

// Load all tasks
function loadAllTasks() {
    fetch(API_BASE)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch tasks');
            }
            return response.json();
        })
        .then(tasks => {
            renderTasks(tasks);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error loading tasks');
        });
}

// Render tasks to UI
function renderTasks(tasks) {
    const tasksList = document.getElementById('tasksList');
    const emptyState = document.getElementById('emptyState');

    if (tasks.length === 0) {
        tasksList.innerHTML = '';
        emptyState.style.display = 'block';
        return;
    }

    emptyState.style.display = 'none';
    tasksList.innerHTML = tasks.map(task => createTaskElement(task)).join('');
}

// Create task element HTML
function createTaskElement(task) {
    const deadline = new Date(task.deadline).toLocaleString();
    const statusClass = `status-${task.status.replace('-', '-')}`;
    const categoryClass = `category-${task.category.toLowerCase()}`;

    return `
        <div class="task-item">
            <div class="task-content">
                <div class="task-title">${escapeHtml(task.title)}</div>
                <div class="task-meta">
                    <span class="task-status ${statusClass}">${getStatusIcon(task.status)} ${task.status}</span>
                    <span class="task-category ${categoryClass}">${task.category}</span>
                    <span class="task-deadline">📅 ${deadline}</span>
                </div>
            </div>
            <div class="task-actions">
                <button class="btn-small btn-edit" onclick="editTask(${task.id})">Edit</button>
                <button class="btn-small btn-delete" onclick="deleteTask(${task.id})">Delete</button>
            </div>
        </div>
    `;
}

// Get status icon
function getStatusIcon(status) {
    const icons = {
        'pending': '⏳',
        'in-progress': '⚙️',
        'completed': '✅'
    };
    return icons[status] || '•';
}

// Delete task
function deleteTask(id) {
    if (!confirm('Are you sure you want to delete this task?')) {
        return;
    }

    fetch(`${API_BASE}/${id}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to delete task');
        }
        alert('Task deleted successfully!');
        loadAllTasks();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error deleting task');
    });
}

// Edit task (placeholder)
function editTask(id) {
    alert(`Edit task ${id} - Feature coming soon!`);
    // This will be implemented by another team member
}

// Escape HTML to prevent XSS
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}
