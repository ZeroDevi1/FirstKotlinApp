package com.zerodevi1.firstkotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.music_list_item.view.*

class MyAdapter(val data:ArrayList<MusicInfo>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // 获取当前行的数据
        val musicInfo = data[position]
        // 将数据设置到对应的控件中
        holder.itemView.textViewSinger.text = musicInfo.singer
        holder.itemView.textViewTitle.text = musicInfo.title
        holder.itemView.ratingBar.rating = musicInfo.like.toFloat()
    }

    override fun getItemCount(): Int = data.size
}