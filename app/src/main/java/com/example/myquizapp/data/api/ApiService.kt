package com.example.myquizapp.data.api

import com.example.myquizapp.utils.ApiEndpoints.GET_QUESTIONS
import com.example.myquizapp.ui.home.models.QuestionsModel
import retrofit2.Response
import retrofit2.http.GET

/** Interface defining the API endpoints for the application. */
interface ApiService {

    /**
     * Fetches a list of quiz questions from the server.
     */
    @GET(GET_QUESTIONS)
    suspend fun getQuizQuestions(): Response<QuestionsModel>
}