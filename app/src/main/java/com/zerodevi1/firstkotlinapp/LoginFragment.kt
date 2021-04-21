package com.zerodevi1.firstkotlinapp

import android.animation.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
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
//            testAnimatorSet()
//            testAnimateResource()
            testLayoutAnimate()
        }

        // 启动注册页面
        this.buttonRegister.setOnClickListener {
            // 启动注册页面
            val fragment = RegisterFragment()
            // 当注册按钮调用的时候执行此方法
            val fragmentManager = requireActivity().supportFragmentManager
            // addToBackStack() 把这次操作放到后退栈,这样用户点击返回键就会执行反向操作,也可以使用 FragmentManager 执行反向操作
            // 参数是这次操作的名称,用于查找某个操作
            fragmentManager.beginTransaction()
                // 设置动画必须在操作前
                .setCustomAnimations(
                    R.anim.in_anim1,
                    R.anim.out_anim1,
                    R.anim.in_anim2,
                    R.anim.out_anim2
                )
                .replace(R.id.fragment_container, fragment)
                .addToBackStack("login")
                // 为 Fragment 切换增加动画,进入和退出是相反的动画
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
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

    private fun testAnimateResource() {
        // 利用 AnimatorInflater 从资源中加载动画
        val set = AnimatorInflater.loadAnimator(context, R.animator.test_animate) as AnimatorSet
        // 资源中并没有指定动画要应用到哪个控件上,所以在这里指定
        set.setTarget(imageViewHead)
        set.start()
    }

    /**
     * 测试 Layout 动画
     */
    private fun testLayoutAnimate() {
        // 创建一个新按钮
        val btn = Button(context)
        // 设置它显示的文本
        btn.setText("我是被动态添加的")
        // 创建一个排版参数对象
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, // 横向由约束拉伸
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // 把应用这个LayoutParams(排版参数)的控件放在注册按钮的下面
        layoutParams.topToBottom = R.id.buttonRegister
        // 左边与注册按钮对齐
        layoutParams.leftToLeft = R.id.buttonRegister
        // 右边也与注册按钮对齐
        layoutParams.rightToRight = R.id.buttonRegister
        // 设置外部空白,参数分别为 左 上 右 下 ,实际效果是设置顶部与相邻控件的间距
        // 24个像素,这里的单位不是dp
        layoutParams.setMargins(0, 24, 0, 0)
        // 将这个排版参数应用到新建的按钮中
        btn.layoutParams = layoutParams

        val transition = LayoutTransition()
        // 当一个控件出现时,希望它是大小有变化的动画
        // 利用 AnimatorInflater 从资源中加载动画
        val set = AnimatorInflater.loadAnimator(context, R.animator.test_animate) as AnimatorSet
        // 设置控件出现时的动画
        transition.setAnimator(LayoutTransition.APPEARING, set)
        // 设置一个控件出现时,其他控件位置改变动画的持续时间
        transition.setDuration(LayoutTransition.CHANGE_APPEARING, 4000)
        // 将包含动画的 LayoutTransition 对象设置到 ViewGroup 控件中
        layout.layoutTransition = transition
        // 将按钮添加到 RelativeLayout 中
        layout.addView(btn)
    }
}