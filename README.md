# list2-android
This is a simple list mobile app (to-do list, tasks, shopping list, recipes, and the like) 
showcasing the implementation of CRUD operations 
with various Android technologies and patterns, including:
- Single-activity architecture with Navigation component
- MVVM pattern with `ViewModel` and `LiveData`
- View Binding
- Room persistence library
- Repository pattern
- Singleton pattern for database and repository
- Coroutines

## Features
- Display a list of items (`RecyclerView`, `LiveData`)
- Navigate to a page to add or edit items (Navigation component, Safe Args, `EditText`)
- Swipe to delete items (`ItemTouchHelper`)
- Store items in database (Room, coroutines)

## Requirements
- Android Studio version 3.6 or higher
- Android 4.4 (API level 19) or higher
- Kotlin 1.3 or higher