package com.luckly.eraser.ui.fragment.home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luckly.eraser.R;
import com.luckly.eraser.data.slider.SliderItem;
import com.luckly.eraser.data.write.Write;
import com.luckly.eraser.ui.adapter.list.EraserAdapter;
import com.luckly.eraser.ui.adapter.sliderview.SlideAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Animation animation;
    String key = "KEY-SAVE";
    private ImageView img_music;
    EraserAdapter adapter;
    SlideAdapter adapters;
    List<Write> data = new ArrayList<>();
    private SliderView sliderView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
        img_music = view.findViewById(R.id.img_cd);
        img_music.setAnimation(animation);
        sliderView = view.findViewById(R.id.imageSlider);

        adapters = new SlideAdapter(getContext());
        sliderView.setSliderAdapter(adapters);

        sliderView.startAutoCycle();
        sliderView.setIndicatorEnabled(false);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }
    public void addNewItem(View view) {
        for(int i = 0; i < 10; i ++){
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item Added Manually " + i);
            sliderItem.setImageUrl(R.drawable.test);
            adapters.addItem(sliderItem);
        }
    }
    public List<Write> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Write>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : null;
    }
}