package com.test.tix.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.tix.R
import com.test.tix.core.app.model.ItemUser
import com.test.tix.databinding.ItemUserBinding

class MainAdapter(private val listener: (ItemUser) -> Unit) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items = listOf<ItemUser>()

    fun set(items: List<ItemUser>) {
        this.items = items
        notifyDataSetChanged()
    }
    fun clearAll() {
        items = mutableListOf()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val itemBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: ItemUser) {
            itemBinding.apply {
                Glide.with(root.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions().dontTransform().placeholder(R.drawable.ic_launcher_background))
                    .fitCenter()
                    .centerCrop()
                    .into(ivItemPhoto)
                root.setOnClickListener {
                    listener(user)
                }
                tvItemCity.text = user.location
                tvItemEmail.text = user.company
                tvItemName.text = user.login
                tvItemUsername.text = user.type
            }
        }
    }
}