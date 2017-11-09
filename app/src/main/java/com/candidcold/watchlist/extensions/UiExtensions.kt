package com.candidcold.watchlist.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadFromUrl(path: String) {
    Glide.with(this.context)
            .load(path)
            .into(this)
}