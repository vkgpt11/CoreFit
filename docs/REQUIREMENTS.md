# CoreFit — Product Requirements & Backlog

**Status:** Living requirements document  
**Product:** CoreFit  
**Purpose:** Track the intended product experience, what is already available, and—most importantly—the requirements that are not yet fulfilled.

## 1. Product vision

CoreFit is a guided exercise companion. A user chooses a body-area/focus program, selects exercises, reviews the generated routine, and starts a hands-free guided session. CoreFit should control the sequence, repetitions, movement pace, hold time, sets, rest periods, transitions, voice prompts and vibration cues so the user can focus on movement rather than manually counting.

CoreFit must distinguish between **exercise focus areas** and medical diagnosis/treatment. Pain-related programs are general exercise/conditioning content and must not claim to diagnose, cure, or prescribe treatment for a disease or injury.

## 2. Current baseline

The current implementation already provides:

- CoreFit branding and launcher/in-app logo.
- Safe display handling for status bars and punch-hole/display-cutout areas.
- Focus categories: Upper Back, Lower Back, Shoulder, Knee, Ankle, Core Strength and General Fitness.
- A local curated exercise catalog with default execution parameters.
- Exercise selection before a session.
- Routine review before starting.
- Automatic exercise sequencing.
- Rep, set, hold, rest and transition phases.
- Voice prompts and vibration cues.
- Pause, resume and stop controls.
- GitHub Actions debug APK build.

The items below are the **unfulfilled or incomplete requirements**.

---

## 3. P0 — Guided exercise experience

### R-001 Exercise visualization
**Status:** Not implemented  
Every exercise must have a visual demonstration of correct movement. The user should be able to understand the start position, movement direction, end/hold position and return movement without relying only on text.

**Acceptance criteria**
- Each catalog exercise has an associated visual asset.
- Exercise selection/review can preview the movement.
- The active workout screen displays the current exercise visual.
- Assets work offline.

### R-002 Phase-aware movement guidance
**Status:** Not implemented  
The visual and instruction must change/highlight according to the current movement phase.

Example for Glute Bridge:
1. Start position.
2. Lift hips.
3. Hold at top.
4. Lower with control.
5. Repeat.

Voice guidance should match the phase, e.g. “Lift”, “Hold — 3, 2, 1”, “Lower”.

### R-003 Exercise instructions and form cues
**Status:** Partial  
Each exercise needs structured instructions rather than only a short cue string.

Required fields:
- setup/start position
- movement instruction
- breathing cue where useful
- hold instruction
- return instruction
- common mistakes
- stop/avoid cue

### R-004 Exercise transition experience
**Status:** Partial  
Between exercises CoreFit should announce and display the upcoming exercise rather than simply moving through a generic transition timer.

**Acceptance criteria**
- Show “Next: <exercise>”.
- Show the next exercise visual.
- Configurable preparation countdown.
- Voice announces the next exercise and start countdown.

### R-005 Robust pause/resume
**Status:** Incomplete  
Pausing must preserve the exact current phase and remaining duration. Resume must continue from that point rather than restarting a hold/rest/get-ready interval.

### R-006 Skip / previous exercise
**Status:** Not implemented  
During a routine the user must be able to skip the current exercise or return to the previous exercise, with confirmation where accidental navigation could disrupt progress.

---

## 4. P0 — Program and exercise configuration

### R-007 Per-exercise configuration
**Status:** Not implemented in UI  
The catalog contains defaults, but the user must later be able to override them before starting.

Configurable properties:
- repetitions
- sets
- movement/up duration
- hold duration
- return/down duration
- rest between sets
- transition/preparation duration
- voice prompts
- vibration

### R-008 Difficulty levels
**Status:** Not implemented  
Support at least:
- Beginner
- Standard
- Advanced
- Custom

A level should resolve to explicit exercise parameters. It must not silently infer medical suitability.

### R-009 Routine ordering
**Status:** Not implemented  
Users should be able to reorder selected exercises before starting the program.

### R-010 Save custom routine
**Status:** Not implemented  
Users should be able to save a selected/reordered/configured group of exercises as a reusable routine with a custom name.

### R-011 Exercise search/filter
**Status:** Not implemented  
As the catalog grows, users need search and filtering by body area, exercise type, equipment, movement mode and difficulty.

---

## 5. P0 — Safety and content governance

### R-012 Safety onboarding
**Status:** Not implemented  
Before using pain-area programs, show a concise safety notice explaining that CoreFit provides general exercise guidance and is not a diagnosis or replacement for professional medical advice.

### R-013 Stop-if-pain guidance
**Status:** Not implemented  
The workout experience must make it easy to stop an exercise/session if it causes pain or concerning symptoms. Safety messaging should be visible without overwhelming normal use.

### R-014 Red-flag / suitability flow
**Status:** Not implemented  
Before a pain-related routine, provide a lightweight screening/suitability step that can direct the user away from exercise and toward appropriate professional evaluation when necessary. The exact questions and language require clinical review before release.

### R-015 Clinically reviewed exercise content
**Status:** Not fulfilled  
The current catalog is a prototype catalog. Before positioning pain-area routines for public use, exercise selection, instructions, contraindications, progression and default dosage should be reviewed by an appropriately qualified physiotherapy/medical professional.

### R-016 Content provenance/versioning
**Status:** Not implemented  
Exercise definitions should carry content version, reviewer/source metadata and last-reviewed date so future changes are traceable.

---

## 6. P1 — Personalization and progression

### R-017 User profile/preferences
**Status:** Not implemented  
Store non-sensitive preferences such as preferred voice cues, vibration, default difficulty and rest style locally.

