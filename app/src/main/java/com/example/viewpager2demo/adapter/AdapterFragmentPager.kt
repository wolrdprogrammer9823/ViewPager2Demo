package com.example.viewpager2demo.adapter
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewpager2demo.fragments.HomeFragment
import com.example.viewpager2demo.fragments.IndicatorFragment
import com.example.viewpager2demo.fragments.OthersFragment
import com.example.viewpager2demo.fragments.PageFragment

class AdapterFragmentPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(PAGE_HOME, HomeFragment.newInstance("home","home"))
        fragments.put(PAGE_FIND, PageFragment.newInstance("page","page"))
        fragments.put(PAGE_INDICATOR, IndicatorFragment.newInstance("indicator","indicator"))
        fragments.put(PAGE_OTHERS, OthersFragment.newInstance("others", "others"))
    }

    override fun getItemCount(): Int = fragments.size()

    override fun createFragment(position: Int): Fragment = fragments[position]

    companion object {
        const val PAGE_HOME = 0
        const val PAGE_FIND = 1
        const val PAGE_INDICATOR = 2
        const val PAGE_OTHERS = 3
    }
}