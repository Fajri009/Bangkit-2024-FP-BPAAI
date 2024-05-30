package com.example.bangkit_2024_fp_bpaai.adapter

import android.view.*
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.bangkit_2024_fp_bpaai.data.remote.response.ListStoryItem
import com.example.bangkit_2024_fp_bpaai.databinding.ItemListStoryBinding
import com.example.bangkit_2024_fp_bpaai.utils.withDateFormat

class StoryAdapter: ListAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    class ViewHolder(val binding: ItemListStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ListStoryItem) {
            Glide.with(itemView.context)
                .load(items.photoUrl)
                .into(binding.ivStory)
            binding.tvNameStory.text = items.name
            binding.tvDescStory.text = items.description
            binding.tvCreatedAtStory.text = items.createdAt!!.withDateFormat()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(items)
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: ListStoryItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}