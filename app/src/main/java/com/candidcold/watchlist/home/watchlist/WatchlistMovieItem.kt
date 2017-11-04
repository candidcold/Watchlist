package com.candidcold.watchlist.home.watchlist

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.candidcold.watchlist.R
import com.candidcold.watchlist.data.Movie
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_watchlist_movie.view.*


class WatchlistMovieItem(val movie: Movie) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.watchlist_content_title.text = movie.title

        val context = viewHolder.itemView.watchlist_content_image.context
        val posterBaseUrl = context.getString(R.string.tmdb_list_poster_base_url)
        val backdropBaseUrl = context.getString(R.string.tmdb_list_backdrop_base_url)
        val posterPath: String? = if (movie.posterPath == null) null else posterBaseUrl + movie.posterPath
        val backdropPath: String? = if (movie.backdropPath == null) null else backdropBaseUrl + movie.backdropPath
        setImage(context, posterPath, viewHolder.itemView.watchlist_content_image)
        setImage(context, backdropPath, viewHolder.itemView.watchlist_backdrop_image)
    }

    override fun getLayout() = R.layout.item_watchlist_movie

    private fun setImage(context: Context, path: String?, imageView: ImageView) {
        path?.let {
            Glide.with(context)
                    .load(path)
                    .crossFade()
                    .centerCrop()
                    .into(imageView)
        }
    }

}