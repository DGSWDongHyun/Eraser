package com.luckly.eraser.ui.fragment.write

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.luckly.eraser.R
import com.luckly.eraser.data.write.Write
import com.luckly.eraser.ui.activity.dragndrop.DragDropActivity
import java.text.SimpleDateFormat
import java.util.*

class WriteFragment : Fragment() {

    var key = "KEY-SAVE"
    var editText_content: EditText? = null
    var submitButton: Button? = null
    var data = ArrayList<Write>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText_content = view.findViewById(R.id.editText_content)
        submitButton = view.findViewById(R.id.submitButton)
        submitButton!!.setOnClickListener(View.OnClickListener { view1: View? ->
            if (!editText_content!!.getText().toString().isEmpty()) {
                data.add(Write(SimpleDateFormat("yyyy년 MM월 dd일").format(Date(System.currentTimeMillis())), "당신이 지워버린 오늘의 고민", editText_content!!.getText().toString()))
                saveArrayList(data, key)
                editText_content!!.setText("")
                val intent = Intent(context, DragDropActivity::class.java)
                val date_str = SimpleDateFormat("yyyy년 MM월 dd일").format(Date(System.currentTimeMillis()))
                intent.putExtra("Date_string", date_str)
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.visible_effects, R.anim.invisible_effects)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_write, container, false)
    }

    fun saveArrayList(list: ArrayList<Write>?, key: String?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }
}