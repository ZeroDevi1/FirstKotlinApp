package com.zerodevi1.firstkotlinapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 响应 "登录" 按钮的单击事件
        this.buttonLogin.setOnClickListener {
            // 这里编写响应事件的代码
            val snackbar = Snackbar.make(it, "你点我干啥?", Snackbar.LENGTH_LONG)
            // 显示提示
            snackbar.show()
        }

        // 启动注册页面
        this.buttonRegister.setOnClickListener {
            // 启动注册页面
            val fragment = RegisterFragment()
            // 当注册按钮调用的时候执行此方法
            val fragmentManager = requireActivity().supportFragmentManager
            // addToBackStack() 把这次操作放到后退栈,这样用户点击返回键就会执行反向操作,也可以使用 FragmentManager 执行反向操作
            // 参数是这次操作的名称,用于查找某个操作
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack("login").commit()
        }
    }

    // onViewCreated之后会执行onViewStateRestored恢复控件内容,所以在 onViewCreated中设置的值就会失效
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        // 将 userName 和 password 的值赋给相应的变量
        val mainActivity = requireActivity() as MainActivity
        mainActivity.userName?.let {
            editTextName.setText(it)
        }
        mainActivity.password?.let {
            editTextPassword.setText(it)
        }
    }

}