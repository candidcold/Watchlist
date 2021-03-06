package com.candidcold.watchlist.detail.actor

import com.candidcold.watchlist.R
import com.candidcold.watchlist.extensions.loadCircularImage
import com.candidcold.watchlist.network.ActorResponse
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_actor_detail.view.*


class ActorDetailItem(val actor: ActorResponse) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.item_actor_bio.text = actor.biography.trimEnd()
        viewHolder.itemView.item_actor_name.text = actor.name
        val path = viewHolder.itemView.context.getString(R.string.tmdb_cast_member_base_url) + actor.profile_path
        viewHolder.itemView.item_actor_image.loadCircularImage(path)
    }

    override fun getLayout() = R.layout.item_actor_detail

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return 3
    }
}