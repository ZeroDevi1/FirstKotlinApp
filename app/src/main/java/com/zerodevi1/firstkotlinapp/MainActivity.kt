package com.zerodevi1.firstkotlinapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            startActivityForResult(intent, REGISTER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 说明是注册页面返回了
        if (requestCode == REGISTER_REQUEST_CODE) {
            // 说明在注册页面执行的逻辑成功了,从data中取出数据
            if (resultCode == Activity.RESULT_OK) {
                val name = intent?.getStringExtra(KEY_NAME)
                val password = intent?.getStringExtra(KEY_PASSWORD)
                // 用日志的方式输出
                Log.i("testLogin", "name=${name},password=${password}")
            }
        }
        // 调用父类的实现
        super.onActivityResult(requestCode, resultCode, data)
    }
}