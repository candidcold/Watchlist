package com.candidcold.watchlist.detail

import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.candidcold.watchlist.R
import com.candidcold.watchlist.network.NetworkCast
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_cast_member.view.*


class CastItem(val castMember: NetworkCast) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.item_cast_member

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.item_cast_character_name.text = castMember.character
        viewHolder.itemView.item_cast_real_name.text = castMember.name

        val context = viewHolder.itemView.item_cast_image.context
        val baseUrl = context.getString(R.string.tmdb_cast_member_base_url)
        val path: String = baseUrl + castMember.profile_path

        Glide.with(context)
                .load(path)
                .asBitmap()
                .centerCrop()
                .animate(android.R.anim.fade_in)
                .into(object : BitmapImageViewTarget(viewHolder.itemView.item_cast_image) {
                    override fun setResource(resource: Bitmap) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        viewHolder.itemView.item_cast_image.setImageDrawable(circularBitmapDrawable)
                    }
                })
    }

}