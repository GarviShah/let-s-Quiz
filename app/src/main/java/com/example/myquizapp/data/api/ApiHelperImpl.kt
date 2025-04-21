package com.example.myquizapp.data.api


import com.example.myquizapp.ui.home.models.QuestionsModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of the ApiHelper interface.
 * This class is responsible for making API calls through the ApiService.
 * It uses dependency injection to receive an instance of ApiService.
 *
 * @property apiService The service used to make API calls.
 * @constructor Creates an ApiHelperImpl instance with the provided ApiService.
 */
class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getQuizQuestions(): Response<QuestionsModel> = apiService.getQuizQuestions()
}