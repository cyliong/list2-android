package com.example.ltp.list2.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ltp.list2.R
import com.example.ltp.list2.databinding.FragmentItemBinding
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.extension.reloadWidget
import com.example.ltp.list2.viewmodel.ItemViewModel

class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    private val args: ItemFragmentArgs by navArgs()
    private val viewModel : ItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemId = args.itemId
        if (itemId > 0) {
            if (savedInstanceState == null) {
                viewModel.getItem(itemId).observe(viewLifecycleOwner, {
                    it?.let {
                        binding.editTextItemTitle.append(it.title)
                    }
                })
            }
        } else {
            (activity as AppCompatActivity).supportActionBar?.title  = "New Item"
        }

        binding.editTextItemTitle.requestFocus()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_item, menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        R.id.action_save -> {
            val title = binding.editTextItemTitle.text?.toString()
            if (title.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
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
                reloadWidget()
                findNavController().navigateUp()
                true
            }
        }
        else -> super.onOptionsItemSelected(menuItem)
    }

}