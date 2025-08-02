package com.example.daily_quiz.presentation.ui.screen.main_quiz_screen_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.daily_quiz.presentation.viewmodel.QuizViewModel
import com.example.daily_quiz.utils.cleanHtml

@Composable
fun MainQuizScreenQuiz(
    viewModel: QuizViewModel,
//    onQuizComplete: () -> Unit
) {
//    val currentQuestion by remember { derivedStateOf { viewModel.currentQuestion } }
    val currentQuestion by remember { derivedStateOf { viewModel.currentQuestion } }

//    if (viewModel.isQuizCompleted) {
//        onQuizComplete()
//        return
//    }

    currentQuestion?.let { question ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Шапка с номером вопроса
            Text(
                text = "Вопрос ${viewModel.currentQuestionNumber} из ${viewModel.totalQuestions}",
//                style = MaterialTheme.typography.h6
            )

            // Текст вопроса
            Text(
//                text = Html.fromHtml(question.text).toString(),
                text = question.text.cleanHtml(),
                modifier = Modifier.padding(vertical = 24.dp),
//                style = MaterialTheme.typography.body1
            )

            // Варианты ответов
            val answers = remember(question) {
                (question.incorrectAnswers + question.correctAnswer).shuffled()
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                answers.forEach { answer ->
                    RadioButtonItem(
//                        text = Html.fromHtml(answer).toString(),
                        text = answer.cleanHtml(),
                        isSelected = answer == viewModel.selectedAnswer,
                        onSelect = { viewModel.selectAnswer(answer) }
                    )
                }
            }

            // Подпись и кнопка
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Вернуться к предыдущим вопросам нельзя",
//                    style = MaterialTheme.typography.caption
                )

                Button(
                    onClick = { viewModel.moveToNextQuestion() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = viewModel.selectedAnswer != null
                ) {
                    Text(
                        text =
                            if (viewModel.currentQuestionNumber < viewModel.totalQuestions)
                                "Далее"
                            else
                                "Завершить"
                    )
                }
            }
        }
    }
}

@Composable
private fun RadioButtonItem(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(8.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}