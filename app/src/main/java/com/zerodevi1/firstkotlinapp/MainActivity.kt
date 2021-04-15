package com.zerodevi1.firstkotlinapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var userName: String? = null
    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 设置软键盘的模式,解决 ScrollView 的冲突
        // SOFT_INPUT_ADJUST_PAN : 软键盘适应ScrollView
        // SOFT_INPUT_STATE_HIDDEN : 软键盘在刚启动时不会弹出
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        // 在 ActionBar 上显示返回图标
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 创建Fragment对象
        val loginFragment = LoginFragment()

        // 将LoginFragment加入到Activity
        // 获取 Fragment 事务
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, loginFragment)
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // 单击了ActionBar上的返回图标,从栈中弹出当前Fragment
            supportFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}