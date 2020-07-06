package com.example.ltp.list2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.list2.databinding.FragmentListItemsBinding
import com.example.ltp.list2.viewmodel.ListItemsViewModel

class ListItemsFragment : Fragment() {

    private var _binding: FragmentListItemsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListItemsViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)
        val listItemsAdapter = ListItemsAdapter()

        with(binding.recyclerView) {
            adapter = listItemsAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        }

        viewModel.items.observe(viewLifecycleOwner, Observer { items ->
            items?.let { listItemsAdapter.setItems(it) }
        })

        setSwipeToDeleteHandler(listItemsAdapter)

        binding.fab.setOnClickListener {
            navController.navigate(
                ListItemsFragmentDirections.actionListItemsFragmentToItemFragment()
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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
            .attachToRecyclerView(binding.recyclerView)
    }

}