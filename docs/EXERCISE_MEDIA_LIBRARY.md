# CoreFit — Exercise Media Library Specification

**Status:** Living media requirements document  
**Current storage:** Local Android resources  
**Future storage:** AWS S3 + CloudFront  
**Goal:** Define every image and short demo video required by CoreFit, what each asset should demonstrate, where it is stored, and production-ready prompts for generating consistent media.

## 1. Storage

Current images: `app/src/main/res/drawable/`  
Current videos: `app/src/main/res/raw/`

Use the stable `exercise.id` from `ProgramCatalog.kt`.

Example for `bridge`:
```text
exercise_bridge_preview.webp
exercise_bridge_start.webp
exercise_bridge_move.webp
exercise_bridge_hold.webp
exercise_bridge_return.webp
exercise_bridge_demo.mp4
```

Future S3 convention:
```text
s3://corefit-exercise-media-<env>/exercises/v1/<category_id>/<exercise_id>/preview.webp
s3://corefit-exercise-media-<env>/exercises/v1/<category_id>/<exercise_id>/start.webp
s3://corefit-exercise-media-<env>/exercises/v1/<category_id>/<exercise_id>/move.webp
s3://corefit-exercise-media-<env>/exercises/v1/<category_id>/<exercise_id>/hold.webp
s3://corefit-exercise-media-<env>/exercises/v1/<category_id>/<exercise_id>/return.webp
s3://corefit-exercise-media-<env>/exercises/v1/<category_id>/<exercise_id>/demo.mp4
```
Recommended later: `Android -> CloudFront -> private S3`, with device caching.

## 2. Common generation specification

Generated exercise form must be reviewed by a qualified professional before pain-related media is approved.

**IMAGE PREFIX — prepend to each image prompt:**
> CoreFit exercise instruction asset. Photorealistic adult fitness model, modest neutral athletic clothing, inclusive non-branded appearance, clean bright home/physiotherapy studio, uncluttered light neutral background, entire relevant body and joints visible, anatomically plausible posture, instructional rather than dramatic, soft even lighting, fixed informative camera angle, no text, labels, logos, watermark or collage. 16:9 landscape, 1280x720 or higher, sharp for Android mobile UI.

**VIDEO PREFIX — prepend to each video prompt:**
> CoreFit short exercise demonstration. Photorealistic adult fitness model, modest neutral athletic clothing, clean bright home/physiotherapy studio, uncluttered background, entire relevant body and joints continuously visible, anatomically plausible controlled movement, fixed camera, no cuts, zoom, text, logo or watermark, silent, 16:9 landscape, 1080p source, H.264-compatible, 24-30 fps, natural speed, near-seamless loop. Demonstrate only the named exercise and add no equipment unless specified.

**Phase rule:** keep the same person, clothing, room and camera across phases. `preview` = clearest representative pose; `start` = setup; `move` = principal movement; `hold` = exact HOLD pose; `return` = controlled lowering/return.

## 3. Exercise prompts

### Upper Back

#### Wall Angels — `wall_angels`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Wall angel against flat wall. START elbows bent about 90 degrees, ribs relaxed, neck neutral. MOVE/PREVIEW arms slide upward only through comfortable range without rib flare or shrugging. RETURN controlled descent.”
**Video:** VIDEO PREFIX + “Wall Angels, three-quarter angle showing wall contact and arm path. Two slow reps: elbows bent, slide upward comfortably with shoulders down, return slowly. 8–12 sec.”

#### Scapular Squeeze — `scapular_squeeze`
Assets: preview/start/move/hold/return + demo.
**Image:** IMAGE PREFIX + “Scapular squeeze. START shoulders neutral. MOVE draw shoulder blades gently back and down. HOLD long relaxed neck, no shrugging or exaggerated chest arch. RETURN release to neutral.”
**Video:** VIDEO PREFIX + “Rear three-quarter view. Two cycles: neutral, gently draw shoulder blades back/down, hold 2 seconds, release. 10–12 sec.”

