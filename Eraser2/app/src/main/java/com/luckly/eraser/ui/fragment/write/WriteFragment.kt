package com.luckly.eraser.ui.fragment.write

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.luckly.eraser.R
import com.luckly.eraser.data.Write
import com.luckly.eraser.databinding.FragmentWriteBinding
import com.luckly.eraser.ui.activity.dragndrop.DragDropActivity
import java.text.SimpleDateFormat
import java.util.*

class WriteFragment : Fragment() {

    private lateinit var writeBinding : FragmentWriteBinding
    var data = ArrayList<Write>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        writeBinding.submitButton.setOnClickListener(View.OnClickListener { view1: View? ->
            if (writeBinding.submitButton.text.toString().isNotEmpty()) {
                data.add(Write(SimpleDateFormat("yyyy년 MM월 dd일").format(Date(System.currentTimeMillis())), "당신이 지워버린 오늘의 고민", writeBinding.editTextContent.text.toString()))
                writeBinding.editTextContent.text = Editable.Factory.getInstance().newEditable("")
                val intent = Intent(context, DragDropActivity::class.java)
                val dateStr = SimpleDateFormat("yyyy년 MM월 dd일").format(Date(System.currentTimeMillis()))
                intent.putExtra("Date_string", dateStr)
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.visible_effects, R.anim.invisible_effects)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        writeBinding = FragmentWriteBinding.inflate(layoutInflater)
        return writeBinding.root
    }

}