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

    // 伴生对象,可以静态调用
    companion object {
        val REGISTER_REQUEST_CODE = 123
        val KEY_NAME = "name"
        val KEY_PASSWORD = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 设置软键盘的模式,解决 ScrollView 的冲突
        // SOFT_INPUT_ADJUST_PAN : 软键盘适应ScrollView
        // SOFT_INPUT_STATE_HIDDEN : 软键盘在刚启动时不会弹出
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        // 在 ActionBar 上显示返回图标
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // 响应 "登录" 按钮的单击事件
        this.buttonLogin.setOnClickListener {
            // 这里编写响应事件的代码
            val snackbar = Snackbar.make(it, "你点我干啥?", Snackbar.LENGTH_LONG)
            // 显示提示
            snackbar.show()
        }

        // 启动注册页面
        this.buttonRegister.setOnClickListener {
            // 创建 Intent 对象
            val intent = Intent(this, RegisterActivity::class.java)
            // 启动 Intent
            startActivityForResult(intent, MainActivity.Companion.REGISTER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 说明是注册页面返回了
        if (requestCode == MainActivity.Companion.REGISTER_REQUEST_CODE) {
            // 说明在注册页面执行的逻辑成功了,从data中取出数据
            if (resultCode == Activity.RESULT_OK) {
                val name = data?.getStringExtra(MainActivity.Companion.KEY_NAME)
                val password = data?.getStringExtra(MainActivity.Companion.KEY_PASSWORD)
                // 用日志的方式输出
                Log.i("testLogin", "name=${name},password=${password}")
                // 将收到的用户名和密码设置到输入框
                editTextName.setText(name)
                editTextPassword.setText(password)
            }
        }
        // 调用父类的实现
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            // 提示用户 : 再点一次退出
            val snackbar = Snackbar.make(editTextName, "你再点我,我真要退出了!", Snackbar.LENGTH_LONG)
            // 显示提示
            snackbar.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}