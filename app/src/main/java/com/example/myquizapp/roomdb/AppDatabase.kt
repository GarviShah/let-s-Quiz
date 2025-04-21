package com.example.myquizapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [QuizScore::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizScoreDao(): QuizScoreDao
}