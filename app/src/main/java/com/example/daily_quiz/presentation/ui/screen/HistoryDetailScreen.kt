package com.example.daily_quiz.presentation.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.data.local.QuizQuestion
import com.example.daily_quiz.data.local.QuizResultWithQuestions
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    resultWithQuestions: QuizResultWithQuestions,
    onBack: () -> Unit
) {
    Column {
        TopAppBar(
            title = { Text("Прохождение от ${resultWithQuestions.result.date.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                }
            }
        )

        LazyColumn {
            items(resultWithQuestions.questions) { question ->
                QuestionResultItem(question)
            }
        }
    }
}

@Composable
fun QuestionResultItem(question: QuizQuestion) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(question.questionText, fontWeight = FontWeight.Bold)
            Text("Правильный ответ: ${question.correctAnswer}")
            Text("Ваш ответ: ${question.userAnswer}",
                color = if (question.isCorrect) Color.Green else Color.Red)
        }
    }
}