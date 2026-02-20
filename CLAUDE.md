# CLAUDE.md — OLIVE Recipe Vault

This file provides guidance for AI assistants working in this repository.

## Project Overview

**OLIVE** is a fullstack Recipe Vault web application with:
- **Backend**: Spring Boot 3.4.5 / Java 21 REST API deployed on Render
- **Frontend**: Vue.js 3 SPA deployed on Vercel
- **Database**: TiDB Cloud (MySQL-compatible serverless database)

Live URLs:
- Frontend: `https://front-end-pied-two.vercel.app/`
- Backend: `https://back-end-oo5f.onrender.com`

---

## Repository Structure

```
OLIVE/
├── CLAUDE.md
├── README.md
├── olive-backend/          # Spring Boot API
│   ├── Dockerfile          # Multi-stage build (Maven → JRE)
│   ├── docker-compose.yml  # Local MySQL setup
│   ├── init.sql            # DB schema + seed data
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd
│   └── src/
│       ├── main/java/com/olive_backend/Recipe/Vault/
│       │   ├── RecipeVaultApplication.java
│       │   ├── Difficulty.java            # Enum: Easy | Medium | Hard
│       │   ├── Config/
│       │   │   ├── ConfigController.java      # GET /api/config/ping → "pong"
│       │   │   ├── RenderKeepAliveConfig.java # Self-ping every 14 min (prod profile only)
│       │   │   ├── StaticResourceConfig.java  # Serves /uploads/** from filesystem
│       │   │   └── WebConfig.java             # CORS for /uploads/**
│       │   ├── Recipe/
│       │   │   ├── Recipe.java            # @Entity (JPA)
│       │   │   ├── RecipeController.java  # REST endpoints
│       │   │   ├── RecipeDTO.java         # Data transfer object
│       │   │   └── RecipeRepository.java  # JpaRepository
│       │   ├── Ingredient/
│       │   │   ├── Ingredient.java        # @Entity (ManyToOne → Recipe)
│       │   │   ├── IngredientDTO.java
│       │   │   └── IngredientRepository.java
│       │   └── Service/
│       │       └── RecipeService.java     # Business logic + image upload
│       ├── main/resources/
│       │   └── application.properties    # DB connection, JPA config
│       └── test/java/...
│           └── RecipeVaultApplicationTests.java
└── olive-frontend/         # Vue.js 3 SPA
    ├── package.json
    ├── vite.config.js
    ├── vercel.json         # Rewrites all routes → /index.html (SPA support)
    ├── index.html
    ├── data/
    │   └── db.json         # JSON Server mock data (for frontend-only dev)
    └── src/
        ├── main.js         # App bootstrap (Vue + Pinia + Router + Element Plus)
        ├── App.vue
        ├── router/
        │   └── index.js    # Route definitions
        ├── stores/
        │   └── recipeStore.js  # Pinia store (API calls)
        ├── components/
        │   ├── NavBar.vue
        │   ├── RecipeList.vue
        │   └── SingleRecipe.vue
        └── views/
            ├── Dashboard.vue    # Table with search/filter/pagination
            ├── Details.vue      # View + delete a recipe
            ├── CreateRecipe.vue # Create form with image upload
            └── EditRecipe.vue   # Edit form with image upload
```

---

## Tech Stack

### Backend
| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Language |
| Spring Boot | 3.4.5 | Framework |
| Spring Data JPA | via Boot | ORM / DB access |
| Spring Validation | via Boot | Request validation |
| Hibernate | via JPA | ORM implementation |
| Lombok | latest | Boilerplate reduction (`@Data`, `@NoArgsConstructor`, etc.) |
| MySQL Connector/J | via Boot | JDBC driver |
| Spring DevTools | runtime | Hot reload in development |

### Frontend
| Technology | Version | Purpose |
|---|---|---|
| Vue.js | 3.5.x | UI framework (Composition API) |
| Vite | 6.x | Build tool / dev server |
| Pinia | 3.x | State management |
| Vue Router | 4.x | Client-side routing |
| Element Plus | 2.9.x | UI component library |
| JSON Server | 1.0.0-beta | Mock REST API for local dev |

### Infrastructure
| Service | Purpose |
|---|---|
| Render | Backend hosting (Docker container) |
| Vercel | Frontend hosting (static SPA) |
| TiDB Cloud | Production MySQL-compatible database |
| Docker | Backend containerization |

