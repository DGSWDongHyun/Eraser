package com.luckly.eraser.ui.activity.dragndrop

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.luckly.eraser.R
import com.luckly.eraser.ui.activity.dragndrop.listener.DragDropOnDragListener
import com.luckly.eraser.ui.activity.dragndrop.listener.DragDropOnTouchListener
import com.luckly.eraser.ui.activity.main.MainActivity

class DragDropActivity : AppCompatActivity() {

    private val i = intent
    private var imageView: ImageView? = null
    private var textView_date: TextView? = null
    private var BeforePaper: ConstraintLayout? = null
    private var ConstraintLayoutTop: ConstraintLayout? = null
    private var ConstraintLayoutBottom: ConstraintLayout? = null
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_drop)

        // Init all controls.
        initControls()
    }

    private fun initControls() {
        val soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        val soundID = soundPool.load(applicationContext, R.raw.sound_paper, 1)
        textView_date = findViewById(R.id.date)
        if (intent != null) {
            textView_date!!.text = intent.getStringExtra("Date_string")
        }
        if (context == null) {
            context = applicationContext
        }
        if (imageView == null) {
            imageView = findViewById<View>(R.id.drag_drop_image) as ImageView
            BeforePaper = findViewById<View>(R.id.before_drop) as ConstraintLayout
            BeforePaper!!.setOnClickListener {
                BeforePaper!!.visibility = View.GONE
                imageView!!.visibility = View.VISIBLE
                soundPool.play(soundID, 1f, 1f, 0, 0, 1f)
            }
            imageView!!.tag = "ImageView"

            // Set on touch listener to source dragged view.
            imageView!!.setOnTouchListener(DragDropOnTouchListener())
        }
        if (ConstraintLayoutTop == null) {
            ConstraintLayoutTop = findViewById<View>(R.id.drag_drop_left_layout) as ConstraintLayout
            // Set on drag listener to target dropped view.
            ConstraintLayoutTop!!.setOnDragListener(DragDropOnDragListener(context, this))
        }
        if (ConstraintLayoutBottom == null) {
            ConstraintLayoutBottom = findViewById<View>(R.id.drag_drop_right_layout) as ConstraintLayout

            // Set on drag listener to target dropped view.
            ConstraintLayoutBottom!!.setOnDragListener(DragDropOnDragListener(context, this))
        }
    }
}