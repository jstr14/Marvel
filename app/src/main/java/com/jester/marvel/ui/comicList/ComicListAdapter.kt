package com.jester.marvel.ui.comicList

import android.support.annotation.NonNull
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jester.marvel.R
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
        disableParentsClip(itemView.imageView)
        itemView.imageView.load(properPath)

//        itemView.comicName.text = comicViewEntity.title
        itemView.imageView.setOnClickListener {
            context.longToast("imagen!")
        }

    }

    fun disableParentsClip(@NonNull view: View) {
        var view = view
        while (view.parent != null && view.parent is ViewGroup) {
            val viewGroup = view.parent as ViewGroup
            viewGroup.clipChildren = false
            viewGroup.clipToPadding = false
            view = viewGroup
        }
    }

}
