package com.luckly.eraser.ui.fragment.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luckly.eraser.R;
import com.luckly.eraser.data.setting.Setting;
import com.luckly.eraser.ui.adapter.list.deleted.EraserAdapter;
import com.luckly.eraser.ui.adapter.list.setting.SettingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SettingFragment extends Fragment {
    SettingAdapter adapter;
    List<Setting> list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);


        RecyclerView recyclerView = root.findViewById(R.id.recyler);

        list = new ArrayList<Setting>();
        list.add(new Setting("배경 설정", "배경을 설정 할 수 있는 설정이에요."));

        adapter = new SettingAdapter(list, getContext());

        LinearLayoutManager manger = new LinearLayoutManager(requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manger);

        return root;
    }
}