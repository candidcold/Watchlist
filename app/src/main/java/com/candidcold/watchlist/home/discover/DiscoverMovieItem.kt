package com.candidcold.watchlist.home.discover

import com.candidcold.watchlist.R
import com.candidcold.watchlist.data.Movie
import com.candidcold.watchlist.extensions.loadImage
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_discover_movie.view.*


class DiscoverMovieItem(val movie: Movie) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.content_image.context
        val baseUrl = context.getString(R.string.tmdb_list_poster_base_url)
        viewHolder.itemView.content_image.loadImage(baseUrl + movie.posterPath)
    }

    override fun getLayout() = R.layout.item_discover_movie
}