#### Seated Thoracic Rotation — `thoracic_rotation`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Seated on stable chair, feet planted, pelvis forward. START upright. MOVE/PREVIEW rotate upper trunk gently while hips stay stable. RETURN to center.”
**Video:** VIDEO PREFIX + “Rotate upper trunk right, center, left, center while hips remain stable. Comfortable range. 10–15 sec.”

### Lower Back

#### Pelvic Tilt — `pelvic_tilt`
Assets: preview/start/move/hold/return + demo.
**Image:** IMAGE PREFIX + “Supine on mat, knees bent, feet flat, side view. START natural lumbar curve. MOVE/HOLD gentle posterior pelvic tilt lightly flattening lower back without lifting hips. RETURN neutral.”
**Video:** VIDEO PREFIX + “Two to three gentle pelvic tilts: neutral, flatten lower back lightly, hold 2 sec, release. Hips stay on mat. 8–12 sec.”

#### Glute Bridge — `bridge`
Assets: preview/start/move/hold/return + demo.
**Image:** IMAGE PREFIX + “Glute bridge side view. START supine, knees bent, feet hip-width. MOVE hips rise. HOLD/PREVIEW controlled shoulders-to-knees diagonal, glutes engaged, no excessive lumbar arch. RETURN lower slowly.”
**Video:** VIDEO PREFIX + “Two glute bridges: lift smoothly, hold top 2–3 sec without over-arching, lower slowly. 10–15 sec.”

#### Bird Dog — `bird_dog`
Assets: preview/start/move/hold/return + demo.
**Image:** IMAGE PREFIX + “Quadruped START hands under shoulders, knees under hips. MOVE opposite arm forward and leg back. HOLD/PREVIEW hips level, trunk steady. RETURN controlled.”
**Video:** VIDEO PREFIX + “One bird-dog rep each side, hold 2–3 sec, hips level and trunk steady, controlled return. 12–15 sec.”

#### Single Knee-to-Chest — `knee_to_chest`
Assets: preview/start/move/hold/return + demo.
**Image:** IMAGE PREFIX + “Supine. START comfortable neutral. MOVE hands guide one knee gently toward chest. HOLD/PREVIEW comfortable range without forcing. RETURN controlled release.”
**Video:** VIDEO PREFIX + “Bring one knee gently toward chest, hold comfortably several seconds, release slowly. No bouncing or forced range. 10–15 sec.”

### Shoulder

#### Pendulum — `pendulum`
Assets: preview/start/move + demo.
**Image:** IMAGE PREFIX + “Supported forward lean with one hand on stable chair/table, other arm fully relaxed. MOVE/PREVIEW small gentle circle driven by body sway, no active shoulder lifting.”
**Video:** VIDEO PREFIX + “Relax hanging arm and make small slow circles with support. Shoulder remains relaxed. 10–15 sec.”

#### Wall Slide — `wall_slide`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Shoulder wall slide. START arms comfortable low. MOVE/PREVIEW slide upward within comfortable range, shoulders down, neck relaxed. RETURN slowly.”
**Video:** VIDEO PREFIX + “Two wall-slide reps, comfortable range only, no shrugging or forced overhead position. 8–12 sec.”

#### Isometric External Rotation — `external_rotation`
Assets: preview/start/hold + demo.
**Image:** IMAGE PREFIX + “Elbow bent about 90 degrees and close to side. HOLD/PREVIEW gentle outward pressure against fixed wall/doorframe or opposite hand with no visible arm movement.”
**Video:** VIDEO PREFIX + “Set elbow at side, gently press outward against fixed resistance without moving arm, hold several seconds, relax. 8–12 sec.”

### Knee

