package com.luckly.eraser.ui.activity.dragndrop.listener

import android.content.ClipData
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnTouchListener

class DragDropOnTouchListener : OnTouchListener {
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

        // Get view object tag value.
        val tag = view.tag as String

        // Create clip data.
        val clipData = ClipData.newPlainText("", tag)

        // Create drag shadow builder object.
        val dragShadowBuilder = DragShadowBuilder(view)


        /* Invoke view object's startDrag method to start the drag action.
           clipData : to be dragged data.
           dragShadowBuilder : the shadow of the dragged view.
        */view.startDrag(clipData, dragShadowBuilder, view, 0)


        // Hide the view object because we are dragging it.
        view.visibility = View.INVISIBLE
        return true
    }
}