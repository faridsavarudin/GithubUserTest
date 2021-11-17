package com.test.tix.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.tix.core.app.model.ReposResponseItem
import com.test.tix.core.app.utils.DateUtils
import com.test.tix.databinding.ItemUserRepoBinding

class ItemRepoAdapter(private val context: Context) : RecyclerView.Adapter<ItemRepoAdapter.ViewHolder>() {

    private var items = listOf<ReposResponseItem>()

    fun set(items: List<ReposResponseItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemUserRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val itemBinding: ItemUserRepoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(repos: ReposResponseItem) {
            itemBinding.apply {
                Glide.with(root.context)
                    .load(repos.owner?.avatarUrl)
                    .fitCenter()
                    .centerCrop()
                    .into(ivItemPhoto)
                if (repos.description.isNullOrEmpty()) {
                    tvDesc.visibility = View.GONE
                } else {
                    tvDesc.visibility = View.VISIBLE
                    tvDesc.text = repos.description
                }
                tvRepoName.text = repos.name
                tvStarts.text = repos.stargazersCount.toString()
                tvDate.text = DateUtils.stringTimeAgo(repos.updatedAt, context)
            }
        }
    }
}