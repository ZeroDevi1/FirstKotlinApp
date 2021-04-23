package com.zerodevi1.firstkotlinapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_music_list.*

class MusicListFragment : Fragment() {

    private val data = ArrayList<MusicInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data.add(MusicInfo("徐良","今天见了前女友",2))
        data.add(MusicInfo("汪苏泷","后会无期",5))
        data.add(MusicInfo("周杰伦","我不配",4))
        data.add(MusicInfo("杨祖帅","灰色节拍",3))
        data.add(MusicInfo("许嵩","明智之举",4))
        data.add(MusicInfo("星弟","辞别",3))
        // 不调用这一句,Fragment的菜单显示不出来
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化 RecyclerView
        musicListView.layoutManager = LinearLayoutManager(context)
        musicListView.adapter = MyAdapter(data)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // 从资源中创建菜单
        inflater.inflate(R.menu.music_list_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 响应本Fragment中的菜单项的选择
        item.let {
            if(item.itemId == R.id.add_one_music_info){
                // 向列表中添加一项
                val musicInfo = MusicInfo("徐良","指环",5)
                data.add(musicInfo)
                // 利用 Adapter 通知 RecyclerView,刷新刚刚插入的一条数据数据
//                musicListView.adapter?.notifyDataSetChanged()
                musicListView.adapter?.notifyItemInserted(data.size-1)
                // 将 RecyclerView 定位到最后一行
                musicListView.scrollToPosition(data.size-1)
                // 返回 true 表示此菜单被响应
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}