# CoreFit — Exercise Media Library Specification

**Status:** Living media requirements document  
**Current storage:** Local Android resources  
**Future storage:** AWS S3 + CloudFront  
**Goal:** Define every image and short demo video required by CoreFit, what each asset should demonstrate, and exactly where it should be stored.

---

## 1. Storage strategy

### Current — local Android resources

For the current version, all exercise media should be bundled inside the Android app.

Images:

```text
app/src/main/res/drawable/
```

Short videos:

```text
app/src/main/res/raw/
```

Use the exercise ID from `ProgramCatalog.kt` as the stable asset key.

### Naming convention

For an exercise with ID `bridge`:

```text
app/src/main/res/drawable/exercise_bridge_preview.webp
app/src/main/res/drawable/exercise_bridge_start.webp
app/src/main/res/drawable/exercise_bridge_move.webp
app/src/main/res/drawable/exercise_bridge_hold.webp
app/src/main/res/drawable/exercise_bridge_return.webp
app/src/main/res/raw/exercise_bridge_demo.mp4
```

Not every exercise needs every phase image. Only create phase images that materially improve instruction.

### Future — AWS S3

The same exercise IDs and filenames should later migrate to:

```text
s3://corefit-exercise-media-<env>/exercises/v1/<category_id>/<exercise_id>/
```

Example:

```text
s3://corefit-exercise-media-prod/exercises/v1/lower_back/bridge/preview.webp
s3://corefit-exercise-media-prod/exercises/v1/lower_back/bridge/start.webp
s3://corefit-exercise-media-prod/exercises/v1/lower_back/bridge/move.webp
s3://corefit-exercise-media-prod/exercises/v1/lower_back/bridge/hold.webp
s3://corefit-exercise-media-prod/exercises/v1/lower_back/bridge/return.webp
s3://corefit-exercise-media-prod/exercises/v1/lower_back/bridge/demo.mp4
```

Recommended future delivery:

```text
Android App -> CloudFront -> private S3 bucket
```

The Android app should eventually reference media metadata/URLs rather than embed all assets.

---

## 2. Standard asset requirements

### Preview image — required for every exercise

Purpose:
- exercise selection
- routine review
- fallback if video is unavailable

Recommended:
- WebP
- 16:9
- 1280×720 source or better
- clean background
- full body or relevant body region visible
- no unnecessary text embedded in image

### Start image — recommended

Shows the correct starting position before movement begins.

### Move image — recommended for rep-based exercises

Shows the principal movement or peak motion.

### Hold image — required when `holdSeconds > 0`

Shows exactly what position the user should maintain while CoreFit announces **Hold**.

### Return image — recommended for multi-phase exercises

Shows the controlled lowering/return phase when CoreFit announces **Lower** or **Return**.

### Demo video — required for final media-complete catalog

Recommended:
- MP4 / H.264
- 720p minimum
- 1080p source preferred
- 16:9 landscape preferred
- usually 6–15 seconds
- up to 20 seconds for more complex exercises
- silent is fine; CoreFit provides voice prompts
- demonstrate 1–3 complete repetitions
- clear camera angle
- correct form
- near-seamless loop preferred

---

# 3. Complete media inventory

## Upper Back

### Wall Angels
**ID:** `wall_angels`

Local files:
```text
drawable/exercise_wall_angels_preview.webp
drawable/exercise_wall_angels_start.webp
drawable/exercise_wall_angels_move.webp
drawable/exercise_wall_angels_return.webp
raw/exercise_wall_angels_demo.mp4
```

Image/video should show:
- start against wall
- controlled upward arm slide
- ribs relaxed
- controlled return

Video target: 8–12 sec, 2–3 repetitions.

### Scapular Squeeze
**ID:** `scapular_squeeze`

```text
drawable/exercise_scapular_squeeze_preview.webp
drawable/exercise_scapular_squeeze_start.webp
drawable/exercise_scapular_squeeze_move.webp
drawable/exercise_scapular_squeeze_hold.webp
drawable/exercise_scapular_squeeze_return.webp
raw/exercise_scapular_squeeze_demo.mp4
```

Show neutral shoulders -> blades gently back/down -> hold without shrugging -> release.

Video target: 10–15 sec.

### Seated Thoracic Rotation
**ID:** `thoracic_rotation`

