package com.example.ltp.list2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.list2.R
import com.example.ltp.list2.db.ListItem
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class ListItemsAdapter(private val context: Context) :
    RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {

    private var items = emptyList<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var item: ListItem? = null

        init {
            itemView.setOnClickListener {
                item?.let {
                    context.startActivity(
                        ItemActivity.newIntent(context, it.id)
                    )
                }
            }
        }

        fun bind(item: ListItem) {
            this.item = item
            itemView.text_view_list_item.text = item.title
        }

    }

}