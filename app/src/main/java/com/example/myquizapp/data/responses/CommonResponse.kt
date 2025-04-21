package com.example.myquizapp.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
/**
 * CommonResponse is an open class that represents a common response from the API.
 * It includes fields for a message, status, and a token expiration flag.
 * This class is Parcelable, which means it can be passed between Android components.
 *
 * @property message The message returned by the API.
 * @property status The status of the API call, represented as a Boolean.
 * @property tokenExpired A flag indicating whether the token has expired.
 * @constructor Creates a CommonResponse instance with the provided message, status, and token expiration flag.
 */
open class CommonResponse(

    @field:SerializedName("message")
    open val message: String = "",

    @field:SerializedName("success")
    open val status: Boolean = false,

    @field:SerializedName("token_expired")
    val tokenExpired: Boolean = false
) : Parcelable
