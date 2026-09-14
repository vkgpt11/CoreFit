# RepFlow

RepFlow is a lightweight Android workout counter for simple rep-based exercise sessions. It automatically counts repetitions, manages sets and rest periods, and can provide voice and vibration cues so you do not need to keep looking at the screen while exercising.

## Features

- Configurable repetitions per set
- Configurable pace from 0.5 to 3 seconds per rep
- Configurable number of sets
- Configurable rest duration
- 3-second get-ready countdown
- Automatic transition between exercise and rest
- Android Text-to-Speech rep counting
- Optional vibration cues
- Pause, resume, stop, repeat, and reset controls
- Keeps the display awake during a workout
- Safe layout handling for status bars and punch-hole / display-cutout areas
- Light and dark theme support through Material 3

## Tech stack

- Kotlin
- Jetpack Compose
- Material 3
- Android Text-to-Speech
- Android vibration APIs
- GitHub Actions for APK builds

## Project structure

```text
app/
  src/main/
    java/com/example/simpleexercisecounter/MainActivity.kt
    res/
      drawable/ic_repflow.xml
      values/strings.xml
      values/styles.xml
.github/workflows/android-build.yml
```

## Build locally

Open the project in Android Studio and build the debug APK, or run:

```bash
gradle :app:assembleDebug
```

The APK is generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## GitHub Actions

The `Android APK Build` workflow runs automatically on pushes and pull requests to `main` and can also be started manually from the Actions tab.

After a successful build, download the artifact named:

```text
simple-exercise-counter-debug-apk
```

It contains `app-debug.apk`.

## Current scope

RepFlow intentionally stays focused on the core workout-counter experience. There are currently no accounts, cloud services, workout history, analytics, or external database dependencies.

## Possible next steps

- Saved exercise presets
- Timed hold / plank mode
- Custom exercise names
- Better pause/resume state handling
- Exercise history using local storage
- Adaptive launcher icon variants
- Foreground workout service for more reliable background execution

## License

This project is currently intended for personal development and experimentation. Add a formal open-source license before redistributing it publicly as an open-source project.
