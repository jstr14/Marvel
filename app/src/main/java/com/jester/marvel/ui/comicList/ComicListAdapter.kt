package com.jester.marvel.ui.comicList

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jester.marvel.R
import com.jester.marvel.ui.FormatComic
import com.jester.marvel.ui.load
import com.jester.marvel.ui.model.ComicViewEntity
import kotlinx.android.synthetic.main.awesome_item_comic.view.*
import org.jetbrains.anko.longToast
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Héctor on 23/11/2017.
 */
class ComicListAdapter @Inject constructor(val comicListPresenter: ComicListPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var comicList: List<ComicViewEntity> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { old, new ->
            old.id == new.id
        }
    }

    fun autoNotify(oldList: List<ComicViewEntity>, newList: List<ComicViewEntity>, compare: (ComicViewEntity, ComicViewEntity) -> Boolean) {

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

                return oldList[oldItemPosition] == newList[newItemPosition]

            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

                return compare(oldList[oldItemPosition], newList[newItemPosition])

            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size

        })

        diff.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return comicList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ComicViewHolder).bind(comicList[position], comicListPresenter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.awesome_item_comic, parent, false)
        return ComicViewHolder(view)
    }
}

class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(comicViewEntity: ComicViewEntity, presenter: ComicListPresenter) {

        val context = itemView.context

        val properPath = comicViewEntity.image?.path + "/" + context.getString(R.string.portrait) + "." + comicViewEntity.image?.extension
        itemView.comicCover.load(properPath)

        itemView.name.text = comicViewEntity.title
        setFormat(comicViewEntity.format)
        itemView.comicCover.setOnClickListener {
            context.longToast("imagen!")
        }

    }

    private fun setFormat(format: String) {
        itemView.format.text = format
        when (format) {
            FormatComic.COMIC.format -> {
                itemView.format.setBackgroundResource(FormatComic.COMIC.getColor())
                itemView.format.setTextColor(FormatComic.COMIC.getTextColor())
            }
            FormatComic.DIGEST.format -> {
                itemView.format.setBackgroundResource(FormatComic.DIGEST.getColor())
                itemView.format.setTextColor(FormatComic.DIGEST.getTextColor())

            }
            FormatComic.DIGITAL_COMIC.format -> {
                itemView.format.setBackgroundResource(FormatComic.DIGITAL_COMIC.getColor())
                itemView.format.setTextColor(FormatComic.DIGITAL_COMIC.getTextColor())

            }
            FormatComic.GRAPHIC_NOVEL.format -> {
                itemView.format.setBackgroundResource(FormatComic.GRAPHIC_NOVEL.getColor())
                itemView.format.setTextColor(FormatComic.GRAPHIC_NOVEL.getTextColor())

            }
            FormatComic.HARDCOVER.format -> {
                itemView.format.setBackgroundResource(FormatComic.HARDCOVER.getColor())
                itemView.format.setTextColor(FormatComic.HARDCOVER.getTextColor())

            }
            FormatComic.INFINITE_COMIC.format -> {
                itemView.format.setBackgroundResource(FormatComic.INFINITE_COMIC.getColor())
                itemView.format.setTextColor(FormatComic.INFINITE_COMIC.getTextColor())

            }
            FormatComic.MAGAZINE.format -> {
                itemView.format.setBackgroundResource(FormatComic.MAGAZINE.getColor())
                itemView.format.setTextColor(FormatComic.MAGAZINE.getTextColor())

            }
            FormatComic.TRADE_PAPERBACK.format -> {
                itemView.format.setBackgroundResource(FormatComic.TRADE_PAPERBACK.getColor())
                itemView.format.setTextColor(FormatComic.TRADE_PAPERBACK.getTextColor())

            }
            else -> itemView.format.visibility = View.GONE
        }

    }

}
