# MyTaskPlanner 🧠📅

MyTaskPlanner is a **modular, scalable task management app** built using modern Android development best practices. It uses **Jetpack Compose**, **Kotlin**, and **Room**, organized with a clean architecture approach to showcase robust design patterns, reusability, and maintainability—perfect for interview evaluations.

---

## 🔍 Project Overview

MyTaskPlanner enables users to manage daily tasks with ease. Core functionalities include:

- ✅ Create, update, delete tasks
- 📅 Assign due dates and priorities
- 🔃 Sort tasks (priority, due date, alphabetical)
- 🔎 Filter by status (All, Completed, Pending)
- 🧠 MVVM & Use Case-based architecture
- 💾 Offline storage using Room DB

---

## 🧱 Architecture

This project follows a **Clean Architecture-inspired modular approach** with the following layers:

### 🧩 Layers

- **data**: Local Room DB setup, DAO, entities, and repository
- **domain.usecase**: Application-specific business logic
- **ui**: Jetpack Compose UI layer, split into components, screens, navigation, and theme
- **viewmodel**: ViewModel classes handling state & logic

No third-party dependency injection is used—dependencies are manually wired for transparency.

---

## 📂 Folder Structure

```plaintext
com.phalu.mytaskplanner/
├── data/
│   ├── local/
│   │   ├── converter/       # Type converters (e.g., Date)
│   │   ├── dao/             # DAO interfaces
│   │   ├── database/        # RoomDatabase setup
│   │   ├── entities/        # Data classes for Task, Priority, etc.
│   │   └── repository/      # Repository layer
│
├── di/                      # (Reserved for DI, currently unused)
│
├── domain/
│   └── usecase/             # Business logic (add, delete, update, get tasks)
│
├── ui/
│   ├── components/          # Reusable UI elements (buttons, filters)
│   ├── navigation/
│   │   └── routes/          # Navigation graph (AppNavigation.kt)
│   ├── screens/
│   │   ├── addtask/
│   │   │   └── category/    # AddTaskScreen UI
│   │   └── tasklist/        # TaskListScreen UI
│   └── theme/               # App-wide UI theme and helpers
│
├── viewmodel/
│   └── generics/            # ViewModels for each screen (TaskList, Add/Edit)
│
├── MainActivity.kt          # App entry point
└── MyTaskPlanner.kt         # Application scaffold & navigation
