package com.luckly.eraser.ui.adapter.sliderview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.luckly.eraser.R
import com.luckly.eraser.data.SliderItem
import com.luckly.eraser.ui.adapter.sliderview.SlideAdapter.SliderAdapterVH
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.*


class SlideAdapter(private val context: Context?) : SliderViewAdapter<SliderAdapterVH>() {
    private var mSliderItems: MutableList<SliderItem> = ArrayList()


    fun addItem(sliderItem: SliderItem) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val (imageUrl) = mSliderItems[position]
        Glide.with(viewHolder.itemView)
                .load(imageUrl)
                .fitCenter()
                .into(viewHolder.imageViewBackground)
        viewHolder.itemView.setOnClickListener { }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)

    }
}