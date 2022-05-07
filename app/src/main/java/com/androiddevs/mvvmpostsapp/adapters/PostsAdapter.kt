package com.androiddevs.mvvmpostsapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmpostsapp.DeletePostActivity
import com.androiddevs.mvvmpostsapp.R
import com.androiddevs.mvvmpostsapp.models.Data
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_post_preview.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostViewHolder>(){



    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private val differCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.image== newItem.image
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post_preview,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(post.image).into(PostImage)
            Glide.with(this).load(post.owner?.picture).into(userphoto)
            description.text = post.text
            fullName.text =
                post.owner?.title + ". " + post.owner?.firstName + " " + post.owner?.lastName
            publishDate.text = post.publishDate
            tag1.text = post.tags?.get(0)
            tag2.text = post.tags?.get(1)
            tag3.text = post.tags?.get(2)

            setOnClickListener {
                onItemClickListener?.let { it(post) }

            }

            //un long clique pour supprimer un poste
            setOnLongClickListener{
                val postId = post.id
                val intent = Intent(context, DeletePostActivity::class.java)
                intent.putExtra("post_id", postId)
                context?.startActivity(intent)
                return@setOnLongClickListener true
            }}

         }
    private var onItemClickListener: ((Data) -> Unit)? = null
    fun  setOnItemClickListener(listener: (Data) -> Unit ){
        onItemClickListener = listener
    }



}