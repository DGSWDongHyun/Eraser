package com.luckly.eraser.ui.fragment.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luckly.eraser.R;
import com.luckly.eraser.data.setting.Setting;
import com.luckly.eraser.ui.activity.main.MainActivity;
import com.luckly.eraser.ui.adapter.list.deleted.EraserAdapter;
import com.luckly.eraser.ui.adapter.list.setting.SettingAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SettingFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    SettingAdapter adapter;
    List<Setting> list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);


        RecyclerView recyclerView = root.findViewById(R.id.recyler);

        list = new ArrayList<Setting>();
        list.add(new Setting("배경 설정", "배경을 설정 할 수 있어요."));
        list.add(new Setting("개발자 보기", "개발자를 볼 수 있어요."));
        list.add(new Setting("설정 초기화", "설정을 초기화 할 수 있어요."));

        adapter = new SettingAdapter(list, getContext(), this, getActivity());

        LinearLayoutManager manger = new LinearLayoutManager(requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manger);

        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            try {
                InputStream in = getContext().getContentResolver().openInputStream(data.getData());

                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();

                View settingDetailView = View.inflate(getContext(), R.layout.dialog_setting, null);
                initializeSetting(settingDetailView, img);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("배경 설정하기")
                        .setCancelable(false)
                        .setView(settingDetailView)
                        .setPositiveButton("확인",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((MainActivity)getActivity()).setBackground(new BitmapDrawable(img), img);
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception e) {

            }
        }
    }
    private void initializeSetting(View settingDetailView, Bitmap bitmap){
        ImageView imageView = settingDetailView.findViewById(R.id.img_bg);

        imageView.setImageDrawable(new BitmapDrawable(bitmap));

    }
}