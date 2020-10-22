package com.luckly.eraser.ui.activity.dragndrop.listener

import android.app.Activity
import android.content.ClipDescription
import android.content.Context
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.luckly.eraser.R

class DragDropOnDragListener(context: Context?, activity: Activity?) : OnDragListener {
    var tts: TextToSpeech? = null
    var str_healing = arrayOf("당신의 고민이 치유되기를 바라.\n 당신이 많이 힘들었고, 고통스러웠던 걸 알았기에 더더욱.",
            "오늘의 마음이 내일의 마음에는 긁힘하나 나지 않게, \n당신의 마음은 누구보다 당신이 잘 알기에.",
            "오늘만큼은 당신에게 관대해지는 건 어떨까요? \n당신은 존중받아야 마땅한 존재인걸요.",
            "너무 아파하지마요, 겉에 상처도 천천히 아물고 치유되듯이,\n당신의 마음도 천천히 치유될거에요.",
            "누구든 상처는 있기 마련이지만, 오늘 이후로 누군가 이 일을 기억하냐고 묻는다면, \n신경쓰지 않는다고 당당히 말할 수 있는 사람이 되길.")
    private var context: Context? = null
    private var activity: Activity? = null
    override fun onDrag(view: View, dragEvent: DragEvent): Boolean {

        // Get the drag drop action.
        val dragAction = dragEvent.action
        if (dragAction == DragEvent.ACTION_DRAG_STARTED) {
            // Check whether the dragged view can be placed in this target view or not.
            val clipDescription = dragEvent.clipDescription
            if (clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                // Return true because the target view can accept the dragged object.
                return true
            }
        } else if (dragAction == DragEvent.ACTION_DRAG_ENTERED) {
            // When the being dragged view enter the target view, change the target view background color.
            return true
        } else if (dragAction == DragEvent.ACTION_DRAG_EXITED) {
            // When the being dragged view exit target view area, clear the background color.
            resetTargetViewBackground(view)
            return true
        } else if (dragAction == DragEvent.ACTION_DRAG_ENDED) {

            // When the drop ended reset target view background color.
            resetTargetViewBackground(view)
            return true
        } else if (dragAction == DragEvent.ACTION_DROP) {
            // When drop action happened.

            // Get clip data in the drag event first.
            val clipData = dragEvent.clipData

            // Get drag and drop item count.
            val itemCount = clipData.itemCount
            // If item count bigger than 0.
            if (itemCount > 0) {


                // Show a toast popup.
                // Reset target view background color.
                resetTargetViewBackground(view)

                // Get dragged view object from drag event object.
                val srcView = dragEvent.localState as View
                // Get dragged view's parent view group.
                val owner = srcView.parent as ViewGroup
                // Remove source view from original parent view group.
                owner.removeView(srcView)

                // The drop target view is a LinearLayout object so cast the view object to it's type.
                val newContainer = view as ConstraintLayout
                // Add the dragged view in the new container.
                newContainer.addView(srcView)
                if (srcView.parent !== owner) {
                    srcView.visibility = View.INVISIBLE
                    val tv = activity!!.findViewById<TextView>(R.id.thx)
                    val animation = AnimationUtils.loadAnimation(activity, R.anim.visible_effects)
                    val rand = (Math.random() * str_healing.size).toInt()
                    tv.text = str_healing[rand]
                    tv.animation = animation
                    tv.visibility = View.VISIBLE
                    Handler().postDelayed({
                        activity!!.finish()
                        activity!!.overridePendingTransition(R.anim.visible_effects, R.anim.invisible_effects)
                    }, 5000) // 0.5초 정도 딜레이를 준 후 시작
                } else {
                    srcView.visibility = View.VISIBLE
                }


                // Returns true to make DragEvent.getResult() value to true.
                return true
            }
        } else if (dragAction == DragEvent.ACTION_DRAG_LOCATION) {
            return true
        } else {
            Toast.makeText(context, "Drag and drop unknow action type.", Toast.LENGTH_LONG).show()
        }
        return false
    }

    /* Reset drag and drop target view's background color. */
    private fun resetTargetViewBackground(view: View) {
        // Clear color filter.
        view.background.clearColorFilter()

        // Redraw the target view use original color.
        view.invalidate()
    }

    init {
        this.context = context
        this.activity = activity
    }
}