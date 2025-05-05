# Fullstack Recipe Vault Application

This project is a fullstack web application for managing and browsing recipes. It consists of a Vue.js frontend and a Spring Boot backend, deployed separately.

## 🌐 Live Demo

- **Frontend (Vue.js):** [https://your-frontend.vercel.app](https://front-end-pied-two.vercel.app/)
- **Backend (Spring Boot):** [https://your-backend.onrender.com](https://back-end-oo5f.onrender.com/api/recipes)

Backend (Spring Boot with Docker)
1. Navigate to the backend directory:
   ```bash
   cd backend
2. Build the Docker image
   ```bash
   docker build -t recipe-vault-backend .

3. Run the container
   ```bash
   docker run -d -p 8080:8080 --name recipe-vault-backend recipe-vault-backend

   
Frontend (Vue.js)
1. Navigate to the frontend directory:
    ```bash
   cd frontend
2. Install Dependencies
      ```bash
   npm install
3. Run the development server
      ```bash
   npm run dev
