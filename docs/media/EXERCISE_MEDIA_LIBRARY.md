# CoreFit Exercise Media Library

This folder defines the media contract for every guided exercise in CoreFit.

## Goal

Every exercise should eventually have:

- one reviewed preview image;
- one short silent looping demonstration clip;
- clear start/end movement representation;
- attribution/source metadata;
- review status and review date;
- an asset version.

The workout engine resolves media by exercise ID, so media can be replaced without changing timing/state-machine logic.

## Android asset convention

### Preview image

`app/src/main/res/drawable/exercise_<exercise_id>.webp`

Example:

`app/src/main/res/drawable/exercise_bridge.webp`

### Short demo video

`app/src/main/res/raw/exercise_<exercise_id>.mp4`

Example:

`app/src/main/res/raw/exercise_bridge.mp4`

Recommended clip characteristics:

- 4–10 seconds;
- muted/no embedded narration;
- seamless or near-seamless loop;
- portrait-friendly 4:3 or 1:1 framing;
- subject fully visible throughout movement;
- uncluttered background;
- no logos/watermarks;
- offline playback;
- compressed for mobile delivery.

## Review states

- `PLANNED` — asset not yet produced.
- `DRAFT` — asset exists but is not approved for release.
- `FORM_REVIEW` — movement/form needs qualified review.
- `LICENSE_REVIEW` — rights/attribution still being verified.
- `APPROVED` — acceptable for app distribution.

## Current catalog inventory

| Category | Exercise ID | Exercise | Image | Video |
|---|---|---|---|---|
| Upper Back | `wall_angels` | Wall Angels | PLANNED | PLANNED |
| Upper Back | `scapular_squeeze` | Scapular Squeeze | PLANNED | PLANNED |
| Upper Back | `thoracic_rotation` | Seated Thoracic Rotation | PLANNED | PLANNED |
| Lower Back | `pelvic_tilt` | Pelvic Tilt | PLANNED | PLANNED |
| Lower Back | `bridge` | Glute Bridge | PLANNED | PLANNED |
| Lower Back | `bird_dog` | Bird Dog | PLANNED | PLANNED |
| Lower Back | `knee_to_chest` | Single Knee-to-Chest | PLANNED | PLANNED |
| Shoulder | `pendulum` | Pendulum | PLANNED | PLANNED |
| Shoulder | `wall_slide` | Wall Slide | PLANNED | PLANNED |
| Shoulder | `external_rotation` | Isometric External Rotation | PLANNED | PLANNED |
| Knee | `quad_set` | Quad Set | PLANNED | PLANNED |
| Knee | `straight_leg_raise` | Straight Leg Raise | PLANNED | PLANNED |
| Knee | `calf_raise` | Calf Raise | PLANNED | PLANNED |
| Knee | `sit_to_stand` | Sit to Stand | PLANNED | PLANNED |
| Ankle | `ankle_alphabet` | Ankle Alphabet | PLANNED | PLANNED |
| Ankle | `heel_raise` | Heel Raise | PLANNED | PLANNED |
| Ankle | `toe_raise` | Toe Raise | PLANNED | PLANNED |
| Ankle | `single_leg_balance` | Supported Single-Leg Balance | PLANNED | PLANNED |
| Core | `dead_bug` | Dead Bug | PLANNED | PLANNED |
| Core | `bridge_core` | Glute Bridge | PLANNED | PLANNED |
| Core | `bird_dog_core` | Bird Dog | PLANNED | PLANNED |
| Core | `plank` | Front Plank | PLANNED | PLANNED |
| General | `chair_squat` | Chair Squat | PLANNED | PLANNED |
| General | `wall_pushup` | Wall Push-Up | PLANNED | PLANNED |
| General | `march` | Standing March | PLANNED | PLANNED |
| General | `calf_raise_general` | Calf Raise | PLANNED | PLANNED |

## Content rules

1. Visuals must demonstrate the exact exercise variant CoreFit describes.
2. Pain-area exercises need form/suitability review before `APPROVED`.
3. Do not use media merely because it appears online. Rights must allow redistribution inside the application.
4. Avoid imagery that implies diagnosis, guaranteed recovery, or treatment outcomes.
5. For side-specific movements, future assets should clearly identify left/right orientation.
6. Videos should demonstrate movement only; CoreFit supplies timing and voice cues itself.
7. When an asset is missing, the app must continue to work with text/voice guidance rather than crash.

## Production workflow

1. Produce or obtain candidate image/video.
2. Name it using the exercise ID convention.
3. Verify movement matches `ProgramCatalog.kt`.
4. Verify redistribution rights.
5. Perform form review where appropriate.
6. Update `exercise_media_manifest.json`.
7. Mark asset `APPROVED` only after reviews are complete.
8. Build/test APK on at least a small and large Android screen.

## Next media-production batch

Start with the exercises most useful for validating the experience end-to-end:

1. Glute Bridge
2. Bird Dog
3. Pelvic Tilt
4. Front Plank
5. Wall Angels
6. Sit to Stand
7. Straight Leg Raise
8. Calf Raise

Once the presentation/playback experience is validated, populate the remaining catalog using the same format.