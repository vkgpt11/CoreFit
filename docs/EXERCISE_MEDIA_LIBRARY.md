# CoreFit — Exercise Media Library

**Status:** Living production tracker  
**Current storage:** Local Android resources  
**Future storage:** AWS S3 + CloudFront  
**Catalog:** 26 exercise entries / 23 unique media sets

This document is intentionally table-first so an exercise can be followed from app ID -> required media -> generation prompt -> file location -> production status.

## 1. Storage and naming

| Item | Current local location | Future location |
|---|---|---|
| Images | `app/src/main/res/drawable/` | `s3://corefit-exercise-media-<env>/exercises/v1/<category>/<exercise_id>/` |
| Videos | `app/src/main/res/raw/` | `s3://corefit-exercise-media-<env>/exercises/v1/<category>/<exercise_id>/` |
| Delivery | Bundled with APK | `Android -> CloudFront -> private S3`, with device caching |

Naming example for `bridge`:

| Asset | Local filename | Future S3 object |
|---|---|---|
| Preview | `exercise_bridge_preview.webp` | `lower_back/bridge/preview.webp` |
| Start | `exercise_bridge_start.webp` | `lower_back/bridge/start.webp` |
| Move | `exercise_bridge_move.webp` | `lower_back/bridge/move.webp` |
| Hold | `exercise_bridge_hold.webp` | `lower_back/bridge/hold.webp` |
| Return | `exercise_bridge_return.webp` | `lower_back/bridge/return.webp` |
| Demo | `exercise_bridge_demo.mp4` | `lower_back/bridge/demo.mp4` |

## 2. Common generation specification

Use these prefixes with the exercise-specific prompt in the tables below.

**IMAGE PREFIX**  
`CoreFit exercise instruction asset. Photorealistic adult fitness model, modest neutral athletic clothing, inclusive non-branded appearance, clean bright home/physiotherapy studio, uncluttered light neutral background, entire relevant body and joints visible, anatomically plausible posture, instructional rather than dramatic, soft even lighting, fixed informative camera angle, no text, labels, logos, watermark or collage. 16:9 landscape, 1280x720 or higher, sharp for Android mobile UI. Keep the same person, clothing, room and camera across all phase images.`

**VIDEO PREFIX**  
`CoreFit short exercise demonstration. Photorealistic adult fitness model, modest neutral athletic clothing, clean bright home/physiotherapy studio, uncluttered background, entire relevant body and joints continuously visible, anatomically plausible controlled movement, fixed camera, no cuts, zoom, text, logo or watermark, silent, 16:9 landscape, 1080p source, H.264-compatible, 24-30 fps, natural speed, near-seamless loop. Demonstrate only the named exercise and add no equipment unless specified.`

Phase meaning: **Preview** = clearest representative pose; **Start** = setup; **Move** = principal movement; **Hold** = exact position held while CoreFit says HOLD; **Return** = controlled lowering/return.

## 3. Master production tracker

`Pending` means the asset has not yet been generated/reviewed and added to the app.

