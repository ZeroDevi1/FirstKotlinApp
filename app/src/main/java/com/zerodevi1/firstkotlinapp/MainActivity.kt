package com.zerodevi1.firstkotlinapp

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
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