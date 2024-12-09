package com.example.simpletapgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpletapgame.ui.theme.SimpleTapGameTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleTapGameTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SimpleTapGame()
                }
            }
        }
    }
}

@Composable
fun SimpleTapGame() {
    // 게임 상태 정의
    var score by remember { mutableStateOf(0) }
    var remainingTime by remember { mutableStateOf(10) }
    var isGameOver by remember { mutableStateOf(false) }

    // 타이머 실행: 컴포저블이 재조합될 때마다 새로 시작되지 않도록 remember와 LaunchedEffect 사용
    LaunchedEffect(Unit) {
        while (remainingTime > 0) {
            delay(1000L)
            remainingTime--
        }
        // 시간 종료 시 게임 종료 처리
        isGameOver = true
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!isGameOver) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "남은 시간: $remainingTime 초")
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "점수: $score")
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { score++ },
                    enabled = !isGameOver
                ) {
                    Text(text = "탭!")
                }
            }
        } else {
            // 게임 종료 시 최종 점수 표시
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "게임 종료!")
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "최종 점수: $score")
            }
        }
    }
}
