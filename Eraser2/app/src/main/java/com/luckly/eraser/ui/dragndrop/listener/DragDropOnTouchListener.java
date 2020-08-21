package com.luckly.eraser.ui.dragndrop.listener;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

public class DragDropOnTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // Get view object tag value.
        String tag = (String) view.getTag();

        // Create clip data.
        ClipData clipData = ClipData.newPlainText("", tag);

        // Create drag shadow builder object.
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);


        /* Invoke view object's startDrag method to start the drag action.
           clipData : to be dragged data.
           dragShadowBuilder : the shadow of the dragged view.
        */
        view.startDrag(clipData, dragShadowBuilder, view, 0);


        // Hide the view object because we are dragging it.
        view.setVisibility(View.INVISIBLE);

        return true;
    }
}