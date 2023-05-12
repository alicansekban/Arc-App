package com.alican.mvvm_starter.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.databinding.ItemAdapterBinding

class ItemsAdapter(val onClick: (ResponseModel) -> Unit) :
    ListAdapter<ResponseModel, ItemsAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<ResponseModel>() {
        override fun areItemsTheSame(oldItem: ResponseModel, newItem: ResponseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ResponseModel,
            newItem: ResponseModel
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    private var unFilteredList = listOf<ResponseModel>()

    class ViewHolder(val binding: ItemAdapterBinding, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding) {
            onClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.executePendingBindings()
    }

    fun modifyList(list: List<ResponseModel>) {
        unFilteredList = list
        submitList(list)
    }

    fun filter(query: String?) {
        val list = mutableListOf<ResponseModel>()
        if (!query.isNullOrEmpty()) {
            list.addAll(unFilteredList.filter {
                it.name?.contains(query.toString(), ignoreCase = true) == true
            })
            submitList(list)
        } else {
            submitList(unFilteredList)
        }

    }

}