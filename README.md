# 🎬 Movie Explorer

A full-stack movie browsing application using the TMDb API. Built with **Spring Boot** (backend) and **ReactJS** (frontend).

---

## 🛠️ Tech Stack

### 🔙 Backend
- ☕ Java 17
- 🌱 Spring Boot
- 🔐 Spring Security (JWT)
- 🗃️ Spring Data JPA
- 🛢️ MySQL
- 📦 Maven

### 🌐 Frontend
- ⚛️ ReactJS
- 📦 Axios
- 🔁 React Router
- 💡 Context API

---

## 🚀 How to Implement and Run

### 📦 Clone the Repository
```bash
git clone https://github.com/AkashChaudhari1/movieexplore.git
cd movieexplore
``` 
### 🔙 Running the Backend
1. Navigate to the backend:
```
cd backend
```
2. Create a file named application.properties in:
```
src/main/resources/
```
3. Add your TMDb API key and MySQL config like this:
```
tmdb.api.key=your_api_key
spring.datasource.url=jdbc:mysql://localhost:3306/movieexplore
spring.datasource.username=your_username
spring.datasource.password=your_password
```
4. Make sure MySQL is running and a database named movieexplore exists:
```
CREATE DATABASE movieexplore;
```
5. Run the Spring Boot app:
```
./mvnw spring-boot:run
```
### 🌐 Running the Frontend
1. Navigate to the frontend:
```
cd ../frontend
```
2. Install dependencies:
```
npm install
```
3. Start the development server:
```
npm start
```
4. Open your browser and go to:
```
http://localhost:3000
```
## 👨‍💻 Author
### Akash Kaluram Chaudhari
