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
import com.luckly.eraser.data.slider.SliderItem
import com.luckly.eraser.data.write.Write
import com.luckly.eraser.ui.adapter.list.deleted.EraserAdapter
import com.luckly.eraser.ui.adapter.sliderview.SlideAdapter
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    var animation: Animation? = null
    var key = "KEY-SAVE"
    var key_time = "KEY-TIME"
    private var img_music: ImageView? = null
    var adapter: EraserAdapter? = null
    var adapters: SlideAdapter? = null
    private var tv: TextView? = null
    private var music_t: TextView? = null
    var data: List<Write> = ArrayList()
    private var sharedPreferences: SharedPreferences? = null
    private var sliderView: SliderView? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(key_time, Context.MODE_PRIVATE)
        Music(view)

        tv = view.findViewById(R.id.time)
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate)
        img_music = view.findViewById(R.id.img_cd)
        music_t = view.findViewById(R.id.music_t)
        img_music!!.animation = animation
        sliderView = view.findViewById(R.id.imageSlider)

        times()

        adapters = SlideAdapter(context)

        sliderView!!.setSliderAdapter(adapters!!)
        sliderView!!.setSliderAnimationDuration(2500)
        sliderView!!.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION)
        sliderView!!.startAutoCycle()
        sliderView!!.setIndicatorEnabled(false)
        sliderView!!.scrollTimeInSec = 3;
        sliderView!!.isAutoCycle = true
        addNewItem(sliderView)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adapter = if (getArrayList(key) != null) {
            EraserAdapter(getArrayList(key), context)
        } else {
            EraserAdapter(data, context)
        }
        val manger = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = manger
    }

    fun Music(view: View) {
        music_t = view.findViewById(R.id.music_t)
        music_t!!.setOnClickListener(View.OnClickListener { })
    }

    fun times() {
        Handler().postDelayed({
            tv!!.text = """당신의 시간 ${SimpleDateFormat("yyyy.MM.dd / HH : mm : ss").format(Date(System.currentTimeMillis()))} 이곳에서는 편히 있길 바라."""
            times()
        }, 1000)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun addNewItem(view: View?) {
        val sliderItem = SliderItem()
        sliderItem.imageUrl = Integer(R.drawable.bg_1)
        adapters!!.addItem(sliderItem)

        val sliderItem2 = SliderItem()
        sliderItem2.imageUrl = Integer(R.drawable.bg_2)
        adapters!!.addItem(sliderItem2)

        val sliderItem3 = SliderItem()
        sliderItem3.imageUrl = Integer(R.drawable.bg_3)
        adapters!!.addItem(sliderItem3)
    }

    fun getArrayList(key: String?): List<Write>? {

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<Write?>?>() {}.type
        return if (json != null) gson.fromJson(json, type) else null

    }
}