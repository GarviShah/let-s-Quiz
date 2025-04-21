package com.example.myquizapp.data.repository

import com.example.myquizapp.data.api.ApiHelper
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * CommonRepository is a class that acts as a bridge between the API and the rest of the application.
 * It uses the ApiHelper interface to make API calls and provides methods for various operations such as
 * checking if login is required, checking for the base URL, and saving device information.
 *
 * @property apiHelper The helper used to make API calls.
 * @constructor Creates a CommonRepository instance with the provided ApiHelper.
 */
class CommonRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getQuizQuestions() = apiHelper.getQuizQuestions()
}