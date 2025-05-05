# Fullstack Recipe Vault Application

This project is a fullstack web application for managing and browsing recipes. It consists of a Vue.js frontend and a Spring Boot backend, deployed separately.

## 🌐 Live Demo

- **Frontend (Vue.js):** [https://your-frontend.vercel.app](https://front-end-pied-two.vercel.app/)
- **Backend (Spring Boot):** [https://your-backend.onrender.com](https://back-end-oo5f.onrender.com/api/recipes)

## 📁 Project Structure
OLIVE/
├── backend/                  # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── olive/
│   │   │   │           └── recipevault/
│   │   │   │               ├── controller/   # REST controllers
│   │   │   │               ├── model/        # Entity classes
│   │   │   │               ├── repository/   # JPA repositories
│   │   │   │               └── service/      # Business logic
│   │   │   └── resources/
│   │   │       ├── application.properties    # Backend configuration
│   └── pom.xml                               # Maven build file
│
├── frontend/                 # Vue.js frontend
│   ├── public/               # Static assets
│   ├── src/
│   │   ├── assets/           # Images and styles
│   │   ├── components/       # Reusable Vue components
│   │   ├── views/            # Page-level Vue components
│   │   ├── router/           # Vue Router configuration
│   │   └── App.vue           # Root Vue component
│   └── package.json          # Frontend dependencies and scripts
│
└── README.md                 # Project overview and instructions

