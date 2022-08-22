package com.example.mvvmdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemo.databinding.RowBinding
import com.example.mvvmdemo.model.Post


class PostAdapter(private var postList: List<Post>)
    : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private lateinit var binding: RowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = RowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return PostViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        binding.tasks.text=postList[position].body
    }

    override fun getItemCount(): Int = postList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    fun setData(postList: List<Post>)
    {
        this.postList=postList
        notifyDataSetChanged()
    }

}
