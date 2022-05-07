package com.androiddevs.mvvmpostsapp.models

data class PostsResponse(
    val `data`: MutableList<Data>,
    val limit: Int,
    val page: Int,
    val total: Int
)