package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.akr.vmsapp.mod.Owner;
import com.example.vmsapp.R;

import java.util.List;

public class OwnersAdapter extends RecyclerView.Adapter<OwnersAdapter.MyViewHolder> {

    private List<Owner> data;
    private Context ctx;

    public OwnersAdapter(List<Owner> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_owner, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        Owner obj = data.get(pos);
        holder.tvNem.setText(String.format("%s %s %s", obj.getFirstname(), obj.getLastname(), obj.getSurname()));
        holder.tvFon.setText(String.format("Phone Num: %s", obj.getPhone()));
        holder.tvEml.setText(String.format("Email Add: %s", obj.getEmail()));
        holder.tvPla.setText(String.format("Location: %s (%s, %s)", obj.getLocName(), obj.getLat(), obj.getLng()));
        holder.tvStt.setText(String.format("Status: %s ): Coming Soon...", obj.getStatus()));
        holder.tvTim.setText(String.format("%s", obj.getRegDate()));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNem, tvFon, tvEml, tvPla, tvTim, tvStt;

        public MyViewHolder(View v) {
            super(v);
            tvNem = v.findViewById(R.id.tv_name);
            tvFon = v.findViewById(R.id.tv_phone);
            tvEml = v.findViewById(R.id.tv_email);
            tvPla = v.findViewById(R.id.tv_place);
            tvTim = v.findViewById(R.id.tv_time);
            tvStt = v.findViewById(R.id.tv_status);
        }
    }
}
