package com.example.viewpager2demo.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewpager2demo.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OthersFragment : BaseLazyFragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OthersFragment().apply {
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

    override fun getContentViewLayout(): Int = R.layout.fragment_others

    override fun initView(initView: View) {
        //初始化view操作
    }

    override fun onFragmentLoad() {
        super.onFragmentLoad()
    }

    override fun onFragmentLoadStop() {
        super.onFragmentLoadStop()
    }
}