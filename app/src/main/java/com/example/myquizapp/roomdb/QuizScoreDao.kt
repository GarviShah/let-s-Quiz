package com.example.myquizapp.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: QuizScore)

    @Query("SELECT * FROM quiz_scores ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastScore(): QuizScore?
}
