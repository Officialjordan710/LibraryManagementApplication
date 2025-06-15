# LibraryManagementApplication
## 1. Core Features:
### User Management:

Users can register and login.

User roles (e.g., ADMIN, USER) control access to certain features.

Passwords are stored securely using Spring Security with BCrypt.

### Book Management:

Admins can add, update, or delete books.

Each book has properties like title, author, availability status.

Books can be searched by title and author.

### Borrowing System:

Users can borrow books if available.

Borrowing creates a BorrowRecord that tracks which user borrowed which book and when.

Users can return books, which updates the availability status and borrow record.

Overdue books are tracked by checking borrow dates against due dates.

### Overdue Reminders:

The system can automatically send email reminders to users with overdue books.

Emails are sent via JavaMailSender configured in Spring Boot.

### Admin Dashboard:

Admins can view all overdue books and related user info through a dedicated API endpoint.

### Security:

Spring Security enforces authentication and authorization.

Custom login page using Thymeleaf templates.

Public endpoints like login, registration, and static resources are accessible without login.

Role-based access control protects admin APIs.

## 2. Technology Stack:
### Backend:

Java 17, Spring Boot 3.x

Spring Data JPA (with Hibernate) for ORM

MySQL as the relational database

Spring Security for authentication and authorization

Spring Boot Starter Mail for email notifications

### Frontend:

Thymeleaf templates for rendering views (login page, admin views, etc.)

### Build & Tools:

Maven for dependency management and build

IntelliJ IDEA for development

DevTools for hot reload during development

## 3. How the System Works:
A user registers and logs in.

The user searches the book catalog by title/author.

The user borrows an available book — system updates book status and records the borrow date.

The system tracks borrowed books, and once the due date passes, sends email reminders automatically.

When the user returns the book, the borrow record is updated and the book becomes available again.

Admins access an endpoint to view all overdue books and manage users/books.
