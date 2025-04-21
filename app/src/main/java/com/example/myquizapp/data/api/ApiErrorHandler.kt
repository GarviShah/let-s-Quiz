package com.example.myquizapp.data.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.myquizapp.data.responses.CommonResponse
import okhttp3.ResponseBody

/** Singleton object that handles API error responses. */
object ApiErrorHandler {

    /**
 * This function is used to check the error code from a server response.
 * It takes a nullable ResponseBody object as an argument.
 * The function creates a TypeToken object of CommonResponse type and uses Gson to parse the response body into a CommonResponse object.
 * If the response body is null, a KotlinNullPointerException will be thrown.
 * The function returns the parsed CommonResponse object. If the parsed object is null, a KotlinNullPointerException will be thrown.
 *
 * @param response The server response body.
 * @return The parsed CommonResponse object.
 * @throws KotlinNullPointerException If the response body or the parsed CommonResponse object is null.
 */
fun checkErrorCode(
    response: ResponseBody?
) : CommonResponse {
    val type = object : TypeToken<CommonResponse>() {}.type
    val objResponse: CommonResponse? = Gson().fromJson(response!!.charStream(), type)

    return objResponse!!
}
}