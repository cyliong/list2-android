# list2-android
This is a simple list mobile app 
(to-do list, tasks, shopping list, recipes, and the like) 
showcasing the implementation of CRUD operations 
with various Android technologies and patterns, including:
- Single-activity architecture with Navigation component
- MVVM pattern with `ViewModel` and `LiveData`
- Jetpack Compose 
  - Migrated from View-based UI, using `ComposeView`
  - State hoisting with `MutableState` and `ViewModel`
  - Access `ViewModel` from composable using the `viewModel()` function
  - Composable preview
  - Side-effect API
- View Binding
- Room persistence library
- Repository pattern
- Singleton pattern for database and repository
- Coroutines

## Features
- Display a list of items (`RecyclerView`, `LiveData`)
- Navigate to a page to add or edit items 
  (Navigation component, Safe Args, 
  `TextField` with `FocusRequester` and `TextFieldValue`)
- Swipe to delete items (`ItemTouchHelper`)
- Store items in database (Room, coroutines)
- Database migration

## Widget
This project also implements a widget that can be added to the Home screen, 
with the following features:
- Display a list of items from the app's database 
  (`ListView`, `RemoteViewsService`, `RemoteViewsFactory`, Room)
- Tap widget title bar to launch the app (`PendingIntent.getActivity()`)
- Tap the Add button to launch the app's New Item screen (`NavDeepLinkBuilder`)
- Tap a list item to launch the app's Edit Item screen 
  (`PendingIntent` template, fill-in `Intent`, 
  `NavDeepLinkBuilder` with arguments)
- Widget will be updated when list items are modified from the app 
  (Broadcast using the `ACTION_APPWIDGET_UPDATE` `Intent`)

## Requirements
- Android Studio Bumblebee | 2021.1.1 Patch 1 or newer
- Android 5.0 (API level 21) or higher
- Kotlin 1.5 or higher