package com.example.ltp.list2.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ltp.list2.R
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_item.*

class ItemFragment : Fragment() {

    private val args: ItemFragmentArgs by navArgs()
    private val viewModel : ItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemId = args.itemId
        if (itemId > 0) {
            if (savedInstanceState == null) {
                viewModel.loadItem(itemId)
                viewModel.item.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        edit_text_item_title.append(it.title)
                    }
                })
            }
        } else {
            (activity as AppCompatActivity).supportActionBar?.title  = "New Item"
        }

        edit_text_item_title.requestFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_item, menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        R.id.action_save -> {
            val title = edit_text_item_title.text?.toString()
            if (title.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Please enter an item.",
                    Toast.LENGTH_SHORT
                ).show()
                false
            } else {
                val item = viewModel.item.value
                if (item == null) {
                    viewModel.insert(ListItem(title = title))
                } else {
                    item.title = title
                    viewModel.update(item)
                }
                findNavController().navigateUp()
                true
            }
        }
        else -> super.onOptionsItemSelected(menuItem)
    }

}