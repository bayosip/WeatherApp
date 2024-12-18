![weather](https://github.com/user-attachments/assets/6e75e034-5b99-4b12-a667-eb25472f00b2)


# Android Weather App

This is an Android application that fetches weather data from a weather API. In order to use the app, you'll need to set up a **Weather API Key** in your local environment.

## Prerequisites

Before you can build and run the app, make sure you have the following:

- Android Studio installed.
- A valid **Weather API Key** (you can get one for free at [WeatherAPI](https://www.weatherapi.com/)).

## Getting Started

### 1. Clone the repository

Clone the repository to your local machine using Git:

```bash
git clone https://github.com/bayosip/WeatherApp.git
```

### 2. Open the project in Android Studio

- Open **Android Studio**.
- Select **Open an existing project** and navigate to the folder where you cloned the repo.
- Click **OK** to open the project.

### 3. Set up the API key

In order for the app to fetch weather data, you need to add your **Weather API Key** to the project.

- Go to the root of your project and locate the `local.properties` file.
- Open `local.properties` and add the following line:

  ```properties
  api-key=your-api-key-here
  ```

  **Note**: Replace `your-api-key-here` with the actual API key you obtained from [WeatherAPI](https://www.weatherapi.com/).

### 4. Sync Gradle

- Once the `local.properties` file is updated, sync your project with Gradle by clicking on the "Sync Now" prompt that appears in Android Studio, or go to **File > Sync Project with Gradle Files**.

### 5. Build and Run

Now you're ready to build and run the app:

1. Connect your Android device or start an emulator.
2. Click on the **Run** button (green triangle) in Android Studio.
3. The app should launch on your device, and you should see the weather data fetched from the API!

---

## Troubleshooting

- **API Key Issues**: If you see errors related to the API key, double-check that you have correctly added your key to the `local.properties` file.
- **Gradle Sync Errors**: If there are errors while syncing Gradle, try cleaning the project (**Build > Clean Project**) and then rebuilding it.

---

## Contributing

Feel free to fork this repository, make changes, and submit pull requests. If you encounter any issues, please open an issue, and we'll be happy to help.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
