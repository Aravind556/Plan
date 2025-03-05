# 📝 PLAN - Modern Task Management Application

A sleek, modern task management application built with Spring Boot and a beautiful dark-themed UI. Inspired by Notion's minimalist design, PLAN helps you organize your tasks with style and efficiency.


## ✨ Features

### Task Management
- ✅ Create, edit, and delete tasks
- 🔄 Mark tasks as complete/incomplete
- 🏷️ Filter tasks by status (All, Active, Completed)
- 📱 Responsive design for all devices

### Modern UI/UX
- 🌙 Elegant dark theme
- 💫 Smooth animations and transitions
- 👆 Intuitive drag-and-drop interface
- 🎨 Clean, minimalist design

### User Experience
- 🔐 Secure user authentication
- 👤 User-specific task management
- 🔔 Real-time notifications
- ⚡ Fast and responsive interface

### Security Features
- 🔒 Spring Security integration
- 🛡️ Protected API endpoints
- 🔑 Secure password handling
- 🚫 CSRF protection

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/plan.git
cd plan
```

2. Configure database
```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/plan_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

5. Access the application at `http://localhost:8080`

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Security**: Spring Security
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **Build Tool**: Maven

### Frontend
- **HTML5/CSS3**
- **JavaScript (ES6+)**
- **Thymeleaf**
- **Modern CSS Features**
  - Flexbox/Grid
  - CSS Variables
  - Transitions/Animations

## 🎯 Key Features in Detail

### Task Management
- **Instant Updates**: Real-time task status changes
- **Smart Filtering**: Quick access to task categories
- **Bulk Actions**: Efficient task management
- **Task History**: Track changes and updates

### User Interface
- **Customizable Views**: Toggle between different task views
- **Keyboard Shortcuts**: Enhanced productivity
- **Responsive Design**: Works on all devices
- **Intuitive Controls**: Easy-to-use interface

### Security
- **User Authentication**: Secure login system
- **Data Protection**: Encrypted data storage
- **Session Management**: Secure user sessions
- **API Security**: Protected endpoints



## 🔒 Security Considerations

- Implements Spring Security best practices
- CSRF protection enabled
- Secure password hashing
- Protected API endpoints
- Session management
- XSS prevention


## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Inspired by Notion's clean design
- Built with Spring Boot's robust architecture
- Modern UI/UX principles
- Community feedback and contributions

## 🔮 Upcoming Features

### Group Tasks & Collaboration
- 👥 **Group Task Creation**
  - Create shared task spaces for teams
  - Assign group administrators
  - Set group-specific permissions
  - Custom group categories and labels

- 🏢 **Collaborative Workspaces**
  - Real-time task collaboration
  - Group chat and discussions
  - Task comments and feedback
  - Activity timeline for group tasks

- 👤 **User Management**
  - Role-based access control
  - Team member invitations
  - User groups and departments
  - Customizable user permissions

- 🤝 **Team Features**
  - Shared task boards
  - Team progress tracking
  - Group task analytics
  - Team performance metrics

### Enhanced Collaboration Tools
- 💬 **Communication**
  - In-app messaging system
  - Task-specific discussions
  - @mentions and notifications
  - File sharing in comments

- 📊 **Progress Tracking**
  - Group task progress bars
  - Team contribution metrics
  - Deadline management
  - Task dependencies

- 📅 **Team Calendar**
  - Shared group calendars
  - Event scheduling
  - Deadline visualization
  - Resource allocation

- 📈 **Analytics & Reporting**
  - Team productivity metrics
  - Task completion rates
  - Time tracking
  - Performance analytics

### Integration & Automation
- 🔄 **Workflow Automation**
  - Automated task assignments
  - Status updates
  - Reminder systems
  - Task templates

- 🔌 **Third-party Integration**
  - Calendar sync
  - Email integration
  - File storage services
  - Communication platforms

---

<p align="center">
Made with ❤️ for better task management
</p>

@media print {
  .container {
    width: 100%;
    max-width: none;
  }
  .task-item {
    break-inside: avoid;
  }
} 