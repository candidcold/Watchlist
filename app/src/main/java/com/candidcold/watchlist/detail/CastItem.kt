package com.candidcold.watchlist.detail

import com.candidcold.watchlist.R
import com.candidcold.watchlist.extensions.loadCircularImage
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

        viewHolder.itemView.item_cast_image.loadCircularImage(path)
    }

}