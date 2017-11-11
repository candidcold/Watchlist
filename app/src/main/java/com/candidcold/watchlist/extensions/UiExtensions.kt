package com.candidcold.watchlist.extensions

import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget


fun ImageView.loadFromUrl(path: String) {
    Glide.with(this.context)
            .load(path)
            .crossFade()
            .into(this)
}

fun ImageView.loadFromUrlCircular(path: String) {
    Glide.with(this.context)
            .load(path)
            .asBitmap()
            .centerCrop()
            .animate(android.R.anim.fade_in)
            .into(object : BitmapImageViewTarget(this) {
                override fun setResource(resource: Bitmap) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    this@loadFromUrlCircular.setImageDrawable(circularBitmapDrawable)
                }
            })
}