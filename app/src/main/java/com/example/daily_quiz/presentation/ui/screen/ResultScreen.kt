package com.example.daily_quiz.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel


@Composable
fun ResultScreen(
    viewModel: QuizViewModel,
    onRestartQuiz: () -> Unit
) {
    val score by remember { derivedStateOf { viewModel.calculateScore() } }
    val totalQuestions by remember { derivedStateOf { viewModel.totalQuestions } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Результаты",
//            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Вы ответили правильно на $score из $totalQuestions вопросов",
//            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Визуализация прогресса
//        CircularProgressIndicator(
//            progress = score.toFloat() / totalQuestions.toFloat(),
//            modifier = Modifier.size(120.dp),
//            strokeWidth = 8.dp,
//            color = if (score.toFloat() / totalQuestions.toFloat() > 0.7f) {
//                Color.Green
//            } else {
//                MaterialTheme.colors.primary
//            }
//        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.resetQuiz()
                onRestartQuiz()
            },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text(text = "Начать заново")
        }
    }
}