| # | Category | Exercise | ID | Images needed | Video | Reuse | Status |
|---:|---|---|---|---|---|---|---|
| 1 | Upper Back | Wall Angels | `wall_angels` | Preview, Start, Move, Return | 8–12s | — | Pending |
| 2 | Upper Back | Scapular Squeeze | `scapular_squeeze` | Preview, Start, Move, Hold, Return | 10–12s | — | Pending |
| 3 | Upper Back | Seated Thoracic Rotation | `thoracic_rotation` | Preview, Start, Move, Return | 10–15s | — | Pending |
| 4 | Lower Back | Pelvic Tilt | `pelvic_tilt` | Preview, Start, Move, Hold, Return | 8–12s | — | Pending |
| 5 | Lower Back | Glute Bridge | `bridge` | Preview, Start, Move, Hold, Return | 10–15s | Master | Pending |
| 6 | Lower Back | Bird Dog | `bird_dog` | Preview, Start, Move, Hold, Return | 12–15s | Master | Pending |
| 7 | Lower Back | Single Knee-to-Chest | `knee_to_chest` | Preview, Start, Move, Hold, Return | 10–15s | — | Pending |
| 8 | Shoulder | Pendulum | `pendulum` | Preview, Start, Move | 10–15s | — | Pending |
| 9 | Shoulder | Wall Slide | `wall_slide` | Preview, Start, Move, Return | 8–12s | — | Pending |
| 10 | Shoulder | Isometric External Rotation | `external_rotation` | Preview, Start, Hold | 8–12s | — | Pending |
| 11 | Knee | Quad Set | `quad_set` | Preview, Start, Hold | 8–12s | — | Pending |
| 12 | Knee | Straight Leg Raise | `straight_leg_raise` | Preview, Start, Move, Hold, Return | 10–15s | — | Pending |
| 13 | Knee | Calf Raise | `calf_raise` | Preview, Start, Move, Return | 8–12s | Master | Pending |
| 14 | Knee | Sit to Stand | `sit_to_stand` | Preview, Start, Move, Return | 10–15s | — | Pending |
| 15 | Ankle | Ankle Alphabet | `ankle_alphabet` | Preview, Start, Move | 10–15s | — | Pending |
| 16 | Ankle | Heel Raise | `heel_raise` | Preview, Start, Move, Return | 8–12s | — | Pending |
| 17 | Ankle | Toe Raise | `toe_raise` | Preview, Start, Move, Return | 8–12s | — | Pending |
| 18 | Ankle | Supported Single-Leg Balance | `single_leg_balance` | Preview, Start, Hold | 8–12s | — | Pending |
| 19 | Core | Dead Bug | `dead_bug` | Preview, Start, Move, Return | 10–15s | — | Pending |
| 20 | Core | Glute Bridge | `bridge_core` | Preview, Start, Move, Hold, Return | 10–15s | Reuse `bridge` | Reuse |
| 21 | Core | Bird Dog | `bird_dog_core` | Preview, Start, Move, Hold, Return | 12–15s | Reuse `bird_dog` | Reuse |
| 22 | Core | Front Plank | `plank` | Preview, Start, Hold | 8–12s | — | Pending |
| 23 | General | Chair Squat | `chair_squat` | Preview, Start, Move, Return | 10–15s | — | Pending |
| 24 | General | Wall Push-Up | `wall_pushup` | Preview, Start, Move, Return | 8–12s | — | Pending |
| 25 | General | Standing March | `march` | Preview, Start, Move | 8–12s | — | Pending |
| 26 | General | Calf Raise | `calf_raise_general` | Preview, Start, Move, Return | 8–12s | Reuse `calf_raise` | Reuse |

## 4. Generation prompts and exact files

The **Image prompt** below is appended to IMAGE PREFIX. Generate each required phase as a separate still while preserving the same person/camera/setup. The **Video prompt** is appended to VIDEO PREFIX.

