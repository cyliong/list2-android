package com.example.ltp.list2.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnLifecycleDestroyed
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ltp.list2.R
import com.example.ltp.list2.db.ListItem
import com.example.ltp.list2.extension.reloadWidget
import com.example.ltp.list2.viewmodel.ItemViewModel

class ItemFragment : Fragment() {

    private val args: ItemFragmentArgs by navArgs()
    private val viewModel: ItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                MaterialTheme {
                    ItemForm(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemId = args.itemId
        if (itemId > 0) {
            if (savedInstanceState == null) {
                viewModel.getItem(itemId).observe(viewLifecycleOwner, {
                    it?.let {
                        viewModel.currentItem = it
                        viewModel.titleFieldValue =
                            TextFieldValue(it.title, TextRange(it.title.length))
                        viewModel.isNew = false
                    }
                })
            }
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "New Item"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_item, menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        R.id.action_save -> {
            val item = viewModel.currentItem
            if (item.title.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Please enter an item.",
                    Toast.LENGTH_SHORT
                ).show()
                false
            } else {
                if (viewModel.isNew) {
                    viewModel.insert(item)
                } else {
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

@Composable
private fun ItemForm(viewModel: ItemViewModel) {
    ItemFormContent(
        item = viewModel.currentItem,
        onItemChange = viewModel::onItemChange,
        titleFieldValue = viewModel.titleFieldValue,
        onTitleFieldValueChange = viewModel::onTitleFieldValueChange
    )
}

@Composable
private fun ItemFormContent(
    item: ListItem,
    onItemChange: (ListItem) -> Unit,
    titleFieldValue: TextFieldValue,
    onTitleFieldValueChange: (TextFieldValue) -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        val focusRequester = remember { FocusRequester() }

        TextField(
            value = titleFieldValue,
            onValueChange = {
                onItemChange(item.copy(title = it.text))
                onTitleFieldValueChange(it)
            },
            label = { Text(stringResource(R.string.text_field_item_title_label)) },
            placeholder = { Text(stringResource(R.string.text_field_item_title_placeholder)) },
            singleLine = true,
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
        )

        LaunchedEffect(true) {
            focusRequester.requestFocus()
        }
    }
}

@Preview
@Composable
private fun ItemFormContentPreview() {
    val item = ListItem(title = "Item 1")
    ItemFormContent(item, {}, TextFieldValue(item.title, TextRange(item.title.length)), {})
}