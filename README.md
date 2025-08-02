# Critical TechWorks - Android News App Challenge

A modern, offline-first news application for Android built with 100% Kotlin and Jetpack Compose, showcasing a robust implementation of Clean Architecture principles. This app fetches top headlines from a news API, provides a detailed view for each article, and includes advanced features like biometric security and persistent user preferences.

## ✨ Features Implemented

-   **Dynamic News Feed**: Displays a list of top headlines from a selected news source.
-   **Offline Caching**: Implements a Single Source of Truth (SSoT) pattern using Room. The app is fully functional offline, showing the last fetched data.
-   **Pull-to-Refresh**: Users can swipe down to refresh the news feed with the latest articles.
-   **Article Detail View**: A dedicated screen to display the full image, title, description, and content of a selected article.
-   **Dynamic Source Selection**: A dropdown menu allows users to switch between different news sources at runtime.
-   **Persistent User Preferences**: The user's chosen news source is saved and restored across app sessions using Jetpack DataStore.
-   **Biometric Authentication**: On launch, the app requires fingerprint authentication if available and configured on the device, providing an extra layer of security.
-   **Comprehensive Unit Tests**: Extensive unit test coverage for ViewModels, UseCases, Repositories, and Mappers ensures code reliability and robustness.
-   **Internationalization (i18n)**: The app is prepared for a global audience with string resources for both English (default) and Portuguese.

## 🏛️ Architecture

This project follows the principles of **Clean Architecture**, separating concerns into three distinct layers:

-   **`UI Layer`**: Built entirely with **Jetpack Compose** and **Material 3**. It follows the **MVVM** pattern, with ViewModels exposing state via `StateFlow` in a Unidirectional Data Flow (UDF) manner.
-   **`Domain Layer`**: The core of the application. It is a pure Kotlin module containing the business logic (**UseCases**) and main data models. It is completely independent of the Android framework.
-   **`Data Layer`**: Manages all data operations. It uses the **Repository pattern** to abstract data sources and implements a **Single Source of Truth (SSoT)** strategy, where the Room database is the canonical source of data for the UI.

## 🛠️ Tech Stack & Libraries

-   **Core**
    -   [Kotlin](https://kotlinlang.org/): 100% Kotlin codebase.
    -   [Coroutines & Flow](https://kotlinlang.org/docs/coroutines-overview.html): For asynchronous operations and reactive data streams.

-   **UI**
    -   [Jetpack Compose](https://developer.android.com/jetpack/compose): For building the entire UI declaratively.
    -   [Material 3](https://m3.material.io/): For UI components and design system.
    -   [Navigation for Compose](https://developer.android.com/jetpack/compose/navigation): For navigating between screens.

-   **Architecture & DI**
    -   [Android Jetpack](https://developer.android.com/jetpack): ViewModel, DataStore.
    -   [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): For dependency injection.

-   **Data**
    -   [Retrofit](https://square.github.io/retrofit/): For networking.
    -   [Moshi](https://github.com/square/moshi): For efficient JSON parsing.
    -   [Room](https://developer.android.com/training/data-storage/room): For local database caching and offline support.
    -   [Coil](https://coil-kt.github.io/coil/): For asynchronous image loading and caching.

-   **Security**
    -   [AndroidX Biometric](https://developer.android.com/training/sign-in/biometric-auth): For fingerprint authentication.

-   **Testing**
    -   [JUnit 4](https://junit.org/junit4/): Test framework.
    -   [MockK](https://mockk.io/): For creating mocks in Kotlin tests.
    -   [Turbine](https://github.com/cashapp/turbine): A small testing library for `Flow`.
    -   `kotlinx-coroutines-test`: For managing and testing coroutines.

## ⚙️ Setup & Configuration

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    ```
2.  **Get an API Key:** This project uses the [NewsAPI](https://newsapi.org/). You will need to get a free API key from their website.
3.  **Create `local.properties` file:** In the root directory of the project, create a file named `local.properties`.
4.  **Add your API key:** Inside `local.properties`, add the following line, replacing `YOUR_API_KEY` with the key you obtained:
    ```properties
    API_KEY="YOUR_API_KEY"
    ```
5.  Open the project in Android Studio and run it. Gradle will handle the rest.

## 🚀 Future Improvements

-   **Instrumented Tests**: Add tests for the Room DAO and UI Composables.
-   **Paging**: Implement the Paging 3 library for infinite scrolling of headlines.
-   **Dynamic Source List**: Fetch the list of available news sources from the API instead of using a hardcoded list.
-   **Search Functionality**: Add a search bar to find articles by keywords.