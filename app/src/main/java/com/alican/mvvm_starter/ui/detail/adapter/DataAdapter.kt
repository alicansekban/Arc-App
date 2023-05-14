package com.alican.mvvm_starter.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.databinding.ItemAdapterBinding

class DataAdapter(val onClick: (ResponseModel) -> Unit) :
    ListAdapter<ResponseModel, DataAdapter.ViewHolder>(object :
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

    }), Filterable {

    var originalList = currentList.toList()

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

    override fun submitList(list: MutableList<ResponseModel>?) {
        submitList(list, false)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values =  if (p0.isNullOrBlank()) {
                        originalList
                    } else {
                        originalList.filter {
                            it.name?.contains(p0.toString(), ignoreCase = true) == true
                        }
                    }
                }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                submitList(p1?.values as? MutableList<ResponseModel>, true)
            }

        }
    }

    fun submitList(list: MutableList<ResponseModel>?, isFiltered : Boolean) {
        if (!isFiltered) originalList = list ?: listOf()
        super.submitList(list)
    }

}