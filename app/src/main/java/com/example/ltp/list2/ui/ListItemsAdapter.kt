package com.example.ltp.list2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.list2.databinding.RecyclerViewItemBinding
import com.example.ltp.list2.db.ListItem

class ListItemsAdapter(private val context: Context) :
    RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {

    private var items = emptyList<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<ListItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int) = items[position]

    inner class ViewHolder(private val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val navController by lazy { itemView.findNavController() }

        private var item: ListItem? = null

        init {
            itemView.setOnClickListener {
                item?.let {
                    navController.navigate(
                        ListItemsFragmentDirections.actionListItemsFragmentToItemFragment(it.id)
                    )
                }
            }
        }

        fun bind(item: ListItem) {
            this.item = item
            binding.textViewListItem.text = item.title
        }

    }

}