package com.example.daily_quiz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//@Database(entities = [QuizResult::class], version = 1)

@Database(
    entities = [QuizResult::class, QuizQuestion::class],
    version = 2, // Увеличиваем версию
    exportSchema = true
)

@TypeConverters(Converters::class) // Добавляем конвертеры
abstract class QuizDatabase : RoomDatabase() {
//    abstract fun resultDao(): QuizResultDao
    abstract fun historyDao(): QuizHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabase(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}