package com.example.viewpager2demo.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseLazyFragment : Fragment() {

    private lateinit var rootView: View

    //是否已经创建了View
    private var mIsViewCreated = false
    //Fragment当前可见状态
    private var mIsCurrentVisibleState = false

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(getContentViewLayout(), container, false)
        initView(rootView)
        mIsViewCreated = true
        if (userVisibleHint) {
            userVisibleHint = true
        }

        return rootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (mIsViewCreated) {
            if (isVisibleToUser && !mIsCurrentVisibleState) {

                dispatchUserVisibleHint(true)
            } else if (mIsCurrentVisibleState && !isVisibleToUser) {

                dispatchUserVisibleHint(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            dispatchUserVisibleHint(true)
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            dispatchUserVisibleHint(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsViewCreated = false
    }

    private fun dispatchUserVisibleHint(userVisibleHint: Boolean) {
        if (mIsCurrentVisibleState == userVisibleHint) {
            return
        }
        mIsCurrentVisibleState = userVisibleHint
        if (userVisibleHint) {
            onFragmentLoad()
        } else {
            onFragmentLoadStop()
        }
    }

    abstract fun getContentViewLayout() : Int

    abstract fun initView(initView: View)

    //页面可见调用  可以用来加载网络操作
    open fun onFragmentLoad() {}

    //页面不可见调用  可以停止网络的加载
    open fun onFragmentLoadStop() {}
}