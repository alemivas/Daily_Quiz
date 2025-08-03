package com.example.daily_quiz.presentation.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.data.local.QuizQuestion
import com.example.daily_quiz.data.local.QuizResultWithQuestions
import com.example.daily_quiz.utils.cleanHtml

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    resultWithQuestions: QuizResultWithQuestions,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Результаты") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(resultWithQuestions.questions) { question ->
                QuestionWithAnswers(
                    question = question,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun QuestionWithAnswers(
    question: QuizQuestion,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Текст вопроса
            Text(
                text = question.questionText.cleanHtml(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Все варианты ответов
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                question.allAnswers.forEach { answer ->
                    AnswerItem(
                        answer = answer,
                        isCorrect = answer == question.correctAnswer,
                        isSelected = answer == question.userAnswer
                    )
                }
            }

            // Статус ответа
            Text(
                text = if (question.isCorrect) "✓ Вы ответили правильно"
                else "✗ Вы ошиблись",
                color = if (question.isCorrect) Color(0xFF4CAF50)
                else Color(0xFFF44336),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun AnswerItem(
    answer: String,
    isCorrect: Boolean,
    isSelected: Boolean
) {
    val backgroundColor = when {
        isSelected && isCorrect -> Color(0xFFE8F5E9).copy(alpha = 0.8f)
        isSelected -> Color(0xFFFFEBEE).copy(alpha = 0.8f)
        isCorrect -> Color(0xFFE8F5E9).copy(alpha = 0.4f)
        else -> Color.White
    }

    val borderColor = when {
        isSelected && isCorrect -> Color(0xFF4CAF50)
        isSelected -> Color(0xFFF44336)
        isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.5f)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Иконка статуса
        Icon(
            imageVector = when {
                isSelected && isCorrect -> Icons.Default.CheckCircle
                isSelected -> Icons.Default.CheckCircle
                isCorrect -> Icons.Default.Check
                else -> Icons.Default.Close
            },
            contentDescription = null,
            tint = when {
                isSelected && isCorrect -> Color(0xFF4CAF50)
                isSelected -> Color(0xFFF44336)
                isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.5f)
                else -> Color.Black
            },
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Текст ответа
        Text(
            text = answer.cleanHtml(),
            modifier = Modifier.weight(1f)
        )
    }
}