---

## Data Model

### Recipe (table: `recipes`)
| Field | Type | Notes |
|---|---|---|
| id | Long | Auto-generated PK |
| title | String | Not null |
| difficulty | Enum | `Easy` / `Medium` / `Hard` |
| instructions | TEXT | Step-by-step instructions |
| imageUrl | String | Stored as `/uploads/<uuid>_<filename>` |
| creatorName | String | |
| createdDate | LocalDateTime | Defaults to `LocalDateTime.now()` |
| ingredients | List\<Ingredient\> | OneToMany, CascadeAll, orphanRemoval |

### Ingredient (table: `ingredients`)
| Field | Type | Notes |
|---|---|---|
| id | Long | Auto-generated PK |
| ingredientName | String | Not null |
| recipe | Recipe | ManyToOne FK |

---

## API Endpoints

Base URL (production): `https://back-end-oo5f.onrender.com`
Base URL (local): `http://localhost:8080`

| Method | Path | Description | Request Body | Response |
|---|---|---|---|---|
| GET | `/api/recipes` | List all recipes | — | `List<RecipeDTO>` |
| GET | `/api/recipes/{id}` | Get recipe by ID | — | `RecipeDTO` |
| POST | `/api/recipes` | Create recipe | `Recipe` JSON | `{message, id}` 201 |
| PUT | `/api/recipes/{id}` | Update recipe | `Recipe` JSON | 204 No Content |
| POST | `/api/recipes/{id}/image` | Upload image | `multipart/form-data` (field: `file`) | 200 / 500 |
| DELETE | `/api/recipes/{id}` | Delete recipe | — | 204 No Content |
| GET | `/api/config/ping` | Health check | — | `"pong"` 200 |
| GET | `/uploads/**` | Serve uploaded images | — | Static file |

### Create/Update Recipe JSON shape
```json
{
  "title": "Spaghetti Bolognese",
  "difficulty": "Easy",
  "instructions": "Cook pasta and mix with sauce.",
  "creatorName": "John Doe",
  "imageUrl": null,
  "ingredients": [
    { "ingredientName": "spaghetti" },
    { "ingredientName": "tomato sauce" }
  ]
}
```

---

## Frontend Routes

| Path | Component | Description |
|---|---|---|
| `/` | `Dashboard.vue` | Recipe table with title search, difficulty filter, ingredient count filter, and pagination (5 per page) |
| `/recipes/:id` | `Details.vue` | Recipe detail view with edit and delete actions |
| `/create` | `CreateRecipe.vue` | Form to create a new recipe |
| `/recipes/:id/edit` | `EditRecipe.vue` | Form to edit an existing recipe |

---

## Development Setup

### Backend — Local

**Prerequisites**: Java 21, Maven (or use `./mvnw`), Docker

```bash
cd olive-backend

# Option 1: Run against local MySQL (Docker)
docker-compose up -d          # Starts MySQL on port 3306
./mvnw spring-boot:run

# Option 2: Run against TiDB Cloud (production DB)
# Credentials are already in application.properties
./mvnw spring-boot:run
```

API available at `http://localhost:8080`

To switch between local and TiDB Cloud databases, edit `application.properties`:
```properties
# TiDB Cloud (production)
spring.datasource.url=jdbc:mysql://...tidbcloud.com:4000/test?sslMode=VERIFY_IDENTITY

# Local MySQL (docker-compose)
# spring.datasource.url=jdbc:mysql://localhost:3306/recipe_db
# spring.datasource.username=root
# spring.datasource.password=root
```

### Frontend — Local

**Prerequisites**: Node.js, npm

```bash
cd olive-frontend
npm install
npm run dev          # Dev server at http://localhost:5173
```

**Important**: The frontend currently has hardcoded production backend URLs in several files. To run against a local backend, update the URLs in:
- `src/stores/recipeStore.js` — uncomment `localhost` lines
- `src/views/CreateRecipe.vue` — uncomment `localhost` lines
- `src/views/Details.vue` — uncomment `localhost` lines

---

## Build & Deployment

### Backend

```bash
cd olive-backend

# Build JAR
./mvnw clean package
./mvnw clean package -DskipTests    # Skip tests

# Run tests
./mvnw test

# Build Docker image
docker build -t recipe-vault-backend .

# Run Docker container
docker run -d -p 8080:8080 --name recipe-vault-backend recipe-vault-backend
```

