package com.luckly.eraser.ui.fragment.write;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.luckly.eraser.R;
import com.luckly.eraser.data.write.Write;
import com.luckly.eraser.ui.activity.dragndrop.DragDropActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WriteFragment extends Fragment {
    String key = "KEY-SAVE";
    EditText editText_content;
    Button submitButton;
    ArrayList<Write> data = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText_content = view.findViewById(R.id.editText_content);
        submitButton = view.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(view1 -> {
            data.add(new Write(new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date(System.currentTimeMillis())), "당신이 지워버린 오늘의 고민", editText_content.getText().toString()));
            saveArrayList(data, key);
            Intent intent = new Intent(getContext(), DragDropActivity.class);
            String date_str = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date(System.currentTimeMillis()));
            intent.putExtra("Date_string", date_str);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.visible_effects,R.anim.invisible_effects);
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_write, container, false);

        return root;
    }
    public void saveArrayList(ArrayList<Write> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

}