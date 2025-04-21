package com.example.myquizapp.data.api


import com.example.myquizapp.ui.home.models.QuestionsModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

/** Interface that defines methods for API communication. */
interface ApiHelper {
    /**
     * Fetches a list of questions.
     */
    suspend fun getQuizQuestions(): Response<QuestionsModel>
}

