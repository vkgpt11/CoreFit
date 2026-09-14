package com.example.simpleexercisecounter

import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Locale

enum class AppScreen { HOME, EXERCISES, REVIEW, RUNNER }
enum class RunPhase { READY, MOVE, HOLD, SET_REST, TRANSITION, COMPLETE }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent { MaterialTheme { CoreFitApp() } }
    }
}

@Composable
fun CoreFitApp() {
    var screen by remember { mutableStateOf(AppScreen.HOME) }
    var category by remember { mutableStateOf<ProgramCategory?>(null) }
    var selectedIds by remember { mutableStateOf(setOf<String>()) }

    Surface(Modifier.fillMaxSize()) {
        when (screen) {
            AppScreen.HOME -> CategoryScreen { chosen ->
                category = chosen
                selectedIds = chosen.exercises.map { it.id }.toSet()
                screen = AppScreen.EXERCISES
            }
            AppScreen.EXERCISES -> category?.let { chosen ->
                ExerciseSelectionScreen(
                    category = chosen,
                    selectedIds = selectedIds,
                    onToggle = { id ->
                        selectedIds = if (id in selectedIds) selectedIds - id else selectedIds + id
                    },
                    onBack = { screen = AppScreen.HOME },
                    onContinue = { if (selectedIds.isNotEmpty()) screen = AppScreen.REVIEW }
                )
            }
            AppScreen.REVIEW -> category?.let { chosen ->
                val routine = chosen.exercises.filter { it.id in selectedIds }
                RoutineReviewScreen(
                    category = chosen,
                    routine = routine,
                    onBack = { screen = AppScreen.EXERCISES },
                    onStart = { screen = AppScreen.RUNNER }
                )
            }
            AppScreen.RUNNER -> category?.let { chosen ->
                val routine = chosen.exercises.filter { it.id in selectedIds }
                RoutineRunner(
                    category = chosen,
                    routine = routine,
                    onExit = { screen = AppScreen.REVIEW },
                    onDone = { screen = AppScreen.HOME }
                )
            }
        }
    }
}

