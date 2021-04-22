package com.zerodevi1.firstkotlinapp

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap

/**
 * TODO: document your custom view class.
 */
class RoundImageView : View {

    private var borderColor = Color.RED

    // 公开属性 drawable 的后台数据
    private var _drawable: Drawable? = null

    // 要显示的图像,支持在代码中设置图像,所以此属性为public
    public var drawable: Drawable?
        get() = _drawable
        set(value) {
            this._drawable = drawable
            // 从 Drawable 对象获取 Bitmap 对象,用于创建 BitmapShader
            getBitmapFromDrawable(_drawable)?.let {
                // 保留下图像的宽和高
                bitmapHeight = it.height
                bitmapWidth = it.width

                // 重新计算位图着色器的变换矩阵
                updateShaderMatrix()
                // 发出通知,强制系统重新绘制组件
                invalidate()
            }
        }

    // 图像的宽
    private var bitmapWidth: Int = 0

    // 图像的高
    private var bitmapHeight: Int = 0

    // 画圆形图像时,半径的位置
    private var drawableRadius: Float = 0F

    // 画边框时,半径的位置
    private var borderRadius: Float = 0f

    // 边框的宽度
    private var borderStrokeWidth = 1f

    // 着色器,这是画出圆形图像的关键
    private var bitmapShader: BitmapShader? = null

    // 用于画出圆形图像的画笔
    private var bitmapPaint: Paint? = null

    // 用于画出图像边界的画笔
    private var borderPaint: Paint? = null


    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.RoundImageView, defStyle, 0
        )
        // 获取文本颜色
        borderColor = a.getColor(R.styleable.RoundImageView_borderColor, borderColor)
        // 获取边框宽度
        borderStrokeWidth =
            a.getDimension(R.styleable.RoundImageView_borderWidth, borderStrokeWidth)
        // 获取图像
        if (a.hasValue(R.styleable.RoundImageView_drawable)) {
            this._drawable = a.getDrawable(R.styleable.RoundImageView_drawable)
        }
        // 不再需要从attrs获取属性值,及时释放一些资源
        a.recycle()

        if (drawable != null) {
            // 从 Drawable对象获取Bitmap对象,用于创建BitmapShader
            // Drawable只是Android SDK对于可绘制对象的封装
            // 底层的图像绘制使用的是Bitmap(位图或者光栅图)
            var bitmap = getBitmapFromDrawable(drawable)
            // 保留下图像的宽和高
            bitmapHeight = bitmap!!.height
            bitmapWidth = bitmap.width

            // 创建着色器,第二个和第三个参数指明了图像的平铺模式
            // 这里设置成不平铺
            bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            // 创建画位图的画笔
            bitmapPaint = Paint()
            // 把着色器设置给画笔
            bitmapPaint!!.setShader(bitmapShader)

            // 创建边框的画笔
            borderPaint = Paint()
            borderPaint?.apply {
                // 只画线不填充
                style = Paint.Style.STROKE
                // 画边框需要平滑效果
                flags = Paint.ANTI_ALIAS_FLAG
                // 设置边框的颜色
                color = borderColor
                // 设置边框线条粗细
                strokeWidth = borderStrokeWidth
            }

        }


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmapPaint?.let {
            // 画边框
            canvas.drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                borderRadius,
                borderPaint!!
            )
            // 画图像
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), drawableRadius, it)
        }

    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null;
        }
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        try {
            var bitmap: Bitmap
            if (drawable is ColorDrawable) {
                // 如果是一个颜色,则创建一个宽和高都是一个像素的Bitmap
                // 指定其颜色空间 ARGB 四通道,每个通道占8个字节
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            } else {
                bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
            }
            // 位图中必须有drawable中的图案,所以用位图创建画布,
            // 把 drawable 画到画布上,实际就画到了位图上
            val canvas = Canvas(bitmap)
            // 设置绘画的区域,绘制不会超越这个区域
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap;
        } catch (e: OutOfMemoryError) {
            // 如果内存不够用,返回null
            return null
        }
    }

    // 计算位图的变换矩阵
    private fun updateShaderMatrix() {
        // 获取控件上下左右padding,用于计算内容所处的区域
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom

        // 计算边框的半径
        borderRadius = Math.min((height - borderStrokeWidth) / 2f, (width - borderStrokeWidth) / 2f)

        // 位图所在的外围框,位图不能超出这个矩形
        // 这个矩形应该控件的边框内,同时还有考虑padding的大小
        val drawableRect = RectF(
            borderStrokeWidth + paddingLeft,
            borderStrokeWidth + paddingTop,
            width - borderStrokeWidth - paddingRight,
            height - borderStrokeWidth - paddingBottom
        )

        // 计算画位图所用的半径
        drawableRadius = Math.min(drawableRect.height() / 2f, drawableRect.width() / 2f)

        var scale: Float
        // 图像在x轴上开始的位置
        var dx = 0f
        // 图像在y轴上开始的位置
        var dy = 0f

        // 三维变换矩阵,用于计算图像的缩放和位移
        val mShaderMatrix = Matrix()
        mShaderMatrix.set(null)
        // 计算图像需要缩放的比例,要保证图像根据其外围框的大小和长宽比进行按比例缩放
        if (bitmapWidth * drawableRect.height() < drawableRect.width() * bitmapHeight) {
            // 如果图像的宽大于外围框的宽,则图像缩放后的高度变成跟外围框高度一样
            // 然后按比例计算图像缩放后的宽度
            scale = drawableRect.height() / bitmapHeight.toFloat()
            // 因图像比外围框窄,所以计算x轴上图像开始的位置
            dx = (drawableRect.width() - bitmapWidth * scale) * 0.5f
        } else {
            // 如果图像的宽小于外围框的宽,则图像缩放后的高度变成跟外围框宽度一样
            // 然后按比例计算图像缩放后的宽度
            scale = drawableRect.width() / bitmapWidth.toFloat()
            // 因图像比外围框宽,所以计算y轴上图像开始的位置
            dy = (drawableRect.height() - bitmapHeight * scale) * 0.5f
        }

        // 设置位图在x轴和y轴的缩放比例
        mShaderMatrix.setScale(scale, scale)
        // 设置位图在x轴和y轴上的位移,以保证图像居中
        mShaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + borderStrokeWidth,
            (dy + 0.5f).toInt() + borderStrokeWidth
        )
        // 将变换矩阵设置给着色器
        bitmapShader?.setLocalMatrix(mShaderMatrix)
    }
}