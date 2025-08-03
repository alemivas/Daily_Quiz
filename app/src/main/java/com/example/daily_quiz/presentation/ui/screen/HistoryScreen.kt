package com.example.daily_quiz.presentation.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.data.local.QuizResult
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: QuizViewModel,
    onBack: () -> Unit
) {
    val results by viewModel.results.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar(
            title = { Text("История прохождений") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                }
            }
        )

        LazyColumn {
            items(results) { result ->
                ResultItem(result)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ResultItem(result: QuizResult) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Дата: ${result.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}",
//                style = MaterialTheme.typography.caption
            )
            Text(
                text = "Правильных ответов: ${result.correctAnswers}/${result.totalQuestions}",
//                style = MaterialTheme.typography.body1
            )
        }
    }
}