| Category | Exercise / ID | Exact local files | Image generation prompt | Video generation prompt |
|---|---|---|---|---|
| Upper Back | **Wall Angels** `wall_angels` | `exercise_wall_angels_preview.webp`<br>`exercise_wall_angels_start.webp`<br>`exercise_wall_angels_move.webp`<br>`exercise_wall_angels_return.webp`<br>`exercise_wall_angels_demo.mp4` | Wall angel against a flat wall. START: elbows bent about 90°, ribs relaxed, neck neutral. MOVE/PREVIEW: arms slide upward only through comfortable range without rib flare or shrugging. RETURN: controlled descent. | Wall Angels, three-quarter angle showing wall contact and arm path. Two slow reps: elbows bent, slide upward comfortably with shoulders down, return slowly. 8–12 sec. |
| Upper Back | **Scapular Squeeze** `scapular_squeeze` | `exercise_scapular_squeeze_preview.webp`<br>`exercise_scapular_squeeze_start.webp`<br>`exercise_scapular_squeeze_move.webp`<br>`exercise_scapular_squeeze_hold.webp`<br>`exercise_scapular_squeeze_return.webp`<br>`exercise_scapular_squeeze_demo.mp4` | START shoulders neutral. MOVE: draw shoulder blades gently back and down. HOLD/PREVIEW: long relaxed neck, no shrugging or exaggerated chest arch. RETURN: release to neutral. | Rear three-quarter view. Two cycles: neutral, gently draw shoulder blades back/down, hold 2 sec, release. 10–12 sec. |
| Upper Back | **Seated Thoracic Rotation** `thoracic_rotation` | `exercise_thoracic_rotation_preview.webp`<br>`exercise_thoracic_rotation_start.webp`<br>`exercise_thoracic_rotation_move.webp`<br>`exercise_thoracic_rotation_return.webp`<br>`exercise_thoracic_rotation_demo.mp4` | Seated on stable chair, feet planted, pelvis forward. START upright. MOVE/PREVIEW: rotate upper trunk gently while hips stay stable. RETURN to center. | Rotate upper trunk right, center, left, center while hips remain stable. Comfortable range. 10–15 sec. |
| Lower Back | **Pelvic Tilt** `pelvic_tilt` | `exercise_pelvic_tilt_preview.webp`<br>`exercise_pelvic_tilt_start.webp`<br>`exercise_pelvic_tilt_move.webp`<br>`exercise_pelvic_tilt_hold.webp`<br>`exercise_pelvic_tilt_return.webp`<br>`exercise_pelvic_tilt_demo.mp4` | Supine on mat, knees bent, feet flat, side view. START natural lumbar curve. MOVE/HOLD/PREVIEW: gentle posterior pelvic tilt lightly flattening lower back without lifting hips. RETURN neutral. | Two to three gentle pelvic tilts: neutral, flatten lower back lightly, hold 2 sec, release. Hips stay on mat. 8–12 sec. |
| Lower Back | **Glute Bridge** `bridge` | `exercise_bridge_preview.webp`<br>`exercise_bridge_start.webp`<br>`exercise_bridge_move.webp`<br>`exercise_bridge_hold.webp`<br>`exercise_bridge_return.webp`<br>`exercise_bridge_demo.mp4` | Side view. START supine, knees bent, feet hip-width. MOVE: hips rise. HOLD/PREVIEW: controlled shoulders-to-knees diagonal, glutes engaged, no excessive lumbar arch. RETURN: lower slowly. | Two glute bridges: lift smoothly, hold top 2–3 sec without over-arching, lower slowly. 10–15 sec. |
| Lower Back | **Bird Dog** `bird_dog` | `exercise_bird_dog_preview.webp`<br>`exercise_bird_dog_start.webp`<br>`exercise_bird_dog_move.webp`<br>`exercise_bird_dog_hold.webp`<br>`exercise_bird_dog_return.webp`<br>`exercise_bird_dog_demo.mp4` | Quadruped START hands under shoulders, knees under hips. MOVE: opposite arm forward and leg back. HOLD/PREVIEW: hips level, trunk steady. RETURN controlled. | One bird-dog rep each side, hold 2–3 sec, hips level and trunk steady, controlled return. 12–15 sec. |
| Lower Back | **Single Knee-to-Chest** `knee_to_chest` | `exercise_knee_to_chest_preview.webp`<br>`exercise_knee_to_chest_start.webp`<br>`exercise_knee_to_chest_move.webp`<br>`exercise_knee_to_chest_hold.webp`<br>`exercise_knee_to_chest_return.webp`<br>`exercise_knee_to_chest_demo.mp4` | Supine. START comfortable neutral. MOVE: hands guide one knee gently toward chest. HOLD/PREVIEW: comfortable range without forcing. RETURN controlled release. | Bring one knee gently toward chest, hold comfortably several seconds, release slowly. No bouncing or forced range. 10–15 sec. |
| Shoulder | **Pendulum** `pendulum` | `exercise_pendulum_preview.webp`<br>`exercise_pendulum_start.webp`<br>`exercise_pendulum_move.webp`<br>`exercise_pendulum_demo.mp4` | Supported forward lean with one hand on stable chair/table, other arm fully relaxed. MOVE/PREVIEW: small gentle circle driven by body sway, no active shoulder lifting. | Relax hanging arm and make small slow circles with support. Shoulder remains relaxed. 10–15 sec. |
| Shoulder | **Wall Slide** `wall_slide` | `exercise_wall_slide_preview.webp`<br>`exercise_wall_slide_start.webp`<br>`exercise_wall_slide_move.webp`<br>`exercise_wall_slide_return.webp`<br>`exercise_wall_slide_demo.mp4` | START arms comfortable low. MOVE/PREVIEW: slide upward within comfortable range, shoulders down, neck relaxed. RETURN slowly. | Two wall-slide reps, comfortable range only, no shrugging or forced overhead position. 8–12 sec. |
| Shoulder | **Isometric External Rotation** `external_rotation` | `exercise_external_rotation_preview.webp`<br>`exercise_external_rotation_start.webp`<br>`exercise_external_rotation_hold.webp`<br>`exercise_external_rotation_demo.mp4` | Elbow bent about 90° and close to side. HOLD/PREVIEW: gentle outward pressure against fixed wall/doorframe or opposite hand with no visible arm movement. | Set elbow at side, gently press outward against fixed resistance without moving arm, hold several seconds, relax. 8–12 sec. |
| Knee | **Quad Set** `quad_set` | `exercise_quad_set_preview.webp`<br>`exercise_quad_set_start.webp`<br>`exercise_quad_set_hold.webp`<br>`exercise_quad_set_demo.mp4` | Straight supported leg, optional rolled towel under knee. START thigh relaxed. HOLD/PREVIEW: quadriceps tightened while knee gently presses toward support; leg does not lift. | Two quad-set cycles: tighten thigh, press knee down gently, hold, release. Show thigh/knee clearly. 8–12 sec. |
| Knee | **Straight Leg Raise** `straight_leg_raise` | `exercise_straight_leg_raise_preview.webp`<br>`exercise_straight_leg_raise_start.webp`<br>`exercise_straight_leg_raise_move.webp`<br>`exercise_straight_leg_raise_hold.webp`<br>`exercise_straight_leg_raise_return.webp`<br>`exercise_straight_leg_raise_demo.mp4` | One knee bent, exercising leg straight. START leg on mat. MOVE: raise straight leg modestly. HOLD/PREVIEW: knee straight, pelvis stable. RETURN: slow lower. | Two straight-leg raises: engage thigh, lift slowly, hold 1–2 sec, lower under control, pelvis stable. 10–15 sec. |
| Knee | **Calf Raise** `calf_raise` | `exercise_calf_raise_preview.webp`<br>`exercise_calf_raise_start.webp`<br>`exercise_calf_raise_move.webp`<br>`exercise_calf_raise_return.webp`<br>`exercise_calf_raise_demo.mp4` | Standing bilateral calf raise near stable support. START feet flat. MOVE/PREVIEW: heels lift evenly onto balls of feet, ankles aligned. RETURN controlled lowering. | Two to three calf raises with light support available: rise, brief top, lower slowly. 8–12 sec. |
| Knee | **Sit to Stand** `sit_to_stand` | `exercise_sit_to_stand_preview.webp`<br>`exercise_sit_to_stand_start.webp`<br>`exercise_sit_to_stand_move.webp`<br>`exercise_sit_to_stand_return.webp`<br>`exercise_sit_to_stand_demo.mp4` | Stable chair. START seated, feet planted. MOVE: torso inclines slightly and hips rise. PREVIEW: controlled standing with knees tracking over feet. RETURN: hips back for controlled sitting. | Two sit-to-stands: lean slightly, stand with knee alignment, reach hips back and sit under control. 10–15 sec. |
| Ankle | **Ankle Alphabet** `ankle_alphabet` | `exercise_ankle_alphabet_preview.webp`<br>`exercise_ankle_alphabet_start.webp`<br>`exercise_ankle_alphabet_move.webp`<br>`exercise_ankle_alphabet_demo.mp4` | Seated, leg supported and foot free. START ankle neutral. MOVE/PREVIEW: foot traces a letter using ankle motion while knee/hip stay mostly still. Foot and ankle prominent. | Trace several sample alphabet letters slowly using ankle motion without excessive whole-leg movement. 10–15 sec. |
| Ankle | **Heel Raise** `heel_raise` | `exercise_heel_raise_preview.webp`<br>`exercise_heel_raise_start.webp`<br>`exercise_heel_raise_move.webp`<br>`exercise_heel_raise_return.webp`<br>`exercise_heel_raise_demo.mp4` | Standing with stable support within reach. START feet flat. MOVE/PREVIEW: heels rise evenly. RETURN slowly to floor, upright posture. | Two to three supported heel raises, brief top pause, controlled lower. 8–12 sec. |
| Ankle | **Toe Raise** `toe_raise` | `exercise_toe_raise_preview.webp`<br>`exercise_toe_raise_start.webp`<br>`exercise_toe_raise_move.webp`<br>`exercise_toe_raise_return.webp`<br>`exercise_toe_raise_demo.mp4` | Standing near support. START feet flat. MOVE/PREVIEW: heels stay planted while forefoot/toes lift. RETURN slowly. | Two to three toe raises: heels planted, lift front of feet, pause, lower slowly. 8–12 sec. |
| Ankle | **Supported Single-Leg Balance** `single_leg_balance` | `exercise_single_leg_balance_preview.webp`<br>`exercise_single_leg_balance_start.webp`<br>`exercise_single_leg_balance_hold.webp`<br>`exercise_single_leg_balance_demo.mp4` | Beside stable chair/wall. START two feet down. HOLD/PREVIEW: one foot lifted slightly, upright posture, support immediately available, optional light fingertip contact. | Start two feet down, lift one foot slightly, balance several seconds with support immediately available, return foot. 8–12 sec. |
| Core | **Dead Bug** `dead_bug` | `exercise_dead_bug_preview.webp`<br>`exercise_dead_bug_start.webp`<br>`exercise_dead_bug_move.webp`<br>`exercise_dead_bug_return.webp`<br>`exercise_dead_bug_demo.mp4` | Supine tabletop START, hips/knees about 90°, arms up. MOVE/PREVIEW: lower one arm overhead and opposite leg outward without excessive back arch. RETURN tabletop. | Alternating dead bug: lower opposite arm/leg with trunk controlled, return, switch sides. 10–15 sec. |
| Core | **Glute Bridge** `bridge_core` | Reuse `bridge` files through media alias/mapping. | Reuse the approved `bridge` image set; do not generate an inconsistent duplicate. | Reuse the approved `bridge` demo video. |
| Core | **Bird Dog** `bird_dog_core` | Reuse `bird_dog` files through media alias/mapping. | Reuse the approved `bird_dog` image set. | Reuse the approved `bird_dog` demo video. |
| Core | **Front Plank** `plank` | `exercise_plank_preview.webp`<br>`exercise_plank_start.webp`<br>`exercise_plank_hold.webp`<br>`exercise_plank_demo.mp4` | Forearm plank side view. HOLD/PREVIEW: elbows under shoulders, neck neutral, controlled straight body line, hips neither sagging nor excessively raised, relaxed breathing. | Enter correct forearm plank, maintain steady alignment and breathing several seconds, safely lower. No push-ups. 8–12 sec. |
| General | **Chair Squat** `chair_squat` | `exercise_chair_squat_preview.webp`<br>`exercise_chair_squat_start.webp`<br>`exercise_chair_squat_move.webp`<br>`exercise_chair_squat_return.webp`<br>`exercise_chair_squat_demo.mp4` | Stable chair behind model. START standing. MOVE: hips back toward chair while knees track over feet. PREVIEW: controlled squat near chair. RETURN: stand smoothly. | Two chair squats: hips back, knees controlled, lightly touch/sit as appropriate, stand smoothly. 10–15 sec. |
| General | **Wall Push-Up** `wall_pushup` | `exercise_wall_pushup_preview.webp`<br>`exercise_wall_pushup_start.webp`<br>`exercise_wall_pushup_move.webp`<br>`exercise_wall_pushup_return.webp`<br>`exercise_wall_pushup_demo.mp4` | Facing wall, hands around shoulder height/width. START straight body line. MOVE/PREVIEW: elbows bend and chest moves toward wall while alignment stays straight. RETURN: push away. | Two to three wall push-ups with straight body line, controlled toward wall and back, no hip sag or shrug. 8–12 sec. |
| General | **Standing March** `march` | `exercise_march_preview.webp`<br>`exercise_march_start.webp`<br>`exercise_march_move.webp`<br>`exercise_march_demo.mp4` | Upright standing march. START feet down. MOVE/PREVIEW: one knee lifts comfortably, opposite arm may swing naturally, torso tall and balanced. | Easy controlled march in place with alternating knees and natural arms, upright posture, no running/high impact. 8–12 sec. |
| General | **Calf Raise** `calf_raise_general` | Reuse `calf_raise` files through media alias/mapping. | Reuse the approved `calf_raise` image set. | Reuse the approved `calf_raise` demo video. |

