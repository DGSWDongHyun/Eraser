package com.luckly.eraser.ui.dragndrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.speech.tts.TextToSpeech;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class DragDropOnDragListener implements View.OnDragListener {
    TextToSpeech tts;
    private Context context = null;

    public DragDropOnDragListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        // Get the drag drop action.
        int dragAction = dragEvent.getAction();

        if(dragAction == dragEvent.ACTION_DRAG_STARTED)
        {
            // Check whether the dragged view can be placed in this target view or not.

            ClipDescription clipDescription = dragEvent.getClipDescription();

            if (clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                // Return true because the target view can accept the dragged object.
                return true;

            }
        }else if(dragAction == dragEvent.ACTION_DRAG_ENTERED)
        {
            // When the being dragged view enter the target view, change the target view background color.

            return true;
        }else if(dragAction == dragEvent.ACTION_DRAG_EXITED)
        {
            // When the being dragged view exit target view area, clear the background color.
            resetTargetViewBackground(view);

            return true;
        }else if(dragAction == dragEvent.ACTION_DRAG_ENDED)
        {

            // When the drop ended reset target view background color.
            resetTargetViewBackground(view);

            return true;

        }else if(dragAction == dragEvent.ACTION_DROP)
        {
            // When drop action happened.

            // Get clip data in the drag event first.
            ClipData clipData = dragEvent.getClipData();

            // Get drag and drop item count.
            int itemCount = clipData.getItemCount();
            // If item count bigger than 0.
            if(itemCount > 0) {


                // Show a toast popup.
                // Reset target view background color.
                resetTargetViewBackground(view);

                // Get dragged view object from drag event object.
                View srcView = (View)dragEvent.getLocalState();
                // Get dragged view's parent view group.
                ViewGroup owner = (ViewGroup) srcView.getParent();
                // Remove source view from original parent view group.
                owner.removeView(srcView);

                // The drop target view is a LinearLayout object so cast the view object to it's type.
                ConstraintLayout newContainer = (ConstraintLayout) view;
                // Add the dragged view in the new container.
                newContainer.addView(srcView);

                if(srcView.getParent() != owner){
                    srcView.setVisibility(View.INVISIBLE);
                }else{
                    srcView.setVisibility(View.VISIBLE);
                }


                // Returns true to make DragEvent.getResult() value to true.
                return true;
            }

        }else if(dragAction == dragEvent.ACTION_DRAG_LOCATION)
        {
            return true;
        }else
        {
            Toast.makeText(context, "Drag and drop unknow action type.", Toast.LENGTH_LONG).show();
        }

        return false;
    }
    /* Reset drag and drop target view's background color. */
    private void resetTargetViewBackground(View view)
    {
        // Clear color filter.
        view.getBackground().clearColorFilter();

        // Redraw the target view use original color.
        view.invalidate();
    }

}