package com.example.myquizapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_scores")
data class QuizScore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val score: Int,
    val total: Int,
    val timestamp: Long = System.currentTimeMillis()
)
