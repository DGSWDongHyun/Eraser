package com.luckly.eraser.ui.adapter.list.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.luckly.eraser.R;
import com.luckly.eraser.data.setting.Setting;
import com.luckly.eraser.data.write.Write;
import com.luckly.eraser.ui.activity.main.MainActivity;
import com.luckly.eraser.ui.adapter.list.deleted.EraserAdapter;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

    private static final int REQUEST_CODE = 1;
    List<Setting> setting;
    Context context;
    Fragment fragment;

    public SettingAdapter(List<Setting> setting, Context context, Fragment fragment){
        this.setting = setting;
        this.context = context;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public SettingAdapter.SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item, parent, false);
        return new SettingAdapter.SettingViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.SettingViewHolder holder, int position) {

        Setting set = setting.get(position);


        holder.textView_title.setText(set.getTitle());
        holder.textView_description.setText((set.getDescription()));


    }

    @Override
    public int getItemCount() {
        return setting != null ? setting.size() : 0 ;
    }

    class SettingViewHolder extends RecyclerView.ViewHolder{
        TextView textView_title;
        TextView textView_description;

        public SettingViewHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        switch (setting.get(pos).getTitle()){
                            case "배경 설정":
                                View settingDetailView = View.inflate(context, R.layout.dialog_setting, null);
                                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                        .setTitle("배경 설정하기")
                                        .setCancelable(false)
                                        .setView(settingDetailView)
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                initializeSetting(settingDetailView, alert);
                                alert.show();
                                break;
                            case "개발자 보기":
                                View aboutDeveloper = View.inflate(context, R.layout.setting_dev, null);
                                AlertDialog.Builder builder_dev = new AlertDialog.Builder(context)
                                        .setTitle("개발자 보기")
                                        .setCancelable(false)
                                        .setView(aboutDeveloper)
                                        .setPositiveButton("확인",new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        });
                                AlertDialog alert_dev = builder_dev.create();
                                alert_dev.show();
                                break;
                        }
                    }
                }
            });

            textView_title = itemView.findViewById(R.id.title);
            textView_description = itemView.findViewById(R.id.description);

        }
    }
    private void initializeSetting(View settingDetailView, AlertDialog alert){
        Button button = settingDetailView.findViewById(R.id.Select_Button);
        button.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            fragment.startActivityForResult(intent, REQUEST_CODE);

            alert.dismiss();
        });
    }
}