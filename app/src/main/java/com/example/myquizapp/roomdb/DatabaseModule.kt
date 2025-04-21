package com.example.myquizapp.roomdb

import android.content.Context
import androidx.room.Room
import com.example.myquizapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.DB_NAME).build()

    @Provides
    fun provideQuizScoreDao(db: AppDatabase): QuizScoreDao = db.quizScoreDao()
}
