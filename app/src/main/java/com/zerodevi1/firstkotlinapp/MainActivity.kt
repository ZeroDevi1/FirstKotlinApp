package com.zerodevi1.firstkotlinapp

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

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
        supportFragmentManager.beginTransaction()
            // 设置 Fragment 间的转场动画
            .setCustomAnimations(
                R.anim.in_anim1,
                R.anim.out_anim1,
            )
            .add(R.id.fragment_container, loginFragment)
            .commit()

//        // 开启定时器
//        // 创建一个定时器
//        var timer = Timer()
//        // 创建一个TimerTask对象,要执行的代码就包在它里面
//        val timerTask = object:TimerTask(){
//            override fun run() {
//                // 定时器中要执行的代码
//                // 取得当前时间
//                val date = Date()
//                Log.i("timetest",date.toString())
//            }
//        }
//        // 启用这个定时器
//        timer.schedule(timerTask,1000,3000)
    }

    // 创建菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 从资源创建菜单,传入 menu 表示把创建出来的菜单放到 menu 中
        menuInflater.inflate(R.menu.main, menu)
        // 返回 ture 表示菜单显示,否则不显示
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // 点击了ActionBar上的返回图标
            if (supportFragmentManager.backStackEntryCount == 0) {
                // 如果后退栈空了,则说明返回了最初页面,显示退出提示对话框
                val dialogFragment = ExitDialogFragment()
                dialogFragment.show(supportFragmentManager, "exit")
            } else {
                // 从后退栈中弹出当前Fragment
                supportFragmentManager.popBackStack()
            }
            // 处理过的条目必须返回true
            return true
        } else if (item.itemId == R.id.action_settings) {
            // 选了设置项菜单,显示一条提示信息
            Snackbar.make(findViewById(R.id.fragment_container), "你选了设置", Snackbar.LENGTH_LONG)
                .show()
        } else if (item.itemId == R.id.action_subment_item) {
            // 选了子菜单下的子菜单项,显示一条提示消息
            Snackbar.make(findViewById(R.id.fragment_container), "你选了子菜单项", Snackbar.LENGTH_LONG)
                .show()
        }
        return super.onOptionsItemSelected(item)
    }

    // 响应返回键
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (supportFragmentManager.backStackEntryCount == 0) {
            // 如果后退栈空了,则说明返回了最初页面,显示退出提示对话框
            val dialogFragment = ExitDialogFragment()
            dialogFragment.show(supportFragmentManager, "exit")
            return true
        } else {
            // 执行默认的操作
            return super.onKeyDown(keyCode, event)
        }
    }


    class ExitDialogFragment() : DialogFragment() {
        // 重写父类的方法,在此方法中创建Dialog对象并返回
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(requireActivity())
            // 创建对话框之前,设置一些对话框的配置或数据
            // 设置对话框中显示的主内容
            builder.setMessage(R.string.exit_or_not)
                // 设置对话框中的正按钮以及按钮的响应方法,相当于OK,YES之类的按钮
                .setPositiveButton(
                    android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, which ->
                        // 退出当前 Activity
                        requireActivity().finish()
                    })
                // 设置对话框中的负按钮以及按钮的响应方法,相当于No,Cancel之类的按钮
                .setNegativeButton(
                    android.R.string.cancel,
                    DialogInterface.OnClickListener { dialog, which ->
                        // 用户点击了取消按钮,什么都不做
                    })
            // 创建对话框并返回它
            return builder.create()

        }
    }

}