#### Quad Set — `quad_set`
Assets: preview/start/hold + demo.
**Image:** IMAGE PREFIX + “Straight supported leg, optional rolled towel under knee. START thigh relaxed. HOLD/PREVIEW quadriceps tightened while knee gently presses toward support; leg does not lift.”
**Video:** VIDEO PREFIX + “Two quad-set cycles: tighten thigh, press knee down gently, hold, release. Show thigh/knee clearly. 8–12 sec.”

#### Straight Leg Raise — `straight_leg_raise`
Assets: preview/start/move/hold/return + demo.
**Image:** IMAGE PREFIX + “One knee bent, exercising leg straight. START leg on mat. MOVE raise straight leg modestly. HOLD/PREVIEW knee straight, pelvis stable. RETURN slow lower.”
**Video:** VIDEO PREFIX + “Two straight-leg raises: engage thigh, lift slowly, hold 1–2 sec, lower under control, pelvis stable. 10–15 sec.”

#### Calf Raise — `calf_raise`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Standing bilateral calf raise near stable support. START feet flat. MOVE/PREVIEW heels lift evenly onto balls of feet, ankles aligned. RETURN controlled lowering.”
**Video:** VIDEO PREFIX + “Two to three calf raises with light support available: rise, brief top, lower slowly. 8–12 sec.”

#### Sit to Stand — `sit_to_stand`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Stable chair. START seated, feet planted. MOVE torso inclines slightly and hips rise. PREVIEW controlled standing with knees tracking over feet. RETURN hips back for controlled sitting.”
**Video:** VIDEO PREFIX + “Two sit-to-stands: lean slightly, stand with knee alignment, reach hips back and sit under control. 10–15 sec.”

### Ankle

#### Ankle Alphabet — `ankle_alphabet`
Assets: preview/start/move + demo.
**Image:** IMAGE PREFIX + “Seated, leg supported and foot free. START ankle neutral. MOVE/PREVIEW foot traces letter using ankle motion while knee/hip stay mostly still. Foot and ankle prominent.”
**Video:** VIDEO PREFIX + “Trace several sample alphabet letters slowly using ankle motion without excessive whole-leg movement. 10–15 sec.”

#### Heel Raise — `heel_raise`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Standing with stable support within reach. START feet flat. MOVE/PREVIEW heels rise evenly. RETURN slowly to floor, upright posture.”
**Video:** VIDEO PREFIX + “Two to three supported heel raises, brief top pause, controlled lower. 8–12 sec.”

#### Toe Raise — `toe_raise`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Standing near support. START feet flat. MOVE/PREVIEW heels stay planted while forefoot/toes lift. RETURN slowly.”
**Video:** VIDEO PREFIX + “Two to three toe raises: heels planted, lift front of feet, pause, lower slowly. 8–12 sec.”

#### Supported Single-Leg Balance — `single_leg_balance`
Assets: preview/start/hold + demo.
**Image:** IMAGE PREFIX + “Beside stable chair/wall. START two feet down. HOLD/PREVIEW one foot lifted slightly, upright posture, support immediately available, optional light fingertip contact.”
**Video:** VIDEO PREFIX + “Start two feet down, lift one foot slightly, balance several seconds with support immediately available, return foot. 8–12 sec.”

### Core Strength

#### Dead Bug — `dead_bug`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Supine tabletop START, hips/knees about 90 degrees, arms up. MOVE/PREVIEW lower one arm overhead and opposite leg outward without excessive back arch. RETURN tabletop.”
**Video:** VIDEO PREFIX + “Alternating dead bug: lower opposite arm/leg with trunk controlled, return, switch sides. 10–15 sec.”

#### Glute Bridge — Core — `bridge_core`
Assets: preview/start/move/hold/return + demo.
**Image:** Reuse the approved `bridge` prompt/media.
**Video:** Reuse the approved `bridge` prompt/media rather than generating an inconsistent duplicate.

#### Bird Dog — Core — `bird_dog_core`
Assets: preview/start/move/hold/return + demo.
**Image:** Reuse approved `bird_dog` prompt/media.
**Video:** Reuse approved `bird_dog` prompt/media.

