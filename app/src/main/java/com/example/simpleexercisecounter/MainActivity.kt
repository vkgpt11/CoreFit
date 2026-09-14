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
import kotlin.math.roundToInt

enum class Phase { SETUP, READY, EXERCISE, REST, COMPLETE }

data class WorkoutConfig(
    val reps: Int = 20,
    val secondsPerRep: Float = 2f,
    val sets: Int = 3,
    val restSeconds: Int = 20,
    val voice: Boolean = true,
    val vibration: Boolean = true
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent { MaterialTheme { ExerciseCounter() } }
    }
}

@Composable
fun ExerciseCounter() {
    val context = LocalContext.current
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var ttsReady by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val engine = TextToSpeech(context) { status -> ttsReady = status == TextToSpeech.SUCCESS }
        engine.language = Locale.getDefault()
        tts = engine
        onDispose { engine.stop(); engine.shutdown() }
    }

    var config by remember { mutableStateOf(WorkoutConfig()) }
    var phase by remember { mutableStateOf(Phase.SETUP) }
    var set by remember { mutableIntStateOf(1) }
    var rep by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(3) }
    var paused by remember { mutableStateOf(false) }

    fun speak(text: String) {
        if (config.voice && ttsReady) tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "counter")
    }
    fun buzz() {
        if (!config.vibration) return
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(VibrationEffect.createOneShot(70, VibrationEffect.DEFAULT_AMPLITUDE))
    }
    fun start() {
        set = 1; rep = 0; seconds = 3; paused = false; phase = Phase.READY; speak("Get ready")
    }
    fun reset() {
        set = 1; rep = 0; seconds = 3; paused = false; phase = Phase.SETUP; tts?.stop()
    }

    LaunchedEffect(phase, paused, set, config) {
        if (paused) return@LaunchedEffect
        when (phase) {
            Phase.READY -> {
                for (i in 3 downTo 1) {
                    seconds = i; speak(i.toString()); delay(1000)
                    if (paused || phase != Phase.READY) return@LaunchedEffect
                }
                rep = 0; phase = Phase.EXERCISE; speak("Start")
            }
            Phase.REST -> {
                for (i in config.restSeconds downTo 1) {
                    seconds = i; if (i <= 3) speak(i.toString()); delay(1000)
                    if (paused || phase != Phase.REST) return@LaunchedEffect
                }
                rep = 0; seconds = 3; phase = Phase.READY; speak("Get ready")
            }
            else -> Unit
        }
    }

    LaunchedEffect(phase, paused, set, config.reps, config.secondsPerRep) {
        if (phase != Phase.EXERCISE || paused) return@LaunchedEffect
        while (rep < config.reps) {
            delay((config.secondsPerRep * 1000).toLong())
            if (paused || phase != Phase.EXERCISE) return@LaunchedEffect
            rep++; buzz(); speak(rep.toString())
        }
        if (set >= config.sets) {
            phase = Phase.COMPLETE; speak("Workout complete"); buzz()
        } else {
            set++; seconds = config.restSeconds; phase = Phase.REST; speak("Rest")
        }
    }

    Surface(Modifier.fillMaxSize()) {
        if (phase == Phase.SETUP) {
            Setup(config, { config = it }, ::start)
        } else {
            Workout(phase, config, set, rep, seconds, paused, { paused = !paused }, ::reset, ::start)
        }
    }
}

@Composable
fun Setup(config: WorkoutConfig, update: (WorkoutConfig) -> Unit, start: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_repflow),
                contentDescription = "RepFlow logo",
                modifier = Modifier.size(52.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text("RepFlow", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text("Automatic reps, sets and rest")
            }
        }
        Setting("Repetitions", config.reps.toString()) {
            ChoiceRow(listOf(10, 15, 20, 25, 30), config.reps) { update(config.copy(reps = it)) }
        }
        Setting("Seconds per rep", "${config.secondsPerRep}s") {
            Slider(config.secondsPerRep, { update(config.copy(secondsPerRep = (it * 2).roundToInt() / 2f)) }, valueRange = 0.5f..3f, steps = 4)
        }
        Setting("Sets", config.sets.toString()) {
            ChoiceRow(listOf(1, 2, 3, 4, 5), config.sets) { update(config.copy(sets = it)) }
        }
        Setting("Rest", "${config.restSeconds}s") {
            ChoiceRow(listOf(10, 15, 20, 30, 45), config.restSeconds) { update(config.copy(restSeconds = it)) }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Voice counting"); Switch(config.voice, { update(config.copy(voice = it)) })
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Vibration cue"); Switch(config.vibration, { update(config.copy(vibration = it)) })
        }
        Spacer(Modifier.weight(1f))
        Button(start, Modifier.fillMaxWidth().height(58.dp)) { Text("Start workout", fontSize = 18.sp) }
    }
}

@Composable
fun Setting(title: String, value: String, content: @Composable () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(14.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, fontWeight = FontWeight.SemiBold); Text(value, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(8.dp)); content()
        }
    }
}

@Composable
fun ChoiceRow(options: List<Int>, selected: Int, choose: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        options.forEach { n ->
            FilterChip(selected = n == selected, onClick = { choose(n) }, label = { Text(n.toString()) })
        }
    }
}

@Composable
fun Workout(
    phase: Phase, config: WorkoutConfig, set: Int, rep: Int, seconds: Int, paused: Boolean,
    pause: () -> Unit, reset: () -> Unit, repeat: () -> Unit
) {
    val title = when (phase) {
        Phase.READY -> "GET READY"
        Phase.EXERCISE -> if (paused) "PAUSED" else "GO"
        Phase.REST -> if (paused) "PAUSED" else "REST"
        Phase.COMPLETE -> "COMPLETE"
        else -> ""
    }
    val value = when (phase) {
        Phase.READY, Phase.REST -> seconds.toString()
        Phase.EXERCISE -> rep.toString()
        Phase.COMPLETE -> "✓"
        else -> ""
    }
    Column(
        Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ic_repflow),
                contentDescription = "RepFlow logo",
                modifier = Modifier.size(42.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(if (phase == Phase.COMPLETE) "Workout finished" else "SET $set / ${config.sets}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(20.dp)); Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 130.sp, fontWeight = FontWeight.Black)
            Text(when (phase) {
                Phase.EXERCISE -> "of ${config.reps} reps"
                Phase.REST -> "seconds rest"
                Phase.READY -> "seconds"
                Phase.COMPLETE -> "${config.sets} sets × ${config.reps} reps"
                else -> ""
            }, fontSize = 20.sp)
        }
        if (phase == Phase.COMPLETE) {
            Column(Modifier.fillMaxWidth()) {
                Button(repeat, Modifier.fillMaxWidth()) { Text("Repeat workout") }
                OutlinedButton(reset, Modifier.fillMaxWidth()) { Text("Change settings") }
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedButton(reset) { Text("Stop") }
                Button(pause) { Text(if (paused) "Resume" else "Pause") }
            }
        }
    }
}
