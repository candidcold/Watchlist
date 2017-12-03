package com.candidcold.watchlist.search

import com.candidcold.watchlist.R
import com.candidcold.watchlist.extensions.loadCircularImage
import com.candidcold.watchlist.extensions.loadImage
import com.candidcold.watchlist.network.SearchResult
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_search_result.view.*


class SearchResultItem(val result: SearchResult) : Item<ViewHolder>(){

    override fun getLayout() = R.layout.item_search_result

    override fun bind(viewHolder: ViewHolder, position: Int) {
        when(result.media_type) {
            "tv" -> setupTvResult(viewHolder)
            "movie" -> setupMovieResult(viewHolder)
            "person" -> setupActorResult(viewHolder)
        }
    }

    private fun setupTvResult(viewHolder: ViewHolder) {
        viewHolder.itemView.item_search_result_name.text = result.name
        viewHolder.itemView.item_search_result_tag.text = "Tv Show"
        viewHolder.itemView.item_search_result_tag.setBackgroundColor(
                viewHolder.itemView.context.getColor(android.R.color.holo_green_light))
        val path = viewHolder.itemView.context.getString(R.string.tmdb_list_poster_base_url) +
                result.poster_path
        viewHolder.itemView.item_search_result_image.loadImage(path)
    }

    private fun setupMovieResult(viewHolder: ViewHolder) {
        viewHolder.itemView.item_search_result_name.text = result.title
        viewHolder.itemView.item_search_result_tag.text = "Movie"
        viewHolder.itemView.item_search_result_tag.setBackgroundColor(
                viewHolder.itemView.context.getColor(android.R.color.holo_blue_light))
        val path = viewHolder.itemView.context.getString(R.string.tmdb_list_poster_base_url) +
                result.poster_path
        viewHolder.itemView.item_search_result_image.loadImage(path)
    }

    private fun setupActorResult(viewHolder: ViewHolder) {
        viewHolder.itemView.item_search_result_name.text = result.name
        viewHolder.itemView.item_search_result_tag.text = "Actor"
        viewHolder.itemView.item_search_result_tag.setBackgroundColor(
                viewHolder.itemView.context.getColor(android.R.color.holo_red_light))
        val path = viewHolder.itemView.context.getString(R.string.tmdb_cast_member_base_url) +
                result.profile_path
        viewHolder.itemView.item_search_result_image.loadCircularImage(path)
    }
}