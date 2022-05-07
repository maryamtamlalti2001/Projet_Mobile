package com.androiddevs.mvvmpostsapp.api

import com.androiddevs.mvvmpostsapp.models.CreatePostData
import com.androiddevs.mvvmpostsapp.models.Data
import com.androiddevs.mvvmpostsapp.models.PostsResponse
import com.androiddevs.mvvmpostsapp.models.delete.Delete
import retrofit2.Response
import retrofit2.http.*


interface PostsApi {

    //get a list of posts
    @Headers("app-id: 625acf84c8d8034ebfb479a3")
    @GET("post")
    suspend fun getPostData(
        @Query("page")
        page: Int = 0
    ): Response<PostsResponse>

    //search a post by tag
    @Headers("app-id: 625acf84c8d8034ebfb479a3")
    @GET("tag/{q}/post")
    suspend fun searchPostData(
        @Path("q")
        searchQuery: String,
        @Query("page")
        page: Int = 0
    ): Response<PostsResponse>




    // create a post
    @Headers("app-id: 625acf84c8d8034ebfb479a3")
    @POST("post/create")
    suspend fun createPost(@Body createPostData: CreatePostData): Response<Data>


    // delete a post
    @Headers("app-id: 625acf84c8d8034ebfb479a3")
    @DELETE("post/{post}")
    suspend fun deletePost(
        @Path("post") postId: String
    ): Response<Delete>





}