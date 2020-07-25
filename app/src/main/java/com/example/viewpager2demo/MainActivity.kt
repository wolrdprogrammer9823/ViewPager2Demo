package com.example.viewpager2demo
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager2demo.adapter.AdapterFragmentPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onContentChanged() {

        super.onContentChanged()

        view_pager2.adapter = AdapterFragmentPager(this)
        view_pager2.offscreenPageLimit = 3
        //ViewPager2.ORIENTATION_HORIZONTAL 水平滑动
        //ViewPager2.ORIENTATION_VERTICAL   垂直滑动
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        view_pager2.isUserInputEnabled = false

        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tab_layout.setScrollPosition(position, 0.0f, true, true)
            }
        })

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_pager2.currentItem = tab?.position!!
            }
        })
    }

}