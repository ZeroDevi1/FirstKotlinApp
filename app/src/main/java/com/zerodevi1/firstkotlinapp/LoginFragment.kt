package com.zerodevi1.firstkotlinapp

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.icu.number.Scale
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
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
            // 测试动画效果
            // 创建一个旋转动画(动哪里?动角度)
//            val animation = RotateAnimation(0.0f, 360f)
//            val animation = RotateAnimation(
//                0.0f,
//                180f,
//                Animation.RELATIVE_TO_SELF, // pivotXValue 的类型 : Animation.RELATIVE_TO_SELF 这个轴心X坐标是相对于图像自己
//                0.5f,   // 旋转轴心在 X坐标 上的位置 0-最左边 1-最右边 0.5-中心
//                Animation.RELATIVE_TO_SELF, //
//                0.5f
//            )
            // 沿同一方向旋转
//            val animation = RotateAnimation(
//                0.0f,
//                360f,
//                Animation.RELATIVE_TO_SELF, // pivotXValue 的类型 : Animation.RELATIVE_TO_SELF 这个轴心X坐标是相对于图像自己
//                0.5f,   // 旋转轴心在 X坐标 上的位置 0-最左边 1-最右边 0.5-中心
//                Animation.RELATIVE_TO_SELF, //
//                0.5f
//            )
            // 移动位置的动画
//            val animation = TranslateAnimation(0F, 100F, 0F, 0F)
            // 缩放的动画
//            val animation = ScaleAnimation(1f,1.5f,1f,1.5f,0.5f,0.5f)
            // 改变透明度的动画
//            val animation = AlphaAnimation(0.5f, 1f)
//            // 设置重复模式,REVERSE的意思是动完一次后接着反向动(如何重复)
//            animation.repeatMode = Animation.REVERSE
//            // 旋转速度设置为匀速 : LinearInterpolator 线性插值
//            animation.interpolator = LinearInterpolator()
//            // 设置持续时间,1000毫秒(动多长时间)
//            animation.duration = 1000
//            // 设置重复次数
//            animation.repeatCount = 10
//            // 启动动画(动谁?动imageView)
//            imageViewHead.startAnimation(animation)
//            testAnimationSet()
//            testAnimator()
            testAnimatorSet()
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

    /**
     * 设置动画组
     */
    private fun testAnimationSet() {
        // 创建一个旋转动画
        val animation = RotateAnimation(
            0.0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        // 设置重复模式
        animation.repeatMode = Animation.REVERSE
        // 设置持续时间,1000毫秒
        animation.duration = 1000
        // 设置重复次数
        animation.repeatCount = 10
        // 设置为匀速动画
        animation.interpolator = LinearInterpolator()

        // 创建一个缩放动画,在 X轴 和 Y轴 上都是从0.5到1.5
        val scaleAnimation = ScaleAnimation(0.5f, 1.5f, 0.5f, 1.5f)
        scaleAnimation.repeatMode = Animation.REVERSE
        animation.duration = 2000
        // 设置动画重复次数为永不停止
        scaleAnimation.repeatCount = Animation.INFINITE

        // 创建动画对象,参数表示是否所有动画共享同一个插值参数
        val animationSet = AnimationSet(false)
        animationSet.addAnimation(animation)
        animationSet.addAnimation(scaleAnimation)

        // 启动动画
        imageViewHead.startAnimation(animationSet)
    }

    private fun testAnimator() {
        // 创建一个旋转动画
        // 1) : imageViewHead 要动的控件
        // 2) : rotation 要动的属性
        val rotateAnimator = ObjectAnimator.ofFloat(imageViewHead, "rotation", 0f, 180f)
        rotateAnimator.duration = 1000
        rotateAnimator.repeatCount = 10
        rotateAnimator.repeatMode = ValueAnimator.REVERSE
        rotateAnimator.interpolator = LinearInterpolator()
        // 启动动画
        rotateAnimator.start()
    }

    private fun testAnimatorSet() {
        // 创建一个旋转动画
        val rotateAnimator = ObjectAnimator.ofFloat(imageViewHead, "rotation", 0f, 180f)
        rotateAnimator.duration = 1000
        rotateAnimator.repeatCount = 2
        rotateAnimator.interpolator = LinearInterpolator()
        rotateAnimator.repeatMode = ValueAnimator.REVERSE

        // 创建一个缩放动画,X轴
        val scaleAnimatorX = ObjectAnimator.ofFloat(imageViewHead, "scaleX", 0.5f, 1.5f)
        scaleAnimatorX.duration = 1000
        scaleAnimatorX.repeatCount = 10
        scaleAnimatorX.repeatMode = ValueAnimator.REVERSE

        // 创建一个缩放动画,Y轴
        val scaleAnimatorY = ObjectAnimator.ofFloat(imageViewHead, "scaleY", 0.5f, 1.5F)
        scaleAnimatorY.duration = 1000
        scaleAnimatorY.repeatCount = 10
        scaleAnimatorY.repeatMode = ValueAnimator.REVERSE

        // 创建一个动画组
        val animator = AnimatorSet()
        animator.play(scaleAnimatorX).with(scaleAnimatorY).after(rotateAnimator)
        animator.start()
    }
}