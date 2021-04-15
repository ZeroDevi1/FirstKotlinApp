package com.zerodevi1.firstkotlinapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 响应取消按钮的单击事件
        buttonCancel.setOnClickListener {
            // 从栈中弹出当前Fragment
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 响应OK按钮的单击事件
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
            if (checkRadioId == R.id.radioMale) {
                sex = true
            }

            // 注册
            // TODO : 与后台服务器进行交互

            // 注册完成后,将用户名和密码保存到Activity
            val mainActivity = requireActivity() as MainActivity
            mainActivity.userName = name
            mainActivity.password = password
            // 回到上一个页面
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}