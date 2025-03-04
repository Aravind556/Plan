document.addEventListener('DOMContentLoaded', () => {
    loadTasks();
    setupEventListeners();
    setupUserDropdown();
    setupStrikethroughToggle();
});

function setupEventListeners() {
    document.getElementById('task-form').addEventListener('submit', handleAddTask);
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', handleFilter);
    });
}

function setupUserDropdown() {
    const userIcon = document.getElementById('userIcon');
    const userDropdown = document.getElementById('userDropdown');

    userIcon.addEventListener('click', (e) => {
        e.stopPropagation();
        userDropdown.classList.toggle('show');
    });

    // Close dropdown when clicking outside
    document.addEventListener('click', (e) => {
        if (!userDropdown.contains(e.target) && !userIcon.contains(e.target)) {
            userDropdown.classList.remove('show');
        }
    });
}

function setupStrikethroughToggle() {
    const toggleContainer = document.getElementById('strikethroughToggle');
    const toggleCheckbox = document.getElementById('toggleStrike');
    const taskList = document.getElementById('task-list');

    // Show toggle only when completed filter is active
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            if (btn.dataset.status === 'completed') {
                toggleContainer.classList.add('show');
            } else {
                toggleContainer.classList.remove('show');
            }
        });
    });

    // Handle toggle change
    toggleCheckbox.addEventListener('change', () => {
        const completedTasks = taskList.querySelectorAll('.task-item.completed');
        completedTasks.forEach(task => {
            if (toggleCheckbox.checked) {
                task.classList.remove('no-strike');
            } else {
                task.classList.add('no-strike');
            }
        });
    });
}

async function loadTasks(status = null) {
    try {
        const url = status !== null ? `/api/tasks/status/${status}` : '/api/tasks';
        const response = await fetch(url);
        const tasks = await response.json();
        displayTasks(tasks);
    } catch (error) {
        showNotification('Error loading tasks', 'error');
    }
}

function displayTasks(tasks) {
    const taskList = document.getElementById('task-list');
    taskList.innerHTML = '';

    tasks.forEach(task => {
        const taskElement = createTaskElement(task);
        taskList.appendChild(taskElement);
    });
}

function createTaskElement(task) {
    const div = document.createElement('div');
    div.className = `task-item ${task.status ? 'completed' : ''}`;
    if (task.status && !document.getElementById('toggleStrike').checked) {
        div.classList.add('no-strike');
    }
    div.dataset.taskId = task.id;

    div.innerHTML = `
        <div class="task-content">
            <input type="checkbox" class="task-checkbox" ${task.status ? 'checked' : ''}>
            <span class="task-text">${escapeHtml(task.description)}</span>
            <input type="text" class="task-edit-input" style="display: none;" value="${escapeHtml(task.description)}">
        </div>
        <div class="task-actions">
            <button class="btn btn-secondary edit-btn">Edit</button>
            <button class="btn btn-danger delete-btn">Delete</button>
        </div>
    `;

    // Add event listeners
    const checkbox = div.querySelector('.task-checkbox');
    const editBtn = div.querySelector('.edit-btn');
    const deleteBtn = div.querySelector('.delete-btn');
    const taskText = div.querySelector('.task-text');
    const editInput = div.querySelector('.task-edit-input');

    checkbox.addEventListener('change', (e) => handleStatusChange(task.id, e.target.checked));
    deleteBtn.addEventListener('click', () => handleDelete(task.id));
    
    // Edit functionality
    editBtn.addEventListener('click', () => {
        taskText.style.display = 'none';
        editInput.style.display = 'block';
        editInput.focus();
        editInput.setSelectionRange(0, editInput.value.length);
    });

    editInput.addEventListener('keyup', (e) => {
        if (e.key === 'Enter') {
            handleEdit(task.id, editInput.value.trim());
        } else if (e.key === 'Escape') {
            editInput.value = task.description;
            taskText.style.display = 'block';
            editInput.style.display = 'none';
        }
    });

    editInput.addEventListener('blur', () => {
        const newValue = editInput.value.trim();
        if (newValue !== task.description) {
            handleEdit(task.id, newValue);
        } else {
            taskText.style.display = 'block';
            editInput.style.display = 'none';
        }
    });

    return div;
}

async function handleAddTask(e) {
    e.preventDefault();
    const input = document.getElementById('new-task');
    const description = input.value.trim();

    if (!description) return;

    try {
        const response = await fetch('/api/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ description })
        });

        if (response.ok) {
            input.value = '';
            loadTasks();
            showNotification('Task added successfully', 'success');
        }
    } catch (error) {
        showNotification('Error adding task', 'error');
    }
}

async function handleStatusChange(taskId, status) {
    try {
        const response = await fetch(`/api/tasks/${taskId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ status })
        });

        if (response.ok) {
            loadTasks();
        }
    } catch (error) {
        showNotification('Error updating task status', 'error');
    }
}

async function handleDelete(taskId) {
    const modal = document.getElementById('deleteModal');
    const confirmBtn = document.getElementById('confirmDelete');
    const cancelBtn = document.getElementById('cancelDelete');

    // Show the modal
    modal.classList.add('show');

    // Handle cancel
    const handleCancel = () => {
        modal.classList.remove('show');
        cleanup();
    };

    // Handle confirm
    const handleConfirm = async () => {
        modal.classList.remove('show');
        cleanup();

        try {
            const response = await fetch(`/api/tasks/${taskId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                loadTasks();
                showNotification('Task deleted successfully', 'success');
            }
        } catch (error) {
            showNotification('Error deleting task', 'error');
        }
    };

    // Clean up event listeners
    const cleanup = () => {
        confirmBtn.removeEventListener('click', handleConfirm);
        cancelBtn.removeEventListener('click', handleCancel);
        document.removeEventListener('click', handleOutsideClick);
    };

    // Close modal when clicking outside
    const handleOutsideClick = (e) => {
        if (e.target === modal) {
            handleCancel();
        }
    };

    // Add event listeners
    confirmBtn.addEventListener('click', handleConfirm);
    cancelBtn.addEventListener('click', handleCancel);
    document.addEventListener('click', handleOutsideClick);
}

async function handleEdit(taskId, newDescription) {
    if (!newDescription) return;

    try {
        const response = await fetch(`/api/tasks/${taskId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ description: newDescription })
        });

        if (response.ok) {
            loadTasks();
            showNotification('Task updated successfully', 'success');
        }
    } catch (error) {
        showNotification('Error updating task', 'error');
    }
}

function handleFilter(e) {
    const status = e.target.dataset.status;
    document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
    e.target.classList.add('active');
    loadTasks(status === 'all' ? null : status === 'completed');
}

function showNotification(message, type) {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;

    document.body.appendChild(notification);
    setTimeout(() => notification.remove(), 3000);
}

function escapeHtml(unsafe) {
    return unsafe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
} 