```text
drawable/exercise_thoracic_rotation_preview.webp
drawable/exercise_thoracic_rotation_start.webp
drawable/exercise_thoracic_rotation_move.webp
drawable/exercise_thoracic_rotation_return.webp
raw/exercise_thoracic_rotation_demo.mp4
```

Show seated neutral posture and upper-trunk rotation while hips remain stable.

Video target: 10–15 sec.

---

## Lower Back

### Pelvic Tilt
**ID:** `pelvic_tilt`

```text
drawable/exercise_pelvic_tilt_preview.webp
drawable/exercise_pelvic_tilt_start.webp
drawable/exercise_pelvic_tilt_move.webp
drawable/exercise_pelvic_tilt_hold.webp
drawable/exercise_pelvic_tilt_return.webp
raw/exercise_pelvic_tilt_demo.mp4
```

Show neutral lumbar position -> gentle pelvic tilt/flattening -> brief hold -> release.

Video target: 8–12 sec.

### Glute Bridge
**ID:** `bridge`

```text
drawable/exercise_bridge_preview.webp
drawable/exercise_bridge_start.webp
drawable/exercise_bridge_move.webp
drawable/exercise_bridge_hold.webp
drawable/exercise_bridge_return.webp
raw/exercise_bridge_demo.mp4
```

Show supine start -> hips lift -> top hold -> controlled lower.

Important: no excessive lower-back arch.

Video target: 10–15 sec.

### Bird Dog
**ID:** `bird_dog`

```text
drawable/exercise_bird_dog_preview.webp
drawable/exercise_bird_dog_start.webp
drawable/exercise_bird_dog_move.webp
drawable/exercise_bird_dog_hold.webp
drawable/exercise_bird_dog_return.webp
raw/exercise_bird_dog_demo.mp4
```

Show quadruped -> opposite arm/leg extension -> stable hold -> controlled return.

Video target: 10–15 sec.

### Single Knee-to-Chest
**ID:** `knee_to_chest`

```text
drawable/exercise_knee_to_chest_preview.webp
drawable/exercise_knee_to_chest_start.webp
drawable/exercise_knee_to_chest_move.webp
drawable/exercise_knee_to_chest_hold.webp
drawable/exercise_knee_to_chest_return.webp
raw/exercise_knee_to_chest_demo.mp4
```

Show lying start -> one knee gently brought toward chest -> hold -> return.

Video target: 10–15 sec.

---

## Shoulder

### Pendulum
**ID:** `pendulum`

```text
drawable/exercise_pendulum_preview.webp
drawable/exercise_pendulum_start.webp
drawable/exercise_pendulum_move.webp
raw/exercise_pendulum_demo.mp4
```

Show supported forward lean, relaxed arm, small controlled circles.

Video target: 10–15 sec.

### Wall Slide
**ID:** `wall_slide`

```text
drawable/exercise_wall_slide_preview.webp
drawable/exercise_wall_slide_start.webp
drawable/exercise_wall_slide_move.webp
drawable/exercise_wall_slide_return.webp
raw/exercise_wall_slide_demo.mp4
```

Show arms sliding upward within a comfortable range and returning.

Video target: 8–12 sec.

### Isometric External Rotation
**ID:** `external_rotation`

```text
drawable/exercise_external_rotation_preview.webp
drawable/exercise_external_rotation_start.webp
drawable/exercise_external_rotation_hold.webp
raw/exercise_external_rotation_demo.mp4
```

Show elbow position and gentle outward pressure without visible arm movement.

Video target: 8–12 sec.

---

## Knee

### Quad Set
**ID:** `quad_set`

```text
drawable/exercise_quad_set_preview.webp
drawable/exercise_quad_set_start.webp
drawable/exercise_quad_set_hold.webp
raw/exercise_quad_set_demo.mp4
```

Show supported straight leg and visible thigh contraction while pressing knee toward support.

Video target: 8–12 sec.

### Straight Leg Raise
**ID:** `straight_leg_raise`

```text
drawable/exercise_straight_leg_raise_preview.webp
drawable/exercise_straight_leg_raise_start.webp
drawable/exercise_straight_leg_raise_move.webp
drawable/exercise_straight_leg_raise_hold.webp
drawable/exercise_straight_leg_raise_return.webp
raw/exercise_straight_leg_raise_demo.mp4
```

Show thigh engaged -> straight leg raised -> brief hold -> controlled lower.

Video target: 10–15 sec.

