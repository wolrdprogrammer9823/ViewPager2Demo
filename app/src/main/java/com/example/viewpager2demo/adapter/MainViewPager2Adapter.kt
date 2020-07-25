package com.example.viewpager2demo.adapter
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpager2demo.R
import kotlinx.android.synthetic.main.view_pager2_item.view.*

class MainViewPager2Adapter(context: Context) : RecyclerView.Adapter<MainViewPager2Adapter.MainViewHolder>() {

    private val mContext = context
    private var mList: List<Int> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewPager2Adapter.MainViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.view_pager2_item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: MainViewPager2Adapter.MainViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    fun setDataList(dataList: List<Int>) {
        this.mList = dataList
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var colors = arrayOf("#CCFF99","#41F1E5","#8D41F1","#FF99CC")

        fun bindData(position: Int) {

            itemView.view_pager2_item_tv.text = position.toString()
            itemView.view_pager2_item_tv.setBackgroundColor(Color.parseColor(colors[position]))
        }
    }
}