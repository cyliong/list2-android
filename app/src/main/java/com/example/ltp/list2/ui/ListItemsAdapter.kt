package com.example.ltp.list2.ui

import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.list2.db.ListItem

class ListItemsAdapter : RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {

    private var items = emptyList<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ComposeView(parent.context))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.composeView.disposeComposition()
    }

    fun setItems(items: List<ListItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int) = items[position]

    inner class ViewHolder(val composeView: ComposeView) :
        RecyclerView.ViewHolder(composeView) {

        private val navController by lazy { itemView.findNavController() }

        private var item: ListItem? = null

        init {
            composeView.setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)

            itemView.setOnClickListener {
                item?.let {
                    navController.navigate(
                        ListItemsFragmentDirections
                            .actionListItemsFragmentToItemFragment(it.id, "Edit Item")
                    )
                }
            }
        }

        fun bind(item: ListItem) {
            this.item = item
            composeView.setContent {
                MaterialTheme {
                    Text(
                        text = item.title,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 10.dp)
                    )
                }
            }
        }

    }

}