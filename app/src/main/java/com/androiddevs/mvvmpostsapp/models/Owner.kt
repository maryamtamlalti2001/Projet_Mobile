package com.androiddevs.mvvmpostsapp.models

data class Owner(
    val firstName: String,
    val id: String,
    val lastName: String,
    val picture: String,
    val title: String,
    val fullName: String = "$firstName $lastName"
)