package com.peterstev.trivago.utilities

data class Resource<T>(val status: Status, val data: T?, val message: String) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, "success")
        }

        fun <T> error(message: String, data: T): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(Status.LOADING, data, "loading")
        }

        fun <T> idle(data: T): Resource<T> {
            return Resource(Status.IDLE, data, "")
        }
    }
}