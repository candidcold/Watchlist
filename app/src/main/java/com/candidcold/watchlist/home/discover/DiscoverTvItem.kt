package com.candidcold.watchlist.home.discover

import com.candidcold.watchlist.R
import com.candidcold.watchlist.data.TvShow
import com.candidcold.watchlist.extensions.loadImage
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_discover_tv_show.view.*


class DiscoverTvItem(val tvShow: TvShow) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val path = viewHolder.itemView.item_tv_poster.context.getString(R.string.tmdb_list_poster_base_url) + tvShow.posterPath
        viewHolder.itemView.item_tv_poster.loadImage(path)
    }

    override fun getLayout() = R.layout.item_discover_tv_show

}