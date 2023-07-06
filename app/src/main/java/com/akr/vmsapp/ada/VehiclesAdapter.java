package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.akr.vmsapp.mod.Vehicle;
import com.example.vmsapp.R;

import java.util.List;

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.MyViewHolder> {

    private List<Vehicle> data;
    private Context ctx;

    public VehiclesAdapter(List<Vehicle> data, Context ctx) {
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
        Vehicle obj = data.get(pos);
        holder.tvNem.setText(obj.getNumberPlate());
        holder.tvStt.setText(String.format("Status: %s ): Coming Soon...", obj.getPhotoUrl()));
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
