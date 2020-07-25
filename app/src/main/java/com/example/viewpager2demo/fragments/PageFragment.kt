package com.example.viewpager2demo.fragments
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.example.viewpager2demo.R
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.android.synthetic.main.fragment_page.view.*
import kotlinx.android.synthetic.main.fragment_page.view.load_scaled_iv
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PageFragment : BaseLazyFragment(), View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private var param1: String? = null
    private var param2: String? = null
    private var selectedItem = 0

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun getContentViewLayout(): Int = R.layout.fragment_page

    override fun initView(initView: View) {
        //初始化view操作
        initView.load_btn.setOnClickListener(this)
        initView.page_radio_group.setOnCheckedChangeListener(this)
    }

    override fun onFragmentLoad() {
        super.onFragmentLoad()
    }

    override fun onFragmentLoadStop() {
        super.onFragmentLoadStop()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.load_btn -> {
                when (selectedItem) {
                    0, 1, 2 -> {

                        load_clip_iv.visibility = View.GONE
                        load_scaled_iv.visibility = View.VISIBLE

                        val inputStream = resources.assets.open("timg.jpg")
                        val contentInputStream = resources.assets.open("timg.jpg")
                        val byteArray = getImageBytes(contentInputStream)
                        Log.d("bitmap", "byteArray:${byteArray.size}")

                        //获取图片宽高
                        val bitmapFactoryOptions = BitmapFactory.Options()
                        bitmapFactoryOptions.inJustDecodeBounds = true
                        BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptions)
                        val imageWidth = bitmapFactoryOptions.outWidth
                        val imageHeight = bitmapFactoryOptions.outHeight
                        Log.d("bitmap", "imageWidth:$imageWidth,imageHeight:$imageHeight")

                        val previewWith = resources.getDimension(R.dimen.dp_300)
                        val previewHeight = resources.getDimension(R.dimen.dp_300)

                        Log.d("bitmap", "value1:${imageWidth / previewWith}")
                        Log.d("bitmap", "value2:${imageHeight / previewHeight}")
                        val scaleFactory =
                            Math.max(imageWidth / previewWith, imageHeight / previewHeight)
                        bitmapFactoryOptions.inSampleSize =
                            scaleFactory.times(selectedItem * 3).toInt()
                        Log.d("bitmap", "inSampleSize:${bitmapFactoryOptions.inSampleSize}")
                        bitmapFactoryOptions.inJustDecodeBounds = false
                        //样图可以回收内存
                        bitmapFactoryOptions.inPurgeable = true

                        val scaleBitmap =
                            BitmapFactory.decodeByteArray(
                                byteArray,
                                0,
                                byteArray.size,
                                bitmapFactoryOptions
                            )
                        Log.d("bitmap", "scaleBitmap:${scaleBitmap == null}")
                        load_scaled_iv.setImageBitmap(scaleBitmap)
                    }
                    3 -> {

                        load_scaled_iv.visibility = View.GONE
                        load_clip_iv.visibility = View.VISIBLE

                        val inputStream = resources.assets.open("timg.jpg")
                        //获取图片宽高
                        val bitmapFactoryOptions = BitmapFactory.Options()
                        bitmapFactoryOptions.inJustDecodeBounds = true
                        BitmapFactory.decodeStream(inputStream, null, bitmapFactoryOptions)
                        val imageWidth = bitmapFactoryOptions.outWidth
                        val imageHeight = bitmapFactoryOptions.outHeight
                        Log.d("bitmap", "imageWidth:$imageWidth,imageHeight:$imageHeight")

                        //设置图片显示的中心区域
                        val bitmapRegionDecoder =
                            BitmapRegionDecoder.newInstance(inputStream, false)
                        val bitmapRegionDecoderOptions = BitmapFactory.Options()
                        bitmapFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565
                        val bitmap = bitmapRegionDecoder.decodeRegion(
                            Rect(
                                imageWidth / 2 - 100,
                                imageHeight / 2 - 100,
                                imageWidth / 2 + 100,
                                imageHeight / 2 + 100
                            ), bitmapRegionDecoderOptions
                        )

                        load_clip_iv.setImageBitmap(bitmap)
                    }
                    else -> {}
                }
            }
            else -> {}
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.not_scale_rb -> {
                selectedItem = 0
                Log.d("onCheckedChanged","onCheckedChanged:R.id.not_scale_rb")
            }
            R.id.a_half_scale_rb -> {
                selectedItem = 1
                Log.d("onCheckedChanged","onCheckedChanged:R.id.a_half_scale_rb")
            }
            R.id.quarter_scale_rb -> {
                selectedItem = 2
                Log.d("onCheckedChanged","onCheckedChanged:R.id.quarter_scale_rb")
            }
            R.id.clip_rb -> {
                selectedItem = 3
                Log.d("onCheckedChanged","onCheckedChanged:R.id.clip_rb")
            }
            else ->{}
        }
    }

    private fun getImageBytes(inputStream: InputStream) : ByteArray {

        val byteOutputStream = ByteArrayOutputStream()
        val bytes = ByteArray(1024)
        var length: Int

        try {
            while (inputStream.read(bytes).also { length = it } != -1) {
                byteOutputStream.write(bytes, 0, length)
            }
        } catch (e: IOException) {}

        try {
            byteOutputStream.close()
            inputStream.close()
        } catch (e: IOException) {}

        return byteOutputStream.toByteArray()
    }

}