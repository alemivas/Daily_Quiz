package com.example.daily_quiz.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 5,
        @Query("category") category: Int = 9,
        @Query("difficulty") difficulty: String = "easy",
        @Query("type") type: String = "multiple"
    ): QuestionResponse
}