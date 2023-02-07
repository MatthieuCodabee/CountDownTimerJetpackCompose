package com.example.mycountdowntimer

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycountdowntimer.ui.theme.MyCountDownTimerTheme
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCountDownTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TimerApp()
                }
            }
        }
    }
}

@Composable
fun TimerApp() {
    Scaffold(topBar = { AppBar()}, content = { Body(paddingValues = it)})
}

@Composable
fun AppBar() {
    TopAppBar(title = { Text(text = "Mon Timer")})
}

@Composable
fun Body(paddingValues: PaddingValues) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp

    var isPlaying by remember { mutableStateOf(false) }
    var duration: Long by remember { mutableStateOf(10000) }
    var timer: CountDownTimer? = null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colors.secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = (duration % 1000).toFloat() / 1000,
                modifier = Modifier
                    .height((width * 0.75).dp)
                    .width((width * 0.75).dp),
                strokeWidth = 10.dp
            )
            Text(text = "${duration.toDuration(DurationUnit.MILLISECONDS).inWholeSeconds}s",
                style = TextStyle(
                    fontSize = 45.sp,
                    color = MaterialTheme.colors.primary
                )
            )
        }
        Button(onClick = {
            if (isPlaying) {
                //Arrete
            } else {
                timer = object : CountDownTimer(10000, 10) {
                    override fun onTick(millisUntilFinished: Long) {
                        if (!isPlaying) timer?.cancel()
                        duration = millisUntilFinished
                    }
                    override fun onFinish() {
                        timer?.cancel()
                        isPlaying = !isPlaying
                        duration = 10000
                    }


                }.start()
            }
            isPlaying = !isPlaying
        }) {
            Text(if (isPlaying) "STOP" else "PLAY")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCountDownTimerTheme {
        TimerApp()
    }
}