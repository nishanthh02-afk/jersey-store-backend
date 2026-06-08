# Jersey Store Backend

A complete e-commerce REST API for a football jersey store built with Spring Boot.

## 🚀 Tech Stack
- Java 21
- Spring Boot 4.0.6
- Spring Security + JWT Authentication
- MySQL Database
- Spring Data JPA + Hibernate
- Razorpay Payment Gateway
- Cloudinary Image Upload
- Mailtrap Email Service
- Swagger API Documentation

## ✨ Features
- JWT Authentication with role based access (CUSTOMER, ADMIN)
- Product management with variants (kit type, size, stock)
- Product image upload via Cloudinary
- Product search by name, team, league, brand
- Shopping cart and wishlist
- Order management with stock control
- Razorpay payment integration
- Email notifications for orders
- Password reset with OTP via email
- Admin dashboard with revenue stats
- Swagger UI for API documentation
- Race condition protection with pessimistic locking
- Input validation and global exception handling
- CORS configuration for frontend integration

## 📁 Project Structure
src/main/java/com/jerseystore/jersey_backend/
├── config/          # Security, Cloudinary, Swagger config
├── controller/      # REST API controllers
├── dto/             # Request and Response DTOs
├── entity/          # JPA entities
├── enums/           # Role, OrderStatus, PaymentStatus etc
├── exception/       # Global exception handler
├── repository/      # Spring Data JPA repositories
├── security/        # JWT filter and service
└── service/         # Business logic

## 🔑 API Endpoints

| Module | Endpoint | Access |
|--------|----------|--------|
| Auth | POST /api/auth/register | Public |
| Auth | POST /api/auth/login | Public |
| Auth | POST /api/auth/forgot-password | Public |
| Auth | POST /api/auth/reset-password | Public |
| Products | GET /api/products | Public |
| Products | POST /api/products | ADMIN |
| Products | GET /api/products/search | Public |
| Cart | GET /api/cart | CUSTOMER |
| Cart | POST /api/cart | CUSTOMER |
| Wishlist | GET /api/wishlist | CUSTOMER |
| Wishlist | POST /api/wishlist | CUSTOMER |
| Orders | POST /api/orders | CUSTOMER |
| Orders | GET /api/orders | CUSTOMER |
| Payments | POST /api/payments/create | CUSTOMER |
| Payments | POST /api/payments/verify | CUSTOMER |
| Admin | GET /api/admin/dashboard | ADMIN |
| Admin | GET /api/admin/users | ADMIN |
| Admin | GET /api/admin/orders | ADMIN |

## 📖 API Documentation
Run the project and visit:
http://localhost:8080/swagger-ui/index.html

## ⚙️ Setup and Installation

1. Clone the repository
git clone https://github.com/nishanthh02-afk/jersey-store-backend.git

2. Create MySQL database
CREATE DATABASE jerseystore_db;

3. Create application.properties from example file and fill in your credentials

4. Run the application
./mvnw spring-boot:run

## 🔐 Environment Variables Required
- MySQL credentials
- JWT secret key
- Razorpay API keys
- Cloudinary credentials
- Mailtrap SMTP credentials

## 👨‍💻 Author
Nishanth — GitHub: https://github.com/nishanthh02-afk
