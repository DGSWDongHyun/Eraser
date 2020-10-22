package com.luckly.eraser.ui.fragment.setting

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luckly.eraser.R
import com.luckly.eraser.data.setting.Setting
import com.luckly.eraser.ui.activity.main.MainActivity
import com.luckly.eraser.ui.adapter.list.setting.SettingAdapter
import java.util.*

class SettingFragment : Fragment() {
    var adapter: SettingAdapter? = null
    var list: MutableList<Setting>? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyler)

        list = ArrayList()
        list!!.add(Setting("배경 설정", "배경을 설정 할 수 있어요."))
        list!!.add(Setting("개발자 보기", "개발자를 볼 수 있어요."))
        list!!.add(Setting("설정 초기화", "설정을 초기화 할 수 있어요."))

        adapter = SettingAdapter(list, context, this, activity)
        val manger = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = manger
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            try {
                val `in` = requireActivity().contentResolver.openInputStream(data!!.data!!)
                val img = BitmapFactory.decodeStream(`in`)
                `in`!!.close()
                val settingDetailView = View.inflate(context, R.layout.dialog_setting, null)
                initializeSetting(settingDetailView, img)
                val builder = AlertDialog.Builder(context)
                        .setTitle("배경 설정하기")
                        .setCancelable(false)
                        .setView(settingDetailView)
                        .setPositiveButton("확인") { dialogInterface, i -> (activity as MainActivity?)!!.setBackground(BitmapDrawable(img), img) }.setNegativeButton("취소") { dialogInterface, i -> }
                val alert = builder.create()
                alert.show()
            } catch (e: Exception) {
            }
        }
    }

    private fun initializeSetting(settingDetailView: View, bitmap: Bitmap) {
        val imageView = settingDetailView.findViewById<ImageView>(R.id.img_bg)
        imageView.setImageDrawable(BitmapDrawable(bitmap))
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}