### Calf Raise — Knee Program
**ID:** `calf_raise`

```text
drawable/exercise_calf_raise_preview.webp
drawable/exercise_calf_raise_start.webp
drawable/exercise_calf_raise_move.webp
drawable/exercise_calf_raise_return.webp
raw/exercise_calf_raise_demo.mp4
```

Show standing neutral -> rise onto balls of feet -> controlled lowering.

Video target: 8–12 sec.

### Sit to Stand
**ID:** `sit_to_stand`

```text
drawable/exercise_sit_to_stand_preview.webp
drawable/exercise_sit_to_stand_start.webp
drawable/exercise_sit_to_stand_move.webp
drawable/exercise_sit_to_stand_return.webp
raw/exercise_sit_to_stand_demo.mp4
```

Show seated position -> stand with controlled knee alignment -> controlled sit.

Video target: 10–15 sec.

---

## Ankle

### Ankle Alphabet
**ID:** `ankle_alphabet`

```text
drawable/exercise_ankle_alphabet_preview.webp
drawable/exercise_ankle_alphabet_start.webp
drawable/exercise_ankle_alphabet_move.webp
raw/exercise_ankle_alphabet_demo.mp4
```

Show seated supported leg and foot tracing letters with ankle movement.

Video target: 10–15 sec sample; does not need to show entire alphabet.

### Heel Raise
**ID:** `heel_raise`

```text
drawable/exercise_heel_raise_preview.webp
drawable/exercise_heel_raise_start.webp
drawable/exercise_heel_raise_move.webp
drawable/exercise_heel_raise_return.webp
raw/exercise_heel_raise_demo.mp4
```

Show neutral standing -> heels lift -> controlled return, with support visible if used.

Video target: 8–12 sec.

### Toe Raise
**ID:** `toe_raise`

```text
drawable/exercise_toe_raise_preview.webp
drawable/exercise_toe_raise_start.webp
drawable/exercise_toe_raise_move.webp
drawable/exercise_toe_raise_return.webp
raw/exercise_toe_raise_demo.mp4
```

Show heels maintained on floor while forefoot/toes lift and lower.

Video target: 8–12 sec.

### Supported Single-Leg Balance
**ID:** `single_leg_balance`

```text
drawable/exercise_single_leg_balance_preview.webp
drawable/exercise_single_leg_balance_start.webp
drawable/exercise_single_leg_balance_hold.webp
raw/exercise_single_leg_balance_demo.mp4
```

Show safe standing position with wall/chair support immediately available.

Video target: 8–12 sec.

---

## Core Strength

### Dead Bug
**ID:** `dead_bug`

```text
drawable/exercise_dead_bug_preview.webp
drawable/exercise_dead_bug_start.webp
drawable/exercise_dead_bug_move.webp
drawable/exercise_dead_bug_return.webp
raw/exercise_dead_bug_demo.mp4
```

Show tabletop start -> opposite arm/leg lower -> trunk remains controlled -> return.

Video target: 10–15 sec.

### Glute Bridge — Core Program
**ID:** `bridge_core`

```text
drawable/exercise_bridge_core_preview.webp
drawable/exercise_bridge_core_start.webp
drawable/exercise_bridge_core_move.webp
drawable/exercise_bridge_core_hold.webp
drawable/exercise_bridge_core_return.webp
raw/exercise_bridge_core_demo.mp4
```

Can reuse the same source footage/imagery as `bridge` if technically mapped separately.

### Bird Dog — Core Program
**ID:** `bird_dog_core`

```text
drawable/exercise_bird_dog_core_preview.webp
drawable/exercise_bird_dog_core_start.webp
drawable/exercise_bird_dog_core_move.webp
drawable/exercise_bird_dog_core_hold.webp
drawable/exercise_bird_dog_core_return.webp
raw/exercise_bird_dog_core_demo.mp4
```

Can reuse the same source media as `bird_dog` if mapped separately.

### Front Plank
**ID:** `plank`

```text
drawable/exercise_plank_preview.webp
drawable/exercise_plank_start.webp
drawable/exercise_plank_hold.webp
raw/exercise_plank_demo.mp4
```

Show correct plank alignment and steady breathing position.

Video target: 8–12 sec.

---

## General Fitness

### Chair Squat
**ID:** `chair_squat`

