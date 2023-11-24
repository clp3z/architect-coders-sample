package com.devexperto.architectcoders.ui.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun View.bindVisibility(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String?) {
    url?.let {
        loadUrl(it)
    }
}
