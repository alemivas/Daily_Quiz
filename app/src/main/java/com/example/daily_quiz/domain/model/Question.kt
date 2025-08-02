package com.example.daily_quiz.domain.model

data class Question(
    val text: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val difficulty: String
)