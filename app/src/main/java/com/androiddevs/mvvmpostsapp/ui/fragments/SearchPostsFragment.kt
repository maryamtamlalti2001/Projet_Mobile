package com.androiddevs.mvvmpostsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmpostsapp.R
import com.androiddevs.mvvmpostsapp.adapters.PostsAdapter
import com.androiddevs.mvvmpostsapp.ui.PostsActivity
import com.androiddevs.mvvmpostsapp.ui.PostsViewModel
import com.androiddevs.mvvmpostsapp.ui.Resource
import com.androiddevs.mvvmpostsapp.util.Constants
import com.androiddevs.mvvmpostsapp.util.Constants.Companion.SEARCH_POSTS_TIME_DELAY
import kotlinx.android.synthetic.main.fragment_search_posts.*
import kotlinx.android.synthetic.main.fragment_search_posts.paginationProgressBar
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchPostsFragment : Fragment(R.layout.fragment_search_posts) {

    lateinit var viewModel: PostsViewModel
    lateinit var postsAdapter: PostsAdapter
    val TAG ="SearchPostsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as PostsActivity).viewModel
        setupRecyclerView()

        postsAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("post",it)
            }
            findNavController().navigate(
                R.id.action_searchPostsFragment_to_postFragment,
                bundle

            )
        }

        var job: Job? =null
        etSearch.addTextChangedListener { editable->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_POSTS_TIME_DELAY)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchPostData(editable.toString())
                    }
                }
            }
        }
        viewModel.searchPosts.observe(viewLifecycleOwner, Observer {  response ->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data2?.let{ postsResponse ->
                        postsAdapter.differ.submitList(postsResponse.data.toList())
                        val totalPages = postsResponse.total / Constants.QUERY_PAGE_SIZE +2
                        isLastPage = viewModel.searchPostsPage == totalPages
                        if(isLastPage){
                            rvAllPosts.setPadding(0,0,0,0)
                        }

                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let {
                            message-> Log.e(TAG,"An error occured: $message")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }

        })
    }
    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE

        isLoading = true
    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition>= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible
                    && isScrolling
            if (shouldPaginate){
                viewModel.searchPostData(etSearch.text.toString())
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView(){
        postsAdapter = PostsAdapter()
        rvSearchPosts.apply {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchPostsFragment.scrollListener)
        }

    }
}