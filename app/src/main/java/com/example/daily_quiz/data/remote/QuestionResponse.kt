package com.example.daily_quiz.data.remote

data class QuestionResponse(
    val response_code: Int,
    val results: List<QuestionDto>
)

data class QuestionDto(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)