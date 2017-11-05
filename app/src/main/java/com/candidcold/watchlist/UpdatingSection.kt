package com.candidcold.watchlist

import com.xwray.groupie.Item
import com.xwray.groupie.Section
import com.xwray.groupie.UpdatingGroup
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_header.view.*


class UpdatingSection(header: String,
                      subtitle: String?) : Section() {
    private val updatingGroup =  UpdatingGroup()

    init {
        if (!header.isEmpty()) {
            setHeader(HeaderItem(header, subtitle))
        }
        add(updatingGroup)
    }

    fun update(items: List<Item<ViewHolder>>) {
        updatingGroup.update(items)
    }

}


class HeaderItem(private val header: String,
                 private val subtitle: String?) : Item<ViewHolder>() {


    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.item_header_title.text = header
        viewHolder.itemView.item_header_subtitle.text = subtitle
    }

    override fun getLayout() = R.layout.item_header

}