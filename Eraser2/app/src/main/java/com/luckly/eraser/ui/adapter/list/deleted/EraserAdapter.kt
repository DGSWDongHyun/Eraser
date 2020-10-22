package com.luckly.eraser.ui.adapter.list.deleted

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luckly.eraser.R
import com.luckly.eraser.data.write.Write
import com.luckly.eraser.ui.adapter.list.deleted.EraserAdapter.EraserViewHolder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EraserAdapter(var write_data: List<Write>?, var context: Context?) : RecyclerView.Adapter<EraserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EraserViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment, parent, false)
        return EraserViewHolder(root)
    }

    override fun onBindViewHolder(holder: EraserViewHolder, position: Int) {
        val write = write_data!![position]
        val transFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        try {
            val was = transFormat.parse(write.Date)
            val now = Date(System.currentTimeMillis())
            if (now.before(was)) {
                write.Title = "당신이 지워버린 오늘의 고민"
            } else {
                write.Title = "당신이 지워버린 과거의 고민"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        holder.textView_title.text = write.Title
        holder.textView_content.text = write.Content
        holder.textView_date.text = write.Date
    }

    override fun getItemCount(): Int {
        return if (write_data != null) write_data!!.size else 0
    }

    inner class EraserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView_title: TextView
        var textView_date: TextView
        var textView_content: TextView

        init {
            textView_title = itemView.findViewById(R.id.textView_title)
            textView_content = itemView.findViewById(R.id.textView_content)
            textView_date = itemView.findViewById(R.id.textView_date)
        }
    }
}