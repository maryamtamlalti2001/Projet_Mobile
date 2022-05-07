package com.androiddevs.mvvmpostsapp.models

data class CreatePostData(
    var owner: String,
    var image: String,
    var text: String,
    var likes: Int,
    var tags: List<String>,
)
