package com.luckly.eraser.ui.dragndrop;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.luckly.eraser.R;
import com.luckly.eraser.ui.dragndrop.listener.DragDropOnDragListener;
import com.luckly.eraser.ui.dragndrop.listener.DragDropOnTouchListener;


public class DragDropActivity extends AppCompatActivity {

    private Intent i = getIntent();

    private ImageView imageView = null;

    private TextView textView_date;

    private ConstraintLayout BeforePaper = null;

    private ConstraintLayout ConstraintLayoutTop= null;

    private ConstraintLayout ConstraintLayoutBottom = null;

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);

        // Init all controls.
        initControls();
    }

    private void initControls()
    {
        SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundID = soundPool.load(getApplicationContext(), R.raw.sound_paper, 1);

        textView_date = findViewById(R.id.date);
        if(getIntent() != null) {
            textView_date.setText(getIntent().getStringExtra("Date_string"));
        }
        if(context == null)
        {
            context = getApplicationContext();
        }

        if(imageView == null)
        {
            imageView = (ImageView) findViewById(R.id.drag_drop_image);
            BeforePaper = (ConstraintLayout)findViewById(R.id.before_drop);

            BeforePaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BeforePaper.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    soundPool.play(soundID,1f,1f,0,0,1f);
                }
            });
            imageView.setTag("ImageView");

            // Set on touch listener to source dragged view.
            imageView.setOnTouchListener(new DragDropOnTouchListener());

        }

        if(ConstraintLayoutTop == null)
        {
            ConstraintLayoutTop = (ConstraintLayout)findViewById(R.id.drag_drop_left_layout);

            // Set on drag listener to target dropped view.
            ConstraintLayoutTop.setOnDragListener(new DragDropOnDragListener(context, this));
        }

        if(ConstraintLayoutBottom == null)
        {
            ConstraintLayoutBottom = (ConstraintLayout) findViewById(R.id.drag_drop_right_layout);

            // Set on drag listener to target dropped view.
            ConstraintLayoutBottom.setOnDragListener(new DragDropOnDragListener(context, this));
        }
    }
}