### R-018 Workout history
**Status:** Not implemented  
Persist completed sessions locally with date, category/routine, exercises completed, sets/reps/time and whether the session was completed or stopped.

### R-019 Progress tracking
**Status:** Not implemented  
Provide simple trends such as sessions completed, consistency, total exercise time and progression in configured reps/hold durations.

### R-020 Feedback after exercise/session
**Status:** Not implemented  
Allow the user to record simple feedback such as Too easy / Good / Too difficult and optionally discomfort. This data should inform future user-controlled progression, not perform diagnosis.

### R-021 Controlled progression
**Status:** Not implemented  
Later versions may recommend increasing/decreasing exercise volume based on explicit rules and user feedback. Any pain/rehabilitation-specific progression logic requires appropriate clinical validation.

---

## 7. P1 — Exercise modes

### R-022 Timed hold mode
**Status:** Partial  
Make timed holds a first-class exercise mode for planks, stretches and isometrics rather than representing everything through rep cycles.

### R-023 Side-specific exercises
**Status:** Not implemented  
Support exercises requiring Left and Right sides, including side switching, independent counts and voice prompts.

### R-024 Time-based continuous exercise
**Status:** Not implemented  
Support exercises such as walking/marching where the target is total duration rather than repetitions.

### R-025 Multi-phase cadence
**Status:** Partial  
Represent cadence explicitly, e.g. 2 seconds lift + 3 seconds hold + 2 seconds lower, rather than one generic movement duration.

---

## 8. P1 — App usability

### R-026 Scroll/responsive setup screens
**Status:** Needs verification/improvement  
All selection/review/configuration screens must work on small phones, large phones, font scaling and landscape/tablet layouts where supported. No controls may overflow.

### R-027 Accessibility
**Status:** Not fully implemented  
Provide content descriptions, adequate touch targets, screen-reader semantics, scalable text, sufficient contrast, reduced-motion considerations and non-audio alternatives to voice cues.

### R-028 Background workout reliability
**Status:** Not implemented  
Decide and implement expected behavior when the screen turns off, the user switches apps, receives a call, or Android backgrounds the process. A foreground workout service may be required.

### R-029 Audio focus
**Status:** Not implemented  
TTS guidance should coexist predictably with music/podcasts and phone audio. Handle audio focus and interruptions.

### R-030 TTS queue/cadence quality
**Status:** Needs improvement  
Fast exercise cadence must not cause spoken prompts to cut each other off. Voice sequencing should be designed around movement phases.

---

## 9. P2 — Content and platform growth

### R-031 Expand exercise catalog
**Status:** Ongoing  
Grow beyond the prototype catalog with clinically reviewed content, additional mobility/strength categories and optional equipment variants.

### R-032 Exercise asset management
**Status:** Not implemented  
Define a scalable convention for exercise images/animations/video, thumbnails, localization and asset versioning.

### R-033 Localization
**Status:** Not implemented  
Externalize all UI and exercise strings. Support additional languages and corresponding TTS behavior.

### R-034 Importable/remote program definitions
**Status:** Not implemented  
Move toward a data-driven catalog format so exercises/programs can eventually be updated without rewriting the execution engine. Remote updates should only be introduced with integrity/version controls.

### R-035 Favorites
**Status:** Not implemented  
Allow users to favorite exercises and routines for quick access.

### R-036 Calendar/reminders
**Status:** Not implemented  
Optionally schedule exercise reminders and planned routines. Reminders must be user-controlled and easy to disable.

---

## 10. Engineering quality requirements

### R-037 Automated tests
**Status:** Not implemented  
Add unit tests for routine generation and workout state transitions, plus Compose UI tests for the critical navigation/runner flows.

### R-038 State restoration
**Status:** Not implemented  
Define behavior for configuration changes/process recreation so an active routine is not unexpectedly lost.

### R-039 Architecture modularization
**Status:** Partial  
As the prototype grows, separate catalog/domain logic, workout engine, persistence and Compose UI rather than keeping the majority of behavior in `MainActivity.kt`.

### R-040 Release build/signing
**Status:** Not implemented  
CI currently produces a debug APK. Add a controlled signed release build when distribution begins. Signing secrets must not be committed to the repository.

### R-041 Quality gates
**Status:** Not implemented  
CI should run tests and static analysis before publishing an APK artifact.

### R-042 Analytics/privacy decision
**Status:** Not implemented  
Before adding analytics, accounts, cloud sync or health-related data collection, define what data is truly necessary, retention, consent, deletion and privacy requirements. Default to collecting as little sensitive information as possible.

---

## 11. Proposed delivery order

### Milestone 1 — Strong guided workout
R-001, R-002, R-003, R-004, R-005, R-006, R-025, R-030.

### Milestone 2 — Configurable programs
R-007, R-008, R-009, R-010, R-011, R-022, R-023, R-024.

### Milestone 3 — Safety/content readiness
R-012 through R-016 plus professional review of all pain-area program content.

### Milestone 4 — Persistence and progression
R-017 through R-021, R-035 and R-036.

### Milestone 5 — Production hardening
R-026 through R-029 and R-037 through R-042.

## 12. Product principles

1. **Hands-free first:** voice, timing and visuals should remove the need to count manually.
2. **Explicit over magical:** routine parameters should come from understandable templates/rules, not unexplained medical inference.
3. **Safe by design:** CoreFit is an exercise guide, not a diagnostic engine.
4. **Offline-friendly:** the core workout experience should work without network access.
5. **Simple first:** keep the current fast start experience even as the catalog grows.
6. **Configurable later:** good defaults first; deeper user configuration progressively exposed.
7. **Data-driven engine:** exercise content should evolve independently from the workout state machine.

---

This document should be updated whenever a requirement is implemented, changed, split, deferred or newly discovered.