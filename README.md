# 🤖 AI Interview Assistant

This project is a full-stack AI-powered interview assistant that helps users practice interview questions and get instant feedback on their answers.

The system evaluates answers using basic Natural Language Processing (NLP) techniques and provides a score along with suggestions for improvement.

---

## 🚀 What this project does

* Allows users to answer interview questions
* Evaluates answers using AI (NLP-based similarity)
* Gives a score and feedback
* Shows previous attempts (history)
* Supports user login with JWT authentication

---

## 🏗️ Project Architecture

This project is divided into three parts:

```
Frontend (React)
        ↓
Backend (Spring Boot)
        ↓
ML Service (Python)
        ↓
Database (H2)
```

* Frontend handles UI and user interaction
* Backend handles APIs, authentication, and logic
* ML service evaluates answers
* Database stores user data and history

---

## 🛠️ Technologies Used

### Backend

* Java
* Spring Boot
* Spring Security (JWT Authentication)
* Spring Data JPA

### Frontend

* React.js
* Axios

### ML / AI

* Python
* Flask
* Scikit-learn

### Database

* H2 (in-memory database)

---

## 🧠 How the system works

1. User logs in and gets a JWT token
2. User enters an answer in the frontend
3. Backend verifies the token
4. Backend sends the answer to the ML service
5. ML service compares it with the model answer
6. A similarity score is returned
7. Backend calculates final score and stores it
8. Frontend displays result and history

---

## 🤖 ML Concept Used

This project uses:

* **TF-IDF (Term Frequency – Inverse Document Frequency)**
* **Cosine Similarity**

These are used to compare how similar the user's answer is to the expected answer.

### Why this approach?

* Simple and fast
* Works well for short text answers
* Suitable for real-time feedback

---

## 🔐 Authentication

* Implemented using **JWT (JSON Web Tokens)**
* Each request is validated using a token
* User data is separated based on login

---

## 📂 Project Structure

```
AI-Interview-Assistant/
 ├── backend/        (Spring Boot)
 ├── ml-service/     (Python Flask)
 ├── frontend/       (React)
```

---

## ▶️ How to Run the Project

### 1. Start ML Service

```
cd ml-service
source venv/bin/activate
python app.py
```

---

### 2. Start Backend

```
cd backend
./mvnw spring-boot:run
```

---

### 3. Start Frontend

```
cd frontend
npm install
npm start
```

---

## ⚠️ Common Issues

* 403 error → Check if JWT token is added
* No response → Ensure ML service is running
* CORS error → Check backend configuration

---

## 🚧 Future Improvements

* Use advanced models like BERT for better accuracy
* Add voice input (speech-to-text)
* Improve UI design
* Add personalized recommendations

---

## 🧠 Key Learnings

* Built a full-stack application with multiple services
* Implemented JWT-based authentication
* Integrated Java backend with Python ML service
* Applied NLP techniques for real-world problem solving

---