#### Front Plank — `plank`
Assets: preview/start/hold + demo.
**Image:** IMAGE PREFIX + “Forearm plank side view. HOLD/PREVIEW elbows under shoulders, neck neutral, controlled straight body line, hips neither sagging nor excessively raised, relaxed breathing.”
**Video:** VIDEO PREFIX + “Enter correct forearm plank, maintain steady alignment and breathing several seconds, safely lower. No push-ups. 8–12 sec.”

### General Fitness

#### Chair Squat — `chair_squat`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Stable chair behind model. START standing. MOVE hips back toward chair while knees track over feet. PREVIEW controlled squat near chair. RETURN stand smoothly.”
**Video:** VIDEO PREFIX + “Two chair squats: hips back, knees controlled, lightly touch/sit as appropriate, stand smoothly. 10–15 sec.”

#### Wall Push-Up — `wall_pushup`
Assets: preview/start/move/return + demo.
**Image:** IMAGE PREFIX + “Facing wall, hands around shoulder height/width. START straight body line. MOVE/PREVIEW elbows bend and chest moves toward wall while alignment stays straight. RETURN push away.”
**Video:** VIDEO PREFIX + “Two to three wall push-ups with straight body line, controlled toward wall and back, no hip sag or shrug. 8–12 sec.”

#### Standing March — `march`
Assets: preview/start/move + demo.
**Image:** IMAGE PREFIX + “Upright standing march. START feet down. MOVE/PREVIEW one knee lifts comfortably, opposite arm may swing naturally, torso tall and balanced.”
**Video:** VIDEO PREFIX + “Easy controlled march in place with alternating knees and natural arms, upright posture, no running/high impact. 8–12 sec.”

#### Calf Raise — General — `calf_raise_general`
Assets: preview/start/move/return + demo.
**Image:** Reuse approved `calf_raise` prompt/media.
**Video:** Reuse approved `calf_raise` prompt/media.

## 4. Production workflow

1. Generate preview first and review form/camera.
2. Lock model identity, clothing, room and camera.
3. Generate remaining phase images consistently.
4. Generate short video using the same setup.
5. Verify video matches phase images.
6. Review exercise form and safety.
7. Convert to WebP/MP4 requirements.
8. Store with exact filenames.
9. Track `generated -> reviewed -> approved` status.
10. Only approved media is eligible for public pain-related programs.

Reject/regenerate for anatomically impossible joints, incorrect technique, unsafe range, inconsistent person/camera, unrequested equipment, cropped instructional joints, embedded text/watermarks/logos, distracting camera movement/cuts, or video artifacts altering joint position.

## 5. Media manifest

Use a machine-readable manifest later:
```json
{
  "exerciseId": "bridge",
  "version": 1,
  "storage": "local",
  "preview": "exercise_bridge_preview",
  "start": "exercise_bridge_start",
  "move": "exercise_bridge_move",
  "hold": "exercise_bridge_hold",
  "return": "exercise_bridge_return",
  "video": "exercise_bridge_demo",
  "reviewStatus": "pending",
  "reviewedBy": null,
  "reviewedAt": null
}
```
Later replace local resource names with CloudFront URLs without changing `exerciseId`.

## 6. Initial production target

The current app catalog contains 26 entries, with three reusable concepts: `bridge -> bridge_core`, `bird_dog -> bird_dog_core`, `calf_raise -> calf_raise_general`. This yields **23 unique media sets**.

MVP per unique exercise: preview image + short demo video.  
Enhanced guidance: start/move/hold/return images as applicable.

## 7. Safety/content rule

AI-generated exercise media is a production draft, not clinical validation. For back, shoulder, knee, ankle or other pain-area programs, a qualified reviewer should confirm movement, range, cues and demonstration before release. CoreFit should present these as general exercise/conditioning programs rather than diagnosis or individualized treatment.
