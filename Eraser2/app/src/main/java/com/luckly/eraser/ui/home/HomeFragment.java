package com.luckly.eraser.ui.home;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luckly.eraser.R;
import com.luckly.eraser.data.Write;
import com.luckly.eraser.ui.list.EraserAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    Animation animation;
    String key = "KEY-SAVE";
    private ImageView img_music;
    EraserAdapter adapter;
    List<Write> data = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
        img_music = view.findViewById(R.id.img_cd);
        img_music.setAnimation(animation);

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
    public List<Write> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Write>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : null;
    }
}