```text
drawable/exercise_chair_squat_preview.webp
drawable/exercise_chair_squat_start.webp
drawable/exercise_chair_squat_move.webp
drawable/exercise_chair_squat_return.webp
raw/exercise_chair_squat_demo.mp4
```

Show standing -> controlled squat toward chair -> stand.

Video target: 10–15 sec.

### Wall Push-Up
**ID:** `wall_pushup`

```text
drawable/exercise_wall_pushup_preview.webp
drawable/exercise_wall_pushup_start.webp
drawable/exercise_wall_pushup_move.webp
drawable/exercise_wall_pushup_return.webp
raw/exercise_wall_pushup_demo.mp4
```

Show straight body alignment -> controlled movement toward wall -> push back.

Video target: 8–12 sec.

### Standing March
**ID:** `march`

```text
drawable/exercise_march_preview.webp
drawable/exercise_march_start.webp
drawable/exercise_march_move.webp
raw/exercise_march_demo.mp4
```

Show upright posture and alternating knee lift at easy cadence.

Video target: 8–12 sec.

### Calf Raise — General Fitness
**ID:** `calf_raise_general`

```text
drawable/exercise_calf_raise_general_preview.webp
drawable/exercise_calf_raise_general_start.webp
drawable/exercise_calf_raise_general_move.webp
drawable/exercise_calf_raise_general_return.webp
raw/exercise_calf_raise_general_demo.mp4
```

Can reuse the same source media as `calf_raise` if mapped separately.

---

# 4. Asset count

Current catalog contains **25 exercise entries**.

Minimum MVP media target:

- 25 preview images
- 25 short demo videos

Enhanced guided target:

- preview image for every exercise
- start image for every exercise
- move image for every rep-based exercise
- hold image for every exercise with a hold phase
- return image for every multi-phase rep exercise
- short demo video for every exercise

Some duplicate exercise concepts (`bridge` / `bridge_core`, `bird_dog` / `bird_dog_core`, `calf_raise` / `calf_raise_general`) may reuse the same master media while keeping separate app IDs.

---

# 5. Integration rules

The app should resolve media by stable `exercise.id`.

Current local resolution pattern:

```text
Image: exercise_<exercise_id>_<phase>
Video: exercise_<exercise_id>_demo
```

Examples:

```text
exercise_bridge_preview
exercise_bridge_hold
exercise_bridge_demo
```

If a phase image is unavailable:

1. fall back to `preview`
2. never crash the workout
3. continue voice/timer guidance normally

If a video is unavailable:

1. show `preview.webp`
2. continue the routine normally

---

# 6. Future S3 migration rules

When moving from local storage to S3:

- retain the same category IDs
- retain the same exercise IDs
- retain equivalent filenames
- replace Android resource lookup with a media manifest/URL lookup
- use CloudFront for delivery
- cache images and videos locally on device
- prefetch the next exercise while the current exercise runs
- keep the core workout usable even when media download fails
- do not store AWS access keys in the mobile app

Suggested S3 structure:

```text
exercises/v1/<category>/<exercise-id>/preview.webp
exercises/v1/<category>/<exercise-id>/start.webp
exercises/v1/<category>/<exercise-id>/move.webp
exercises/v1/<category>/<exercise-id>/hold.webp
exercises/v1/<category>/<exercise-id>/return.webp
exercises/v1/<category>/<exercise-id>/demo.mp4
exercises/v1/<category>/<exercise-id>/metadata.json
```

---

# 7. Media quality and review checklist

Before an asset is considered complete:

- exercise name and ID match the catalog
- correct movement is demonstrated
- relevant joints/body alignment are visible
- no unsafe or misleading form
- no copyrighted third-party asset without explicit usage rights
- no watermark
- neutral and consistent visual presentation
- video is short and loop-friendly
- image/video reviewed for exercise correctness before public release

Pain-area exercise media should receive appropriate professional review before CoreFit is positioned as a public rehabilitation or condition-management product.

---

# 8. Recommended implementation order

### Phase 1 — MVP media
Create `preview.webp` + `demo.mp4` for all 25 exercise IDs.

### Phase 2 — guided phase images
Add start/move/hold/return images for exercises where phase-specific visual guidance adds value.

### Phase 3 — media polish
Standardize models, camera angles, backgrounds, lighting, cropping and CoreFit visual identity.

### Phase 4 — cloud migration
Move the same logical library to S3/CloudFront without changing exercise IDs or workout behavior.
