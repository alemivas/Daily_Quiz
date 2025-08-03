package com.example.daily_quiz.data.local

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizHistoryDao {
    @Transaction
    @Query("SELECT * FROM quiz_results ORDER BY date DESC")
    fun getAllResultsWithQuestions(): Flow<List<QuizResultWithQuestions>>

    @Transaction
    @Query("SELECT * FROM quiz_results WHERE id = :resultId")
    suspend fun getResultById(resultId: Int): QuizResultWithQuestions?

    @Insert
    suspend fun insertResult(result: QuizResult): Long

    @Insert
    suspend fun insertQuestions(questions: List<QuizQuestion>)
}

data class QuizResultWithQuestions(
    @Embedded val result: QuizResult,
    @Relation(
        parentColumn = "id",
        entityColumn = "resultId"
    )
    val questions: List<QuizQuestion>
)