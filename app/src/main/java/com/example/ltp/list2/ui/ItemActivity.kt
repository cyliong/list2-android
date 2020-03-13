package com.example.ltp.list2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ltp.list2.R
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.activity_item.*

private const val EXTRA_ITEM_ID = "com.example.ltp.list2.EXTRA_ITEM_ID"

class ItemActivity : AppCompatActivity() {

    private val viewModel : ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val itemId = intent.getIntExtra(EXTRA_ITEM_ID, 0)
        if (itemId > 0) {
            viewModel.getItem(itemId).observe(this, Observer {
                viewModel.item = it
                edit_text_item_title.append(it.title)
            })
        } else {
            title = "New Item"
        }

        edit_text_item_title.requestFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        R.id.action_save -> {
            val title = edit_text_item_title.text?.toString()
            if (title.isNullOrBlank()) {
                Toast.makeText(
                    applicationContext,
                    "Please enter an item.",
                    Toast.LENGTH_SHORT
                ).show()
                false
            } else {
                val item = viewModel.item
                if (item == null) {
                    viewModel.insert(ListItem(title = title))
                } else {
                    item.title = title
                    viewModel.update(item)
                }
                finish()
                true
            }
        }
        else -> super.onOptionsItemSelected(menuItem)
    }

    companion object {

        fun newIntent(context: Context, itemId: Int? = null): Intent {
            return Intent(context, ItemActivity::class.java).apply {
                itemId?.let { putExtra(EXTRA_ITEM_ID, it) }
            }
        }

    }
}