## 5. Future S3 mapping table

Do **not** implement S3 yet. This table preserves the migration contract.

| App ID | Current media key | Future S3 folder |
|---|---|---|
| `wall_angels` | `wall_angels` | `upper_back/wall_angels/` |
| `scapular_squeeze` | `scapular_squeeze` | `upper_back/scapular_squeeze/` |
| `thoracic_rotation` | `thoracic_rotation` | `upper_back/thoracic_rotation/` |
| `pelvic_tilt` | `pelvic_tilt` | `lower_back/pelvic_tilt/` |
| `bridge` | `bridge` | `lower_back/bridge/` |
| `bird_dog` | `bird_dog` | `lower_back/bird_dog/` |
| `knee_to_chest` | `knee_to_chest` | `lower_back/knee_to_chest/` |
| `pendulum` | `pendulum` | `shoulder/pendulum/` |
| `wall_slide` | `wall_slide` | `shoulder/wall_slide/` |
| `external_rotation` | `external_rotation` | `shoulder/external_rotation/` |
| `quad_set` | `quad_set` | `knee/quad_set/` |
| `straight_leg_raise` | `straight_leg_raise` | `knee/straight_leg_raise/` |
| `calf_raise` | `calf_raise` | `knee/calf_raise/` |
| `sit_to_stand` | `sit_to_stand` | `knee/sit_to_stand/` |
| `ankle_alphabet` | `ankle_alphabet` | `ankle/ankle_alphabet/` |
| `heel_raise` | `heel_raise` | `ankle/heel_raise/` |
| `toe_raise` | `toe_raise` | `ankle/toe_raise/` |
| `single_leg_balance` | `single_leg_balance` | `ankle/single_leg_balance/` |
| `dead_bug` | `dead_bug` | `core/dead_bug/` |
| `bridge_core` | alias -> `bridge` | alias/reuse master `bridge` media |
| `bird_dog_core` | alias -> `bird_dog` | alias/reuse master `bird_dog` media |
| `plank` | `plank` | `core/plank/` |
| `chair_squat` | `chair_squat` | `general/chair_squat/` |
| `wall_pushup` | `wall_pushup` | `general/wall_pushup/` |
| `march` | `march` | `general/march/` |
| `calf_raise_general` | alias -> `calf_raise` | alias/reuse master `calf_raise` media |

