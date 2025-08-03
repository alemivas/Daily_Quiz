package com.example.daily_quiz.presentation.ui.screen.QuizFlowScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun StartScreen(
    isLoading: Boolean,          // Передаётся из ViewModel
    isError: Boolean,           // Флаг ошибки
    onStartQuiz: () -> Unit     // Запуск викторины
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = "Добро пожаловать в DailyQuiz!",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (isError) {
                Text(
                    text = "Ошибка загрузки. Попробуйте ещё раз.",
//                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Button(
                onClick = onStartQuiz,
                enabled = !isLoading,  // Блокировка кнопки при загрузке
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp),
            ) {
                Text(text = "Начать викторину")
            }
        }
    }
}