The Dockerfile uses a two-stage build:
1. **Build stage**: `maven:3.9.6-eclipse-temurin-21` — downloads dependencies and builds the JAR
2. **Runtime stage**: `eclipse-temurin:21-jdk` — runs the JAR

### Frontend

```bash
cd olive-frontend

npm run build     # Production build → dist/
npm run serve     # Preview production build locally
```

Vercel auto-deploys on push to the default branch. The `vercel.json` rewrites all routes to `index.html` for SPA support.

---

## Key Conventions

### Backend Conventions

- **Package structure**: `com.olive_backend.Recipe.Vault.<Module>` — PascalCase for module directories
- **Layer separation**: Controller → Service → Repository (never skip layers)
- **DTOs**: Use `RecipeDTO` / `IngredientDTO` for API responses; never expose entities directly
- **Transactions**: Service methods that modify data use `@Transactional`
- **Validation**: Use `@Valid` in controllers and validation annotations on entities
- **Error handling**: Throw `ResponseStatusException` with appropriate HTTP status from Service layer
- **Lombok**: Use `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` on entities to reduce boilerplate
- **CORS**: `@CrossOrigin` on `RecipeController` allows all origins; adjust for production security
- **Image files**: Stored locally in `uploads/` directory, served via `/uploads/**` path. Only JPG/JPEG accepted.

### Frontend Conventions

- **Composition API**: All Vue components use `<script setup>` syntax
- **State management**: All API calls go through the Pinia store (`recipeStore.js`)
- **UI components**: Use Element Plus (`el-*`) components consistently throughout
- **Routing**: Use `useRouter` / `useRoute` from Vue Router for navigation and params
- **Styles**: Scoped styles (`<style scoped>`) per component
- **Image URL resolution**: Use the `getImageUrl()` helper pattern in views to handle null/missing images with a placeholder fallback

---

## Known Issues & Gotchas

1. **Hardcoded production URLs**: `recipeStore.js` and some view files hardcode `https://back-end-oo5f.onrender.com`. Local dev URLs are commented out. There is no `.env` / `VITE_API_URL` abstraction yet.

2. **RecipeDTO getter/setter bug**: `RecipeDTO.java` has mismatched method names:
   - Getter is `getInstruction()` (missing 's') but the field is `instructions`
   - `setInstructions()` assigns `this.instructions = instructions` (uses field instead of parameter)
   - This causes instructions not to display in the Details view, which calls `recipe.instruction`

3. **Render free-tier cold starts**: The backend uses `RenderKeepAliveConfig` (activated with `--spring.profiles.active=prod`) to self-ping every 14 minutes and prevent the free-tier instance from sleeping. This is not active in local development.

4. **No environment variable handling**: Database credentials are committed directly in `application.properties`. For production hardening, these should move to environment variables / secrets.

5. **Static file storage**: Uploaded images are stored on the Render container filesystem — they will be lost on redeployment. A persistent file storage solution (e.g., S3) would be needed for production durability.

6. **CORS is fully open**: `@CrossOrigin` without restrictions and `allowedOrigins("*")` in `WebConfig` — acceptable for development but should be restricted to the Vercel frontend URL in production.

7. **JSON Server mock data**: `data/db.json` provides a local mock API for frontend-only development, but it is not wired up by default in `package.json` scripts.

---

## Testing

### Backend

```bash
cd olive-backend
./mvnw test
```

The test file `RecipeVaultApplicationTests.java` contains only the Spring Boot context load test. No service or controller unit tests exist yet.

### Frontend

No frontend test framework is currently configured.

---

## Environment & Configuration

### Backend (`application.properties`)
```properties
spring.application.name=Recipe-Vault
spring.datasource.url=jdbc:mysql://<host>/test?sslMode=VERIFY_IDENTITY
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update    # Auto-syncs schema on startup
spring.jpa.show-sql=true                # Logs all SQL queries
spring.web.resources.static-locations=file:uploads/
```

### Frontend (no `.env` currently)
All configuration is hardcoded in source files. To add environment variable support, use Vite's `import.meta.env.VITE_*` pattern and create `.env` / `.env.production` files.

---

## Git Workflow

The main branch is `master`. Development branches should follow the pattern `claude/<description>`.

```bash
git checkout -b feature/your-feature
git push -u origin feature/your-feature
```
