package com.candidcold.watchlist.home.discover

import com.bumptech.glide.Glide
import com.candidcold.watchlist.R
import com.candidcold.watchlist.data.Movie
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_discover_movie.view.*
import timber.log.Timber


class DiscoverMovieItem(val movie: Movie) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.content_image.context
        val baseUrl = context.getString(R.string.tmdb_list_poster_base_url)
        movie.posterPath?.let {
        Timber.tag("DiscoverItems").d("${movie.title} : poster is ${movie.posterPath}")
        Glide.with(context)
                .load(baseUrl + movie.posterPath)
                .crossFade()
                .into(viewHolder.itemView.content_image)
        }
    }

    override fun getLayout() = R.layout.item_discover_movie
}