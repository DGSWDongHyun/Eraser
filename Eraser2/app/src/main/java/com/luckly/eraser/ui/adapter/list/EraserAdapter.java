package com.luckly.eraser.ui.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luckly.eraser.R;
import com.luckly.eraser.data.write.Write;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EraserAdapter extends RecyclerView.Adapter<EraserAdapter.EraserViewHolder> {
    List<Write> write_data;
    Context context;
    public EraserAdapter(List<Write> write_data, Context context){
            this.write_data = write_data;
            this.context = context;
    }


    @NonNull
    @Override
    public EraserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment, parent, false);
        return new EraserViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull EraserViewHolder holder, int position) {

        Write write = write_data.get(position);

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        try {
            Date was = transFormat.parse(write.getDate());
            Date now = new Date(System.currentTimeMillis());
            if(now.before(was)){
                write.setTitle("당신이 지워버린 오늘의 고민");
            }else {
                write.setTitle("당신이 지워버린 과거의 고민");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.textView_title.setText(write.getTitle());
        holder.textView_content.setText(write.getContent());
        holder.textView_date.setText(write.getDate());
    }

    @Override
    public int getItemCount() {
        return write_data != null ? write_data.size() : 0 ;
    }

    class EraserViewHolder extends RecyclerView.ViewHolder{
        TextView textView_title;
        TextView textView_date;
        TextView textView_content;

        public EraserViewHolder(@NonNull View itemView){
            super(itemView);

            textView_title = itemView.findViewById(R.id.textView_title);
            textView_content = itemView.findViewById(R.id.textView_content);
            textView_date = itemView.findViewById(R.id.textView_date);

        }
    }
}
