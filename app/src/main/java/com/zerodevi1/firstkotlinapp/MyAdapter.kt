package com.zerodevi1.firstkotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.music_list_advertising_list.view.*
import kotlinx.android.synthetic.main.music_list_item.view.*

class MyAdapter(val data: ArrayList<Any>) : RecyclerView.Adapter<MyAdapter.BaseViewHolder>() {

    inner open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class MyViewHolder(itemView: View) : BaseViewHolder(itemView) {
        init {
            // 让条目的根View响应单击事件以实现选择一行的效果
            itemView.setOnClickListener {
                // adapterPosition 获取当前条目对应 data 的序号
                val musicInfo = data[adapterPosition] as MusicInfo
                Snackbar.make(
                    it,
                    "你选择了第${adapterPosition + 1}行,歌名是:${musicInfo.title}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    inner class AdvertisingViewHolder(itemView: View) : BaseViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return if (viewType == R.layout.music_list_item) MyViewHolder(view) else AdvertisingViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // 获取当前行的数据
        val item = data[position]
        if (item is MusicInfo) {
            // 将数据设置到对应的控件中
            holder.itemView.textViewSinger.text = item.singer
            holder.itemView.textViewTitle.text = item.title
            holder.itemView.ratingBar.rating = item.like.toFloat()
        } else if (item is Advertising) {
            holder.itemView.textViewAdvertiser.text = item.advertiser
            holder.itemView.textViewContent.text = item.content
        }
    }

    override fun getItemCount(): Int = data.size

    /**
     * 区分不同的ViewType
     */
    override fun getItemViewType(position: Int): Int {
        // 根据参数position返回每行对应的viewType的值
        // 为了方便直接将layout的ID作为ViewType的值
        return if (data[position] is MusicInfo) {
            R.layout.music_list_item
        } else {
            R.layout.music_list_advertising_list
        }
    }
}