## 6. Production status meaning

| Status | Meaning |
|---|---|
| Pending | Nothing approved yet |
| Generated | Image/video generated but not form-reviewed |
| Reviewed | Form and media quality reviewed; corrections may remain |
| Approved | Safe/acceptable for intended CoreFit release context |
| Added Local | Approved files added under Android `drawable` / `raw` |
| Reuse | Uses an approved master media set through mapping |

## 7. Production workflow

| Step | Action | Exit condition |
|---:|---|---|
| 1 | Generate **preview image** using IMAGE PREFIX + row prompt | Pose, camera and model look usable |
| 2 | Review exercise form | No obvious unsafe/anatomically incorrect form |
| 3 | Lock person, clothing, room and camera | Visual identity fixed for that exercise |
| 4 | Generate Start/Move/Hold/Return images required by tracker | Phases visually match |
| 5 | Generate demo using VIDEO PREFIX + row prompt | Motion matches still phases |
| 6 | Review movement and safety | Form accepted; regenerate if needed |
| 7 | Export WebP / H.264 MP4 | Meets mobile format requirements |
| 8 | Save under exact filenames from table | Android resources resolve correctly |
| 9 | Mark tracker status | Production state is visible |
| 10 | Later migrate approved master assets to S3/CloudFront | Same exercise/media keys retained |

Reject/regenerate media for anatomically impossible joints, incorrect technique, unsafe range, inconsistent person/camera, unrequested equipment, cropped instructional joints, embedded text/watermarks/logos, distracting camera movement/cuts, or video artifacts altering joint position.

## 8. MVP scope

Because three catalog entries reuse master media (`bridge_core`, `bird_dog_core`, `calf_raise_general`), CoreFit needs **23 unique media sets**.

| Scope | Required |
|---|---|
| MVP | 23 approved preview images + 23 approved short demo videos |
| Enhanced guided experience | Add Start/Move/Hold/Return images according to the tracker |
| Current storage | Local Android resources |
| Future storage | Private S3 + CloudFront + device cache |

## 9. Safety/content rule

AI-generated exercise media is a production draft, not clinical validation. For back, shoulder, knee, ankle or other pain-area programs, a qualified reviewer should confirm movement, range, cues and demonstration before release. CoreFit should present these as general exercise/conditioning programs rather than diagnosis or individualized treatment.
