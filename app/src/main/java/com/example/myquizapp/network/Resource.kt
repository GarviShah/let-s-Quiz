package com.example.myquizapp.network

/**
 * Resource is a generic data class that represents the state of a resource.
 * It includes a status, data of type T, and a message.
 * The status represents the current state of the resource (e.g., SUCCESS, ERROR, LOADING).
 * The data is the actual resource data, which can be of any type.
 * The message is used to provide additional information about the resource state.
 *
 * @property status The status of the resource.
 * @property data The actual resource data.
 * @property message Additional information about the resource state.
 * @constructor Creates a Resource instance with the provided status, data, and message.
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
