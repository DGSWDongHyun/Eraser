package com.luckly.eraser.ui.activity.dragndrop.listener;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.luckly.eraser.R;

public class DragDropOnDragListener implements View.OnDragListener {
    TextToSpeech tts;
    String[] str_healing = {"당신의 고민이 치유되기를 바라.\n 당신이 많이 힘들었고, 고통스러웠던 걸 알았기에 더더욱.",
            "오늘의 마음이 내일의 마음에는 긁힘하나 나지 않게, \n당신의 마음은 누구보다 당신이 잘 알기에.",
            "오늘만큼은 당신에게 관대해지는 건 어떨까요? \n당신은 존중받아야 마땅한 존재인걸요.",
            "너무 아파하지마요, 겉에 상처도 천천히 아물고 치유되듯이,\n당신의 마음도 천천히 치유될거에요.",
            "누구든 상처는 있기 마련이지만, 오늘 이후로 누군가 이 일을 기억하냐고 묻는다면, \n신경쓰지 않는다고 당당히 말할 수 있는 사람이 되길."};
    private Context context = null;
    private Activity activity = null;

    public DragDropOnDragListener(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
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

                    TextView tv = activity.findViewById(R.id.thx);
                    Animation animation = AnimationUtils.loadAnimation(activity, R.anim.visible_effects);

                    int rand = (int)(Math.random() * str_healing.length);
                    tv.setText(str_healing[rand]);
                    tv.setAnimation(animation);
                    tv.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                           activity.finish();
                        }
                    }, 5000);// 0.5초 정도 딜레이를 준 후 시작

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