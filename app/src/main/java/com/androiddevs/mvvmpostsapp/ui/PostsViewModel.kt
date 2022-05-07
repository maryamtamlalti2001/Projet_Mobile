package com.androiddevs.mvvmpostsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmpostsapp.models.CreatePostData
import com.androiddevs.mvvmpostsapp.models.PostsResponse
import com.androiddevs.mvvmpostsapp.models.delete.Delete
import com.androiddevs.mvvmpostsapp.repository.PostsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class PostsViewModel(
val postsRepository: PostsRepository

) : ViewModel() {

    val allPosts: MutableLiveData<Resource<PostsResponse>> = MutableLiveData()
    var postsPage = 0

    var allPostsResponse : PostsResponse? = null

    val searchPosts: MutableLiveData<Resource<PostsResponse>> = MutableLiveData()
    var searchPostsPage = 0
    var searchPostsResponse : PostsResponse? = null


    val myDeletePostResponse: MutableLiveData<Response<Delete>> = MutableLiveData()


    fun getPostData() = viewModelScope.launch {
        allPosts.postValue(Resource.Loading())
        val response = postsRepository.getPostData(postsPage)
        allPosts.postValue(handlePostsResponse(response))
    }


    fun searchPostData(searchQuery: String) = viewModelScope.launch {
        searchPosts.postValue(Resource.Loading())
        val response = postsRepository.searchPostData(searchQuery,searchPostsPage)
        searchPosts.postValue(handleSearchPostsResponse(response))
    }

    private fun handlePostsResponse(response: Response<PostsResponse>) : Resource<PostsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                postsPage++
                if(allPostsResponse==null){
                    allPostsResponse = resultResponse
                }

                else{
                    val oldPosts = allPostsResponse?.data
                    val newPosts = resultResponse.data
                    oldPosts?.addAll(newPosts)


                }
                return Resource.Success(allPostsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }



    private fun handleSearchPostsResponse(response: Response<PostsResponse>) : Resource<PostsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchPostsPage++
                if(searchPostsResponse==null){
                    searchPostsResponse = resultResponse
                }

                else{
                    val oldPosts = searchPostsResponse?.data
                    val newPosts = resultResponse.data
                    oldPosts?.addAll(newPosts)


                }
                return Resource.Success(searchPostsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }




    // createPost
    fun createPost(createPostData: CreatePostData) = viewModelScope.launch {
        postsRepository.createPost(createPostData)
    }


    fun deletePost(postId: String) {
        viewModelScope.launch {
            val response = postsRepository.deletePost(postId)
            myDeletePostResponse.value = response
        }
    }










}