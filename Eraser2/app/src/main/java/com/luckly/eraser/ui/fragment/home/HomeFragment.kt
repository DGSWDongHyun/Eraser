package com.luckly.eraser.ui.fragment.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luckly.eraser.R
import com.luckly.eraser.data.SliderItem
import com.luckly.eraser.data.Write
import com.luckly.eraser.databinding.FragmentHomeBinding
import com.luckly.eraser.ui.adapter.sliderview.SlideAdapter
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    var animation: Animation? = null
    var key_time = "KEY-TIME"
    var adapters: SlideAdapter? = null
    var data: List<Write> = ArrayList()
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var homeBinding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(key_time, Context.MODE_PRIVATE)

        setUpTime()
        setUpSlider()
        setUpAnimation()

    }

    private fun setUpAnimation() {
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate)
        homeBinding.imgCd.animation = animation
    }

    private fun setUpSlider() {
        adapters = SlideAdapter(context)

        homeBinding.imageSlider.setSliderAdapter(adapters!!)
        homeBinding.imageSlider.setSliderAnimationDuration(2500)
        homeBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION)
        homeBinding.imageSlider.startAutoCycle()
        homeBinding.imageSlider.setIndicatorEnabled(false)
        homeBinding.imageSlider.scrollTimeInSec = 3;
        homeBinding.imageSlider.isAutoCycle = true
        addNewItem(homeBinding.imageSlider)
    }

    private fun setUpTime() {
        Handler().postDelayed({
            homeBinding.time.text = "당신의 시간 \n${SimpleDateFormat("yyyy.MM.dd / HH : mm : ss").format(Date(System.currentTimeMillis()))} \n이곳에서는 편히 있길 바래요."
            setUpTime()
        }, 1000)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return homeBinding.root
    }

    private fun addNewItem(view: View?) {
        val firSliderItem = SliderItem(Integer(R.drawable.bg_1))
        adapters!!.addItem(firSliderItem)

        val secSliderItem = SliderItem(Integer(R.drawable.bg_2))
        adapters!!.addItem(secSliderItem)

        val thrSliderItem = SliderItem(Integer(R.drawable.bg_3))
        adapters!!.addItem(thrSliderItem)
    }
}