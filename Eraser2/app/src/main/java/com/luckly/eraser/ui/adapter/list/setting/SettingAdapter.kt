package com.luckly.eraser.ui.adapter.list.setting

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.luckly.eraser.R
import com.luckly.eraser.data.Setting
import com.luckly.eraser.ui.activity.main.MainActivity
import com.luckly.eraser.ui.adapter.list.setting.SettingAdapter.SettingViewHolder

class SettingAdapter(var setting: List<Setting>?, var context: Context?, var fragment: Fragment, var activity: Activity?) : RecyclerView.Adapter<SettingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.setting_item, parent, false)
        return SettingViewHolder(root)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        val (Title, Description) = setting!![position]
        holder.textViewTitle.text = Title
        holder.textViewDescription.text = Description
    }

    override fun getItemCount(): Int {
        return if (setting != null) setting!!.size else 0
    }

    inner class SettingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView
        var textViewDescription: TextView

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    when (setting!![pos].Title) {
                        "배경 설정" -> {
                            val settingDetailView = View.inflate(context, R.layout.dialog_setting, null)
                            val builder = AlertDialog.Builder(context)
                                    .setTitle("배경 설정하기")
                                    .setCancelable(false)
                                    .setView(settingDetailView)
                                    .setNegativeButton("취소") { dialogInterface, i -> }
                            val alert = builder.create()
                            initializeSetting(settingDetailView, alert)
                            alert.show()
                        }
                        "개발자 보기" -> {
                            val aboutDeveloper = View.inflate(context, R.layout.setting_dev, null)
                            val builderDev = AlertDialog.Builder(context)
                                    .setTitle("개발자 보기")
                                    .setCancelable(false)
                                    .setView(aboutDeveloper)
                                    .setPositiveButton("확인") { dialogInterface, i -> }
                            val alertDev = builderDev.create()
                            alertDev.show()
                        }
                        "설정 초기화" -> {
                            val bitmap = (context!!.getDrawable(R.drawable.background) as BitmapDrawable?)!!.bitmap
                            (activity as MainActivity?)!!.setBackground(BitmapDrawable(bitmap), bitmap)
                        }
                    }
                }
            }
            textViewTitle = itemView.findViewById(R.id.title)
            textViewDescription = itemView.findViewById(R.id.description)
        }
    }

    private fun initializeSetting(settingDetailView: View, alert: AlertDialog) {

        val selectButton = settingDetailView.findViewById<Button>(R.id.selectButton)
        val imgBg = settingDetailView.findViewById<ImageView>(R.id.imgBg)
        imgBg.setImageDrawable((activity as MainActivity).getBackground())

        selectButton.setOnClickListener { v: View? ->
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            fragment.startActivityForResult(intent, REQUEST_CODE)
            alert.dismiss()
        }
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}