package com.luckly.eraser.ui.fragment.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luckly.eraser.R;
import com.luckly.eraser.data.slider.SliderItem;
import com.luckly.eraser.data.write.Write;
import com.luckly.eraser.ui.adapter.list.deleted.EraserAdapter;
import com.luckly.eraser.ui.adapter.sliderview.SlideAdapter;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    Animation animation;
    String key = "KEY-SAVE";
    String key_time = "KEY-TIME";
    private ImageView img_music;
    EraserAdapter adapter;
    SlideAdapter adapters;
    private TextView tv;
    List<Write> data = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SliderView sliderView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getContext().getSharedPreferences(key_time, Context.MODE_PRIVATE);


        tv = view.findViewById(R.id.time);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
        img_music = view.findViewById(R.id.img_cd);
        img_music.setAnimation(animation);
        sliderView = view.findViewById(R.id.imageSlider);

        times();

        adapters = new SlideAdapter(getContext());
        sliderView.setSliderAdapter(adapters);

        sliderView.setSliderAnimationDuration(2500);
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.startAutoCycle();
        sliderView.setIndicatorEnabled(false);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);

        addNewItem(sliderView);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        if(getArrayList(key) != null){
            adapter = new EraserAdapter(getArrayList(key), getContext());
        }else{
            adapter = new EraserAdapter(data, getContext());
        }
        LinearLayoutManager manger = new LinearLayoutManager(requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manger);
    }

    public void times(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                tv.setText("당신의 시간\n\n"+new SimpleDateFormat("yyyy.MM.dd / HH : mm : ss").format(new Date(System.currentTimeMillis()))+"\n\n 이곳에서는 편히 있길 바라.");
                times();
            }
        }, 1000);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }
    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setImageUrl(R.drawable.bg_1);
        adapters.addItem(sliderItem);

        SliderItem sliderItem2 = new SliderItem();
        sliderItem2.setImageUrl(R.drawable.bg_2);
        adapters.addItem(sliderItem2);

        SliderItem sliderItem3 = new SliderItem();
        sliderItem3.setImageUrl(R.drawable.bg_3);
        adapters.addItem(sliderItem3);

    }
    public List<Write> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Write>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : null;
    }
}