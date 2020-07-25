package com.example.viewpager2demo.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewpager2demo.R
import kotlinx.android.synthetic.main.fragment_indicator.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val MAPLE_FILE = "world.jpg"

class IndicatorFragment : BaseLazyFragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IndicatorFragment().apply {
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

    override fun getContentViewLayout(): Int = R.layout.fragment_indicator

    override fun initView(initView: View) {
        //初始化view操作
        val inputStream = resources.assets.open(MAPLE_FILE)
        initView.show_large_view.setInputStream(inputStream)
    }

    override fun onFragmentLoad() {
        super.onFragmentLoad()

    }

    override fun onFragmentLoadStop() {
        super.onFragmentLoadStop()

    }
}