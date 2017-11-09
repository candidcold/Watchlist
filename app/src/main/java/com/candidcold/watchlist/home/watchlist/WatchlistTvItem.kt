package com.candidcold.watchlist.home.watchlist

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.candidcold.watchlist.R
import com.candidcold.watchlist.data.TvShow
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_watchlist_content.view.*


class WatchlistTvItem(val tvShow: TvShow) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.watchlist_content_title.text = tvShow.name

        val context = viewHolder.itemView.watchlist_content_image.context
        val posterBaseUrl = context.getString(R.string.tmdb_list_poster_base_url)
        val backdropBaseUrl = context.getString(R.string.tmdb_list_backdrop_base_url)
        val posterPath: String? = if (tvShow.posterPath == null) null else posterBaseUrl + tvShow.posterPath
        val backdropPath: String? = if (tvShow.backdropPath == null) null else backdropBaseUrl + tvShow.backdropPath
        setImage(context, posterPath, viewHolder.itemView.watchlist_content_image)
        setImage(context, backdropPath, viewHolder.itemView.watchlist_backdrop_image)
    }

    override fun getLayout() = R.layout.item_watchlist_content

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