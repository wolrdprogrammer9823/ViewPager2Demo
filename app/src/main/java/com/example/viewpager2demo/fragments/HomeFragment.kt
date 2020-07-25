package com.example.viewpager2demo.fragments
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.viewpager2demo.R
import com.example.viewpager2demo.adapter.MainViewPager2Adapter
import com.example.viewpager2demo.animation.ScaleInTransformer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : BaseLazyFragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
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

    override fun getContentViewLayout(): Int = R.layout.fragment_home

    override fun initView(initView: View) {

        //初始化view操作
        val adapter = MainViewPager2Adapter(requireContext())
        adapter.setDataList(listOf(0, 1, 2, 3))
        initView.main_view_pager2.adapter = adapter

        //设置item之间的边距
        //initView.main_view_pager2.setPageTransformer(MarginPageTransformer(resources.getDimension(R.dimen.dp_10).toInt()))

        initView.main_root_layout.clipChildren = false
        initView.main_view_pager2.clipChildren = false
        initView.main_view_pager2.offscreenPageLimit = 2
        val params = initView.main_view_pager2.layoutParams as ViewGroup.MarginLayoutParams
        params.leftMargin = resources.getDimension(R.dimen.dp_10).toInt().times(2)
        params.rightMargin = params.leftMargin

        //CompositePageTransformer 使得一个页面可以增加多个Transformer
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(resources.getDimension(R.dimen.dp_10).toInt()))
        compositePageTransformer.addTransformer(ScaleInTransformer())
        initView.main_view_pager2.setPageTransformer(compositePageTransformer)
    }

    override fun onFragmentLoad() {
        super.onFragmentLoad()
    }

    override fun onFragmentLoadStop() {
        super.onFragmentLoadStop()
    }
}