@Composable
private fun BrandHeader(subtitle: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.corefit_logo),
            contentDescription = "CoreFit logo",
            modifier = Modifier.size(58.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text("CoreFit", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun CategoryScreen(onSelect: (ProgramCategory) -> Unit) {
    Column(
        Modifier.fillMaxSize().safeDrawingPadding().padding(20.dp)
    ) {
        BrandHeader("Guided movement, one step at a time")
        Spacer(Modifier.height(14.dp))
        Card(Modifier.fillMaxWidth()) {
            Text(
                "Choose a focus area. CoreFit provides general exercise templates, not a diagnosis or medical treatment plan.",
                Modifier.padding(14.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(Modifier.height(14.dp))
        Column(
            Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProgramCatalog.categories.forEach { item ->
                Card(onClick = { onSelect(item) }, modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(item.title, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(3.dp))
                        Text(item.subtitle)
                        Spacer(Modifier.height(8.dp))
                        Text("${item.exercises.size} exercises", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseSelectionScreen(
    category: ProgramCategory,
    selectedIds: Set<String>,
    onToggle: (String) -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    Column(Modifier.fillMaxSize().safeDrawingPadding().padding(20.dp)) {
        TextButton(onClick = onBack) { Text("‹ Categories") }
        Text(category.title, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Pick the exercises you want in today's routine.")
        category.disclaimer?.let {
            Spacer(Modifier.height(10.dp))
            Card { Text(it, Modifier.padding(12.dp), style = MaterialTheme.typography.bodySmall) }
        }
        Spacer(Modifier.height(12.dp))
        Column(
            Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            category.exercises.forEach { exercise ->
                val selected = exercise.id in selectedIds
                Card(onClick = { onToggle(exercise.id) }, modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                        Checkbox(checked = selected, onCheckedChange = { onToggle(exercise.id) })
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(exercise.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(exercise.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(6.dp))
                            Text(exerciseSummary(exercise), style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onContinue,
            enabled = selectedIds.isNotEmpty(),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text("Review ${selectedIds.size} exercise${if (selectedIds.size == 1) "" else "s"}")
        }
    }
}

@Composable
fun RoutineReviewScreen(
    category: ProgramCategory,
    routine: List<ExerciseTemplate>,
    onBack: () -> Unit,
    onStart: () -> Unit
) {
    Column(Modifier.fillMaxSize().safeDrawingPadding().padding(20.dp)) {
        TextButton(onClick = onBack) { Text("‹ Edit exercises") }
        BrandHeader(category.title)
        Spacer(Modifier.height(14.dp))
        Text("Today's program", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("CoreFit will guide the exercises in this order and automatically manage sets, holds and rest.")
        Spacer(Modifier.height(12.dp))
        Column(
            Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            routine.forEachIndexed { index, exercise ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("${index + 1}", fontSize = 24.sp, fontWeight = FontWeight.Black)
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(exercise.name, fontWeight = FontWeight.Bold)
                            Text(exerciseSummary(exercise), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            Card(Modifier.fillMaxWidth()) {
                Text(
                    "Defaults are intentionally conservative and will become configurable in a later version. Stop any movement that clearly worsens symptoms.",
                    Modifier.padding(14.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(58.dp)) {
            Text("Start guided program", fontSize = 18.sp)
        }
    }
}

@Composable
fun RoutineRunner(
    category: ProgramCategory,
    routine: List<ExerciseTemplate>,
    onExit: () -> Unit,
    onDone: () -> Unit
) {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var ttsReady by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        lateinit var engine: TextToSpeech
        engine = TextToSpeech(context) { status ->
            ttsReady = status == TextToSpeech.SUCCESS
            if (ttsReady) engine.language = Locale.getDefault()
        }
        tts = engine
        onDispose { engine.stop(); engine.shutdown() }
    }

    var exerciseIndex by remember { mutableIntStateOf(0) }
    var currentSet by remember { mutableIntStateOf(1) }
    var currentRep by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(3) }
    var phase by remember { mutableStateOf(RunPhase.READY) }
    var paused by remember { mutableStateOf(false) }

    val exercise = routine.getOrNull(exerciseIndex)

    fun speak(text: String) {
        if (ttsReady) tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "corefit")
    }

    fun buzz() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    fun moveToNextExercise() {
        if (exerciseIndex >= routine.lastIndex) {
            phase = RunPhase.COMPLETE
            speak("Program complete")
            buzz()
        } else {
            exerciseIndex++
            currentSet = 1
            currentRep = 0
            seconds = routine[exerciseIndex].transitionSeconds
            phase = RunPhase.TRANSITION
            speak("Next exercise, ${routine[exerciseIndex].name}")
        }
    }

    LaunchedEffect(exerciseIndex, currentSet, currentRep, phase, paused) {
        val item = exercise ?: return@LaunchedEffect
        if (paused || phase == RunPhase.COMPLETE) return@LaunchedEffect

        when (phase) {
            RunPhase.READY -> {
                speak(item.name)
                for (i in 3 downTo 1) {
                    seconds = i
                    speak(i.toString())
                    delay(1000)
                    if (paused || phase != RunPhase.READY) return@LaunchedEffect
                }
                currentRep = 0
                phase = if (item.mode == ExerciseMode.HOLD) RunPhase.HOLD else RunPhase.MOVE
            }

            RunPhase.MOVE -> {
                delay((item.moveSeconds * 1000).toLong())
                if (paused || phase != RunPhase.MOVE) return@LaunchedEffect
                currentRep++
                buzz()
                speak(currentRep.toString())
                if (item.holdSeconds > 0) {
                    seconds = item.holdSeconds
                    phase = RunPhase.HOLD
                } else if (currentRep >= item.reps) {
                    if (currentSet >= item.sets) moveToNextExercise()
                    else {
                        currentSet++
                        seconds = item.restBetweenSets
                        phase = RunPhase.SET_REST
                        speak("Rest")
                    }
                }
            }

            RunPhase.HOLD -> {
                val holdFor = if (item.mode == ExerciseMode.HOLD) item.holdSeconds else item.holdSeconds
                for (i in holdFor downTo 1) {
                    seconds = i
                    if (i <= 3) speak(i.toString())
                    delay(1000)
                    if (paused || phase != RunPhase.HOLD) return@LaunchedEffect
                }
                buzz()
                if (item.mode == ExerciseMode.HOLD) {
                    if (currentSet >= item.sets) moveToNextExercise()
                    else {
                        currentSet++
                        seconds = item.restBetweenSets
                        phase = RunPhase.SET_REST
                        speak("Rest")
                    }
                } else if (currentRep >= item.reps) {
                    if (currentSet >= item.sets) moveToNextExercise()
                    else {
                        currentSet++
                        seconds = item.restBetweenSets
                        phase = RunPhase.SET_REST
                        speak("Rest")
                    }
                } else {
                    phase = RunPhase.MOVE
                }
            }

            RunPhase.SET_REST -> {
                for (i in item.restBetweenSets downTo 1) {
                    seconds = i
                    if (i <= 3) speak(i.toString())
                    delay(1000)
                    if (paused || phase != RunPhase.SET_REST) return@LaunchedEffect
                }
                currentRep = 0
                phase = if (item.mode == ExerciseMode.HOLD) RunPhase.HOLD else RunPhase.MOVE
                speak("Start")
            }

            RunPhase.TRANSITION -> {
                val wait = routine[exerciseIndex].transitionSeconds
                for (i in wait downTo 1) {
                    seconds = i
                    if (i == wait || i <= 3) speak(i.toString())
                    delay(1000)
                    if (paused || phase != RunPhase.TRANSITION) return@LaunchedEffect
                }
                seconds = 3
                phase = RunPhase.READY
            }

            RunPhase.COMPLETE -> Unit
        }
    }

    RunnerScreen(
        category = category,
        exercise = exercise,
        exerciseIndex = exerciseIndex,
        totalExercises = routine.size,
        phase = phase,
        currentSet = currentSet,
        currentRep = currentRep,
        seconds = seconds,
        paused = paused,
        onPause = { paused = !paused },
        onExit = onExit,
        onDone = onDone
    )
}

@Composable
private fun RunnerScreen(
    category: ProgramCategory,
    exercise: ExerciseTemplate?,
    exerciseIndex: Int,
    totalExercises: Int,
    phase: RunPhase,
    currentSet: Int,
    currentRep: Int,
    seconds: Int,
    paused: Boolean,
    onPause: () -> Unit,
    onExit: () -> Unit,
    onDone: () -> Unit
) {
    val item = exercise
    val phaseLabel = when {
        paused -> "PAUSED"
        phase == RunPhase.READY -> "GET READY"
        phase == RunPhase.MOVE -> "MOVE"
        phase == RunPhase.HOLD -> "HOLD"
        phase == RunPhase.SET_REST -> "REST"
        phase == RunPhase.TRANSITION -> "NEXT EXERCISE"
        else -> "COMPLETE"
    }
    val mainValue = when (phase) {
        RunPhase.MOVE -> currentRep.toString()
        RunPhase.READY, RunPhase.HOLD, RunPhase.SET_REST, RunPhase.TRANSITION -> seconds.toString()
        RunPhase.COMPLETE -> "✓"
    }

    Column(
        Modifier.fillMaxSize().safeDrawingPadding().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(R.drawable.corefit_logo), "CoreFit logo", Modifier.size(46.dp))
            Text(category.title, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(8.dp))
            if (phase != RunPhase.COMPLETE && item != null) {
                Text("${exerciseIndex + 1} of $totalExercises • Set $currentSet of ${item.sets}")
                Text(item.name, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            } else {
                Text("Program complete", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { if (totalExercises == 0) 0f else (exerciseIndex + if (phase == RunPhase.COMPLETE) 1 else 0).toFloat() / totalExercises },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(phaseLabel, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(mainValue, fontSize = 120.sp, fontWeight = FontWeight.Black)
            if (item != null && phase != RunPhase.COMPLETE) {
                Text(
                    when (phase) {
                        RunPhase.MOVE -> "of ${item.reps} reps"
                        RunPhase.HOLD -> "seconds"
                        RunPhase.SET_REST -> "seconds rest"
                        RunPhase.TRANSITION -> "seconds to prepare"
                        RunPhase.READY -> "seconds"
                        else -> ""
                    },
                    fontSize = 19.sp
                )
                item.cues.firstOrNull()?.let {
                    Spacer(Modifier.height(14.dp))
                    Text(it, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        if (phase == RunPhase.COMPLETE) {
            Column(Modifier.fillMaxWidth()) {
                Button(onDone, Modifier.fillMaxWidth().height(54.dp)) { Text("Done") }
                OutlinedButton(onExit, Modifier.fillMaxWidth()) { Text("Review program") }
            }
        } else {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onExit, Modifier.weight(1f)) { Text("Stop") }
                Button(onPause, Modifier.weight(1f)) { Text(if (paused) "Resume" else "Pause") }
            }
        }
    }
}

private fun exerciseSummary(exercise: ExerciseTemplate): String {
    return if (exercise.mode == ExerciseMode.HOLD) {
        "${exercise.sets} sets × ${exercise.holdSeconds}s hold • ${exercise.restBetweenSets}s rest"
    } else {
        val hold = if (exercise.holdSeconds > 0) " • ${exercise.holdSeconds}s hold" else ""
        "${exercise.sets} sets × ${exercise.reps} reps$hold • ${exercise.restBetweenSets}s rest"
    }
}