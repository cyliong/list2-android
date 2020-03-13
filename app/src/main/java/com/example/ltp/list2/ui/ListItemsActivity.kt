package com.example.ltp.list2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.list2.R
import com.example.ltp.list2.viewmodel.ListItemsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel : ListItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(this)
        val listItemsAdapter = ListItemsAdapter(this)

        with(recycler_view) {
            adapter = listItemsAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        }

        viewModel.items.observe(this, Observer { items ->
            items?.let { listItemsAdapter.setItems(it) }
        })

        setSwipeToDeleteHandler(listItemsAdapter)

        fab.setOnClickListener {
            startActivity(ItemActivity.newIntent(this))
        }
    }

    private fun setSwipeToDeleteHandler(listItemsAdapter: ListItemsAdapter) {
        val swipeToDeleteHandler = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = listItemsAdapter.getItemAtPosition(viewHolder.adapterPosition)
                viewModel.delete(item)
            }
        }

        ItemTouchHelper(swipeToDeleteHandler)
            .attachToRecyclerView(recycler_view)
    }

}
