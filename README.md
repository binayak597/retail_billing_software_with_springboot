# 🛍️ Retail Billing System - Backend

A Spring Boot-based backend for managing retail shop inventory from end to end — including listing, selling, employee management, payments, and more.
Supports **role-based access (USER & ADMIN)**, **JWT authentication**, **file uploads** (AWS S3 & local storage), and **Razorpay payment integration**.

---

## 🚀 Features
- **Role-based Access Control** (USER & ADMIN)
- **JWT Authentication** (Login/Protected Routes)
- **Inventory Management** (Categories, Items, Orders)
- **File Upload**:
  - Local storage
  - AWS S3 integration
- **Razorpay Payment Integration**
- **Swagger UI API Documentation**
- **CORS Configured for Frontend**
- **MySQL Database Support**

---

## 🛠 Tech Stack
- **Backend Framework:** Spring Boot 3.x
- **Database:** MySQL
- **Auth:** JWT
- **File Storage:** AWS S3 / Local Disk
- **API Docs:** Swagger UI (springdoc-openapi)
- **Payment:** Razorpay API

---

## ⚙️ Installation & Setup

### **1️⃣ Clone the repository**
```bash
git clone https://github.com/binayak597/retail-billing-backend.git
cd retail-billing-backend
```

### **2️⃣ Configure MySQL Database**
- Create a database in MySQL:

```sql
CREATE DATABASE retail_billing;
```

- Update application.properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/retail_billing
spring.datasource.username=root
spring.datasource.password=yourpassword

# File upload location
file.upload-dir=uploads

# AWS S3 configuration (optional)
aws.access.key=YOUR_AWS_ACCESS_KEY
aws.secret.key=YOUR_AWS_SECRET_KEY
aws.bucket.name=your-bucket-name

jwt.secret.key=${JWT_SECRET}

razorpay.key.id=${RAZORPAY_KEY_ID}
razorpay.key.secret=${RAZORPAY_KEY_SECRET}

```

### **3️⃣ Build & Run**

```bash
mvn clean install
mvn spring-boot:run
```
### **📦 Build for Production**

```bash
mvn clean package
java -jar target/retail-billing-0.0.1-SNAPSHOT.jar
```

### **API Documentation (Swagger UI)**
Once the application is running, access the API docs:

```bash
http://localhost:5454/api/v1.0/swagger-ui.html
```
(once uploaded to cloud use your hosted server instead)

### **🔐 JWT Authentication in Swagger UI**
`.Login using /login endpoint (public).

2.Copy the JWT token from the response.

3/Click the "Authorize" button in Swagger UI.

4.Enter:

```bash
Bearer <your_token_here>

```
5.All protected endpoints will now be accessible.

## 👤 Roles & Access
| Role  | Access Level                                   |
| ----- | ---------------------------------------------- |
| USER  | Categories, Items, Orders, Payments, Dashboard |
| ADMIN | All USER access + `/admin/**` endpoints        |

 - #### Admin can only create user.
 - #### User can not create by own.
 - #### Admin will create and pass the credentials to user.

## 💳 Razorpay Payment Integration
Uses Razorpay Orders API for payment creation.

Supports UPI & Cash payment modes.


## 📌 Demo Credentials

To explore the application, use the following login credentials:

- **Username/Email:** admin@example.com  
- **Password:** 123456  

## 📜 License

This project is open-source. Feel free to **fork**, modify, and use it as per your needs.

## 🎯 Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvements, feel free to submit an issue or a pull request.

## 🙌 Connect

If you liked this project, don't forget to ⭐ the repository! You can check out my other projects on [GitHub](https://github.com/binayak597).

Happy coding! 🚀
