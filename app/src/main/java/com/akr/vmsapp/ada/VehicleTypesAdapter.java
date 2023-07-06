package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.akr.vmsapp.mod.VehicleType;
import com.example.vmsapp.R;

import java.util.List;

public class VehicleTypesAdapter extends RecyclerView.Adapter<VehicleTypesAdapter.MyViewHolder> {

    private List<VehicleType> data;
    private Context ctx;

    public VehicleTypesAdapter(List<VehicleType> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_garage, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        VehicleType obj = data.get(pos);
        holder.tvNem.setText(obj.getName());
        holder.tvStt.setText(String.format("Status: %s ): Coming Soon...", obj.getDescription()));
        holder.tvTim.setText(obj.getAddDate());
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNem, tvFon, tvEml, tvPla, tvTim, tvStt;

        public MyViewHolder(View v) {
            super(v);
            tvNem = (TextView) v.findViewById(R.id.tv_name);
            tvFon = (TextView) v.findViewById(R.id.tv_phone);
            tvEml = (TextView) v.findViewById(R.id.tv_email);
            tvPla = (TextView) v.findViewById(R.id.tv_place);
            tvTim = (TextView) v.findViewById(R.id.tv_time);
            tvStt = (TextView) v.findViewById(R.id.tv_status);
        }
    }
}
