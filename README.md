# CoreFit

CoreFit is an Android guided-exercise companion built with Kotlin and Jetpack Compose. It helps users choose a body-area or fitness focus, select exercises, review an automatically structured routine, and then follow the routine with timed movement, holds, sets, rest periods, voice cues, and vibration cues.

![CoreFit logo](app/src/main/res/drawable/corefit_logo.webp)

## Product flow

1. Choose a focus area.
2. Review the curated exercises for that area.
3. Select or remove exercises for today's routine.
4. Review the generated sequence.
5. Start the guided program.
6. CoreFit automatically advances through movements, holds, sets, rest periods, and exercise transitions.

## Current focus areas

- Upper Back
- Lower Back
- Shoulder
- Knee
- Ankle
- Core Strength
- General Fitness

These are exercise focus areas, not diagnoses. CoreFit currently provides general exercise templates and does not diagnose a condition or create an individualized medical treatment plan.

## Exercise model

Every exercise owns its own execution defaults rather than relying on one global counter. An exercise can define:

- Rep-based or timed-hold mode
- Repetitions
- Sets
- Movement duration per repetition
- Hold duration
- Rest between sets
- Transition time before the next exercise
- Simple form cues

For example, a Glute Bridge can be modeled as 10 repetitions × 2 sets with a controlled movement phase, a 3-second hold at the top, and a 25-second set rest.

## Guided runner

The runner supports these phases:

- Get Ready
- Move
- Hold
- Set Rest
- Next Exercise / Transition
- Complete

The screen stays awake during a routine and uses Android Text-to-Speech and vibration cues so users do not need to continuously watch the display.

## Safety and scope

CoreFit is an exercise guidance application, not a medical device. Exercise selection should eventually be reviewed with qualified clinical/physiotherapy input before any condition-specific program is presented as therapeutic guidance.

Users should stop an exercise if it clearly worsens symptoms and seek professional assessment for severe, persistent, new, or concerning symptoms.

## Technology

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
    java/com/example/simpleexercisecounter/
      MainActivity.kt       # navigation, selection UI and guided runner
      ProgramCatalog.kt     # categories, exercises and default execution recipes
    res/
      drawable/corefit_logo.webp
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

The `CoreFit Android APK Build` workflow runs on pushes and pull requests to `main` and can also be started manually.

Successful builds publish the artifact:

```text
corefit-debug-apk
```

## Planned evolution

- User-configurable reps, holds, sets and rest
- Exercise illustrations / demonstrations
- Better pause/resume state preservation
- Routine history and favorites
- Local persistence for saved programs
- Difficulty/progression levels
- Contraindication and safety metadata
- Clinician-reviewed program content
- Warm-up and cool-down stages
- Accessibility improvements
- Background workout service

## License

This project is currently intended for personal development and experimentation. Add a formal license before redistributing it as an open-source project.
