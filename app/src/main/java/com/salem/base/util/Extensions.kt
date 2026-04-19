package com.salem.base.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.salem.base.databinding.LayoutToolbarBinding

fun Context.getLayoutInflater() =
    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun NavController.isFragmentInBackStack(destinationId: Int) =
    try {
        getBackStackEntry(destinationId)
        true
    } catch (e: Exception) {
        false
    }

fun NavController.safeNavigation(destinationId: Int) {
    if (isFragmentInBackStack(destinationId))
        popBackStack(destinationId, false)
    else
        navigate(destinationId)
}

fun LayoutToolbarBinding.setToolbar(
    @StringRes title: Int,
    back: Boolean = false
) {
    this.tvTitle.setText(title)
    this.ivBack.isInvisible = back == false
    this.ivBack.setOnClickListener {
        it.findNavController().popBackStack()
    }
}

fun EditText.fetchText(): String {
    return this.text.toString().trim()
}

fun TextView.fetchText(): String {
    return this.text.toString().trim()
}
