package com.example.daily_quiz

import android.app.Application
import com.example.daily_quiz.data.local.QuizDatabase

class QuizApplication : Application() {
    val database by lazy { QuizDatabase.getDatabase(this) }
}