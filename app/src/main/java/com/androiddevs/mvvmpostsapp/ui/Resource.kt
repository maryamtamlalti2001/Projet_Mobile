package com.androiddevs.mvvmpostsapp.ui

sealed class Resource<T> (
    val data2: T? = null,
    val message: String? = null
        ){
    class Success<T>(data2:T): Resource<T>(data2)
    class Error<T>(message: String,data2: T? = null): Resource<T>(data2, message)
    class Loading<T> : Resource<T>()
}