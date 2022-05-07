package com.androiddevs.mvvmpostsapp.repository

import com.androiddevs.mvvmpostsapp.api.RetrofitInstance
import com.androiddevs.mvvmpostsapp.models.CreatePostData
import com.androiddevs.mvvmpostsapp.models.delete.Delete
import retrofit2.Response


class PostsRepository(

) {
    suspend fun getPostData(page : Int) = RetrofitInstance.api.getPostData(page)

    suspend fun searchPostData(searchQuery: String,page: Int)= RetrofitInstance.api.searchPostData(searchQuery,page)

    suspend fun createPost(createPostData : CreatePostData)=
        RetrofitInstance.api.createPost(createPostData)

    suspend fun deletePost(postId: String): Response<Delete> {
        return RetrofitInstance.api.deletePost(postId)
    }




}