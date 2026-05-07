# LabSync — Laboratory Management System

A full-stack web application for managing laboratory scheduling in educational institutions. It features a React frontend, a Node.js/Express REST API backend, MongoDB for data persistence, and a **Genetic Algorithm** that auto-generates conflict-free lab timetables.

---

## How It Works

The app has two separate parts that run independently:

**Backend (`labsync-node-backend/`)** — An Express REST API on port `8080` that connects to MongoDB. It exposes endpoints for managing labs, batches, subjects, staff, students, schedules, and timetables. The core feature is a Genetic Algorithm (`src/utils/GeneticAlgorithm.js`) triggered via `POST /api/admin/regenerate-timetable` that takes batches, labs, staff, days, and time slots as inputs and produces an optimised, clash-minimised schedule.

**Frontend (`LabManagementSystem-main/LabManagementSystem-main/`)** — A React (CRA) app with Tailwind CSS on port `3000`. It talks to the backend via Axios. JWT tokens are stored in `localStorage`. There are two roles: **admin** (full access including timetable generation) and **staff** (read-only view of schedules).

### Pages

| Route | Description |
|---------------|-------------------------------------------------------------------|
| `/`           | Public landing/home page                                          |
| `/login`      | Login form                                                        |
| `/dashboard`  | Stats overview (students, staff, labs, batches, active schedules) |
| `/labs`       | View and manage lab rooms                                         |
| `/batches`    | View and manage student batches                                   |
| `/schedule`   | View and manage lab schedules                                     |
| `/timetable`  | Timetable view                                                    |
| `/clashes`    | Clash detection report                                            |
| `/admin`      | Admin panel — trigger Genetic Algorithm timetable regeneration    |

### Authentication

Auth is currently hardcoded in the backend. Two accounts work out of the box:

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | Admin — full access |
| `staff` | `staff123` | Staff — limited access |

---

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React 18, React Router v6, Axios, Tailwind CSS, Lucide React |
| Backend | Node.js, Express 5, Mongoose, JWT, bcryptjs, Morgan |
| Database | MongoDB |
| Dev tooling | Nodemon |

---

## Prerequisites

- Node.js v18+
- npm
- MongoDB running locally (or a MongoDB Atlas URI)

---

## Local Setup

### 1. Clone the repository

```bash
git clone https://github.com/suyashjaiswal-gif/Labsync_node.git
cd Labsync_node
```

### 2. Set up the Backend

```bash
cd labsync-node-backend
npm install
```

The `.env` file is already committed with defaults:

```env
PORT=8080
MONGODB_URI=mongodb://localhost:27017/labsync
JWT_SECRET=LabSyncJWTSecretKey2024VeryLongAndSecureKeyForHMACSHA256AlgorithmUsage
JWT_EXPIRES_IN=24h
```

If you're using MongoDB Atlas, replace `MONGODB_URI` with your connection string.

**(Optional but recommended) Seed the database with roles and the admin user:**

```bash
node seed.js
```

You can also seed days and time slots (required for the Genetic Algorithm to work):

```bash
node seed-days.js
node seed-timeslots.js
```

**Start the backend:**

```bash
npm run dev       # development with nodemon (auto-restart)
# or
npm start         # production
```

The API will be live at `http://localhost:8080`. Test it:
```
GET http://localhost:8080/api/health
```

### 3. Set up the Frontend

Open a **new terminal**:

```bash
cd LabManagementSystem-main/LabManagementSystem-main
npm install
npm start
```

The React app will open at `http://localhost:3000/LabManagementSystem`.

> **Note:** The frontend uses `/LabManagementSystem` as its basename (configured for GitHub Pages). React Router will handle this automatically in local development.

---

## API Overview

All endpoints are prefixed with `/api`.

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/login` | Login and receive JWT |
| `GET` | `/admin/dashboard-stats` | Aggregate stats for dashboard |
| `POST` | `/admin/regenerate-timetable` | Run Genetic Algorithm and save new schedule |
| `GET` | `/schedules` | Get all schedules (populated) |
| `GET` | `/labs` | List all labs |
| `GET/POST` | `/batches` | List or create batches |
| `GET/POST` | `/staff` | List or create staff |
| `GET/POST` | `/students` | List or create students |
| `GET/POST` | `/subjects` | List or create subjects |
| `GET` | `/time-slots` | List time slots |
| `GET` | `/days` | List days |

Protected routes require the `Authorization: Bearer <token>` header.

---

## Project Structure
```
Labsync_node/
├── labsync-node-backend/
│   ├── server.js                  # Entry point — connects to DB and starts Express
│   ├── src/
│   │   ├── app.js                 # Express app, middleware, route registration
│   │   ├── config/db.js           # Mongoose connection
│   │   ├── controllers/           # Route handler logic
│   │   ├── middleware/
│   │   │   └── authMiddleware.js  # JWT verification, admin role guard
│   │   ├── models/                # Mongoose schemas (auto-incrementing integer IDs)
│   │   ├── routes/                # Express routers
│   │   └── utils/
│   │       └── GeneticAlgorithm.js  # Timetable generation engine
│   ├── seed.js                    # Seeds roles + admin user
│   ├── seed-days.js               # Seeds Mon–Fri days
│   └── seed-timeslots.js          # Seeds time slot blocks
│
└── LabManagementSystem-main/
└── LabManagementSystem-main/
├── src/
│   ├── App.js             # Routes and auth-protected layout
│   ├── api/axios.js       # Axios instance pointing to localhost:8080
│   ├── context/
│   │   └── AuthContext.js # Auth state, login/logout
│   ├── components/        # Layout, Sidebar
│   └── pages/             # Dashboard, Labs, Batches, Schedule, Timetable, Clashes, Admin
└── public/
```
---
---

## Genetic Algorithm

The timetable generator (`POST /api/admin/regenerate-timetable`) runs a Genetic Algorithm with:

- **Population size:** 50 chromosomes
- **Max generations:** 50
- **Mutation rate:** 5%
- **Elitism:** top chromosome carried forward each generation

Each gene in a chromosome represents a scheduled session (batch + subject + staff + lab + day + time slot). The algorithm minimises conflicts such as double-booked labs, staff assigned to multiple sessions in the same slot, and lab capacity mismatches.

After running, all existing schedules are cleared and replaced with the new generated ones.

---

## Notes

- The `node_modules/` directory inside `labsync-node-backend` is committed to the repo, so `npm install` in the backend may be skipped — but running it is still recommended to ensure consistency.
- The `.env` file is committed with a default JWT secret. Change this before any non-local deployment.
- The frontend API base URL is hardcoded to `http://localhost:8080/api` in `src/api/axios.js`. Update this if you deploy the backend elsewhere.
