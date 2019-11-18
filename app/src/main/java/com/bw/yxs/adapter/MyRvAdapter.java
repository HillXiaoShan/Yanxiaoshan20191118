package com.bw.yxs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.yxs.R;
import com.bw.yxs.bean.MyBean;

import java.util.List;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.hoder> {
    List<MyBean.ResultBean> list;
    Context context;

    public MyRvAdapter(List<MyBean.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public hoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item, null);
        return new hoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull hoder holder, int position) {
        String title = list.get(position).getTitle();
        holder.tv.setText(title);
        long time = list.get(position).getReleaseTime();
        holder.time.setText((int) time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class hoder extends RecyclerView.ViewHolder{

        TextView tv,time;
        public hoder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            time=itemView.findViewById(R.id.time);
        }
    }
}
