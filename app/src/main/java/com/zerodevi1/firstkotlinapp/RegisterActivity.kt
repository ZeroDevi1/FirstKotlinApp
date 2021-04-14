package com.zerodevi1.firstkotlinapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(findViewById(R.id.toolbar))

        // 响应Cancel按钮的单击事件
        buttonCancel.setOnClickListener {
            // 关闭自己
            this.finish()
        }

        // 响应 Ok 按钮的单击事件
        buttonOK.setOnClickListener {
            // 获取控件中的数据
            val name = editTextName.text.toString()
            val password = editTextPassword.text.toString()
            val email = editTextEmail.text.toString()
            val phone = editTextPhone.text.toString()
            val address = editTextAddress.text.toString()
            // 性别,假设 true代表男 false代表女
            var sex = false

            val checkRadioId = radioGroup.checkedRadioButtonId
            // 如果选中的ID等于男则将sex改为true
            if(checkRadioId == R.id.radioMale){
                sex = true
            }

            // 注册
            // TODO : 与后台服务器进行交互

            // 创建 Intent 对象,保存要返回的数据,这里只需要返回 name 和 password
            val intent = Intent()
            intent.putExtra(MainActivity.KEY_NAME,name)
            intent.putExtra(MainActivity.KEY_PASSWORD,password)

            // 设置要返回的数据,第一个参数是SDK中定义的常量,表示本Activity正确执行
            // 第二个参数就是要返回的数据的Intent对象
            setResult(Activity.RESULT_OK,intent)

            // 关闭当前Activity
            finish()
        }
    }
}