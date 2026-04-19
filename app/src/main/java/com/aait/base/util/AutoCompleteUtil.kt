package com.aait.base.util

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.aait.base.R

object AutoCompleteUtil {

    fun setupAutoCompleteTextView(
        autoCompleteTextView: AutoCompleteTextView,
        data: List<String>,
        selectedValue: String?,
        itemView: Int = R.layout.item_list,
        onSelectValue: (String?,Int) -> Unit,
        onItemClickListener: AdapterView.OnItemClickListener? = null
    ) {
        val adapter = ArrayAdapter(autoCompleteTextView.context, itemView, data)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.onItemClickListener = onItemClickListener
        autoCompleteTextView.setText(selectedValue, false)

        // Assuming you want to set the selected value in the ViewModel
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            onSelectValue(data.getOrNull(position),position)
        }
    }

}
