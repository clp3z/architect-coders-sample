package com.devexperto.architectcoders.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devexperto.architectcoders.data.database.Movie

@BindingAdapter("items")
fun RecyclerView.bindItems(items: List<Movie>?) {
    items?.let {
        (adapter as? MoviesAdapter)?.submitList(it)
    }
}
