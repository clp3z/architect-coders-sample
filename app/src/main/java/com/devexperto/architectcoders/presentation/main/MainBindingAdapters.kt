package com.devexperto.architectcoders.presentation.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("items")
fun RecyclerView.bindItems(items: List<com.devexperto.architectcoders.domain.Movie>?) {
    items?.let {
        (adapter as? MoviesAdapter)?.submitList(it)
    }
}
