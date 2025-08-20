package com.example.shoesstore.model.util

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesstore.databinding.CustomSnackbarLayoutBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.Locale


fun Long.toFormattedPrice(): String {
    return NumberFormat.getNumberInstance(Locale.US).format(this)
}

fun View.showSnackbar(
    message: String,
    actionText: String?,
    action: ((View) -> Unit)?
): Snackbar {
    val snackbar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)

    val snackbarLayout = snackbar.view as ViewGroup
    snackbarLayout.setPadding(0, 0, 0, 0)

    val binding = CustomSnackbarLayoutBinding.inflate(LayoutInflater.from(context))

    binding.customSnackbarText.text = message

    if (actionText != null && action != null) {
        binding.customSnackbarAction.text = actionText
        binding.customSnackbarAction.setOnClickListener(action)
    } else {
        binding.customSnackbarAction.visibility = View.GONE
    }

    snackbarLayout.addView(binding.root, 0)

    snackbar.view.setBackgroundColor(Color.TRANSPARENT)

    snackbar.show()
    return snackbar
}