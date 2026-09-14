package com.example.simpleexercisecounter

enum class ExerciseMode { REPS, HOLD }

data class ExerciseTemplate(
    val id: String,
    val name: String,
    val description: String,
    val mode: ExerciseMode = ExerciseMode.REPS,
    val reps: Int = 10,
    val sets: Int = 2,
    val moveSeconds: Float = 2f,
    val holdSeconds: Int = 0,
    val restBetweenSets: Int = 20,
    val transitionSeconds: Int = 15,
    val cues: List<String> = emptyList()
)

data class ProgramCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val disclaimer: String? = null,
    val exercises: List<ExerciseTemplate>
)

object ProgramCatalog {
    val categories = listOf(
        ProgramCategory(
            id = "upper_back",
            title = "Upper Back",
            subtitle = "Mobility and upper-back strength",
            exercises = listOf(
                ExerciseTemplate("wall_angels", "Wall Angels", "Slow shoulder-blade and upper-back movement against a wall.", reps = 8, sets = 2, moveSeconds = 4f, restBetweenSets = 20, cues = listOf("Keep ribs relaxed", "Move only in a comfortable range")),
                ExerciseTemplate("scapular_squeeze", "Scapular Squeeze", "Gently draw the shoulder blades back and down.", reps = 10, sets = 2, moveSeconds = 2f, holdSeconds = 3, cues = listOf("Do not shrug", "Keep neck relaxed")),
                ExerciseTemplate("thoracic_rotation", "Seated Thoracic Rotation", "Rotate the upper trunk slowly while keeping the hips stable.", reps = 8, sets = 2, moveSeconds = 4f, cues = listOf("Rotate gently", "Do not force the range"))
            )
        ),
        ProgramCategory(
            id = "lower_back",
            title = "Lower Back",
            subtitle = "Gentle mobility and trunk-support exercises",
            disclaimer = "For general self-management only. Stop if symptoms worsen or pain travels down the leg, and seek medical advice for severe or persistent symptoms.",
            exercises = listOf(
                ExerciseTemplate("pelvic_tilt", "Pelvic Tilt", "Gently flatten and release the lower back while lying comfortably.", reps = 10, sets = 2, moveSeconds = 2f, holdSeconds = 2, restBetweenSets = 15),
                ExerciseTemplate("bridge", "Glute Bridge", "Lift the hips, hold briefly at the top, then lower with control.", reps = 10, sets = 2, moveSeconds = 2f, holdSeconds = 3, restBetweenSets = 25, cues = listOf("Squeeze glutes at the top", "Do not over-arch the lower back")),
                ExerciseTemplate("bird_dog", "Bird Dog", "Extend opposite arm and leg while keeping the trunk steady.", reps = 8, sets = 2, moveSeconds = 2f, holdSeconds = 3, restBetweenSets = 25, cues = listOf("Keep hips level", "Move slowly")),
                ExerciseTemplate("knee_to_chest", "Single Knee-to-Chest", "Bring one knee gently toward the chest and return.", reps = 6, sets = 2, moveSeconds = 3f, holdSeconds = 5, restBetweenSets = 15, cues = listOf("Use a comfortable range only"))
            )
        ),
        ProgramCategory(
            id = "shoulder",
            title = "Shoulder",
            subtitle = "Gentle mobility and shoulder-support work",
            exercises = listOf(
                ExerciseTemplate("pendulum", "Pendulum", "Relax the arm and make small controlled circles.", mode = ExerciseMode.HOLD, sets = 2, holdSeconds = 30, restBetweenSets = 20, cues = listOf("Let the shoulder stay relaxed")),
                ExerciseTemplate("wall_slide", "Wall Slide", "Slide the arms upward on a wall within a comfortable range.", reps = 8, sets = 2, moveSeconds = 4f, restBetweenSets = 20),
                ExerciseTemplate("external_rotation", "Isometric External Rotation", "Press the back of the hand gently outward without moving the arm.", mode = ExerciseMode.HOLD, sets = 3, holdSeconds = 10, restBetweenSets = 15, cues = listOf("Use gentle pressure", "Stop if painful"))
            )
        ),
        ProgramCategory(
            id = "knee",
            title = "Knee",
            subtitle = "Knee-supporting strength and control",
            exercises = listOf(
                ExerciseTemplate("quad_set", "Quad Set", "Tighten the thigh with the leg supported and hold.", mode = ExerciseMode.HOLD, sets = 3, holdSeconds = 10, restBetweenSets = 15),
                ExerciseTemplate("straight_leg_raise", "Straight Leg Raise", "Raise the straight leg slowly while keeping the thigh engaged.", reps = 10, sets = 2, moveSeconds = 3f, holdSeconds = 2, restBetweenSets = 25),
                ExerciseTemplate("calf_raise", "Calf Raise", "Rise onto the balls of the feet and lower with control.", reps = 10, sets = 2, moveSeconds = 3f, restBetweenSets = 25),
                ExerciseTemplate("sit_to_stand", "Sit to Stand", "Stand from a chair and sit back down with control.", reps = 8, sets = 2, moveSeconds = 4f, restBetweenSets = 30, cues = listOf("Keep knees tracking over feet"))
            )
        ),
        ProgramCategory(
            id = "ankle",
            title = "Ankle",
            subtitle = "Mobility, calf strength and balance",
            exercises = listOf(
                ExerciseTemplate("ankle_alphabet", "Ankle Alphabet", "Trace the alphabet slowly with the foot.", mode = ExerciseMode.HOLD, sets = 1, holdSeconds = 45, restBetweenSets = 10),
                ExerciseTemplate("heel_raise", "Heel Raise", "Rise onto the toes and lower slowly, using support if needed.", reps = 10, sets = 2, moveSeconds = 3f, restBetweenSets = 20),
                ExerciseTemplate("toe_raise", "Toe Raise", "Lift the front of the feet while keeping heels down.", reps = 10, sets = 2, moveSeconds = 3f, restBetweenSets = 20),
                ExerciseTemplate("single_leg_balance", "Supported Single-Leg Balance", "Balance near a wall or chair for support.", mode = ExerciseMode.HOLD, sets = 2, holdSeconds = 20, restBetweenSets = 20, cues = listOf("Keep support within reach"))
            )
        ),
        ProgramCategory(
            id = "core",
            title = "Core Strength",
            subtitle = "Trunk stability and control",
            exercises = listOf(
                ExerciseTemplate("dead_bug", "Dead Bug", "Lower opposite arm and leg while keeping the trunk controlled.", reps = 8, sets = 2, moveSeconds = 4f, restBetweenSets = 25),
                ExerciseTemplate("bridge_core", "Glute Bridge", "Lift, hold, and lower the hips under control.", reps = 10, sets = 3, moveSeconds = 2f, holdSeconds = 3, restBetweenSets = 25),
                ExerciseTemplate("bird_dog_core", "Bird Dog", "Extend opposite arm and leg while resisting trunk rotation.", reps = 8, sets = 2, moveSeconds = 2f, holdSeconds = 3, restBetweenSets = 25),
                ExerciseTemplate("plank", "Front Plank", "Hold a stable plank position with steady breathing.", mode = ExerciseMode.HOLD, sets = 2, holdSeconds = 20, restBetweenSets = 30, cues = listOf("Keep breathing", "Stop before form breaks"))
            )
        ),
        ProgramCategory(
            id = "general",
            title = "General Fitness",
            subtitle = "A simple whole-body starter routine",
            exercises = listOf(
                ExerciseTemplate("chair_squat", "Chair Squat", "Sit toward a chair and stand back up with control.", reps = 10, sets = 2, moveSeconds = 4f, restBetweenSets = 30),
                ExerciseTemplate("wall_pushup", "Wall Push-Up", "Perform a push-up against a wall with a straight body line.", reps = 10, sets = 2, moveSeconds = 4f, restBetweenSets = 25),
                ExerciseTemplate("march", "Standing March", "March in place at an easy controlled pace.", reps = 20, sets = 2, moveSeconds = 1f, restBetweenSets = 20),
                ExerciseTemplate("calf_raise_general", "Calf Raise", "Rise onto the toes and lower slowly.", reps = 12, sets = 2, moveSeconds = 3f, restBetweenSets = 20)
            )
        )
    )
}