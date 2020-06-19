package com.android.videoplayer

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.item_list_video.view.*
import java.io.File


class MainRecyclerAdapter(
    var list: ArrayList<File>, var prefs: SharedPreferences
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_video, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as MainViewHolder
        viewHolder.onBind(list[position])
    }

    inner class MainViewHolder(var parent: View) : ViewHolder(parent) {
        var media_container: FrameLayout = parent.findViewById(R.id.media_container)
        var thumbnail: ImageView = parent.findViewById(R.id.thumbnail)
        var volumeControl: ImageView = parent.findViewById(R.id.volume_control)
        var progressBar: ProgressBar = parent.findViewById(R.id.progressBar)

        fun onBind(mediaObject: File) {
            parent.tag = this
            if (prefs.getStringSet("bookmarks", setOf())!!.contains(adapterPosition.toString()))
                itemView.ivBookmark.setImageResource(android.R.drawable.star_big_on)
            else
                itemView.ivBookmark.setImageResource(android.R.drawable.star_big_off)
        }

        init {

            itemView.ivBookmark.setOnClickListener {
                if (prefs.getStringSet("bookmarks", setOf())!!
                        .contains(adapterPosition.toString())
                ) {
                    itemView.ivBookmark.setImageResource(android.R.drawable.star_big_off)
                    val imSet = prefs.getStringSet("bookmarks", setOf())
                    val set = imSet?.toMutableSet()
                    set!!.remove(adapterPosition.toString())
                    prefs.edit().putStringSet("bookmarks", set).apply()
                } else {
                    itemView.ivBookmark.setImageResource(android.R.drawable.star_big_on)
                    val imSet = prefs.getStringSet("bookmarks", setOf())
                    val set = imSet?.toMutableSet()
                    set!!.add(adapterPosition.toString())
                    prefs.edit().putStringSet("bookmarks", set).apply()
                }
            }
        }

    }

    fun updateList(arrayList: ArrayList<File>) {
        list.clear()
        list.addAll(arrayList)
        notifyDataSetChanged()
    }

}