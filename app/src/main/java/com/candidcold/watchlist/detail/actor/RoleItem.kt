package com.candidcold.watchlist.detail.actor

import com.candidcold.watchlist.R
import com.candidcold.watchlist.extensions.loadFromUrl
import com.candidcold.watchlist.network.NetworkActorCastCredit
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_role.view.*


class RoleItem(private val type: String,
               val role: NetworkActorCastCredit) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.item_role

    override fun bind(viewHolder: ViewHolder, position: Int) {
        // TODO: Use enum for this instead
        when (type) {
            "tv" -> setupShowRole(viewHolder)
            "movie" -> setupMovieRole(viewHolder)
            else -> setupActor()
        }
    }

    private fun setupActor() {
        // Do nothing, find something better
    }

    private fun setupShowRole(viewHolder: ViewHolder) {
        viewHolder.itemView.item_role_content_title.text = role.name
        val character = "as ${role.character}"
        viewHolder.itemView.item_role_character_title.text = character
        val path = viewHolder.itemView.context.getString(R.string.tmdb_list_poster_base_url) +
                role.poster_path
        viewHolder.itemView.item_role_content_image.loadFromUrl(path)
    }

    private fun setupMovieRole(viewHolder: ViewHolder) {
        viewHolder.itemView.item_role_content_title.text = role.title
        val character = "as ${role.character}"
        viewHolder.itemView.item_role_character_title.text = character
        val path = viewHolder.itemView.context.getString(R.string.tmdb_list_poster_base_url) +
                role.poster_path
        viewHolder.itemView.item_role_content_image.loadFromUrl(path)
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return 1
    }
}