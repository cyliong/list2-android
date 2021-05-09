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
- Database migration

## Widget
This project also implements a widget that can be added to the Home screen, with the following features:
- Display a list of items from the app's database (`ListView`, `RemoteViewsService`, `RemoteViewsFactory`, Room)
- Tap widget title bar to launch the app (`PendingIntent.getActivity()`)
- Tap the Add button to launch the app's New Item screen (`NavDeepLinkBuilder`)
- Tap a list item to launch the app's Edit Item screen (`PendingIntent` template, fill-in `Intent`, `NavDeepLinkBuilder` with arguments)
- Widget will be updated when list items are modified from the app (Broadcast using the `ACTION_APPWIDGET_UPDATE` `Intent`)

## Requirements
- Android Studio version 4.2 or higher
- Android 4.4 (API level 19) or higher
- Kotlin 1.4 or higher