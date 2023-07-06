package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.akr.vmsapp.mod.Maker;
import com.example.vmsapp.R;

import java.util.List;

public class MakersAdapter extends RecyclerView.Adapter<MakersAdapter.MyViewHolder> {

    private List<Maker> data;
    private Context ctx;

    public MakersAdapter(List<Maker> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_maker, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        Maker obj = data.get(pos);
        holder.tvNem.setText(String.format("%s %s %s", obj.getFirstname(), obj.getLastname(), obj.getSurname()));
        holder.tvFon.setText(String.format("Phone Number: %s", obj.getPhone()));
        holder.tvEml.setText(String.format("Email Address: %s", obj.getEmail()));
        holder.tvPla.setText(String.format("Location: %s (%s, %s)", obj.getLocName(), obj.getLat(), obj.getLng()));
        holder.tvStt.setText(String.format("Status: %s", obj.getStatus()));
        holder.tvTim.setText(String.format("%s", obj.getRegDate()));
        holder.tvDis.setText(String.format("Distance: %s Km", obj.getDistance()));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNem, tvFon, tvEml, tvPla, tvTim, tvStt, tvDis;

        public MyViewHolder(View v) {
            super(v);
            tvNem = v.findViewById(R.id.tv_name);
            tvFon = v.findViewById(R.id.tv_phone);
            tvEml = v.findViewById(R.id.tv_email);
            tvPla = v.findViewById(R.id.tv_place);
            tvTim = v.findViewById(R.id.tv_time);
            tvStt = v.findViewById(R.id.tv_status);
            tvDis = v.findViewById(R.id.tv_distance);
        }
    }
}
