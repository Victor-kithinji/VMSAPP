package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.akr.vmsapp.mod.Garage;
import com.example.vmsapp.R;

import java.util.List;

public class GaragesAdapter extends RecyclerView.Adapter<GaragesAdapter.MyViewHolder> {

    private List<Garage> data;
    private Context ctx;
    private OnGarage ear;

    public interface OnGarage {
        public void onCol(String fon);

        public void onMsg(String eml);

        public void onGml(String lat, String lng, String nem);
    }

    public void setOnGarageEar(OnGarage ear) {
        this.ear = ear;
    }

    public GaragesAdapter(List<Garage> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_garage, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        Garage obj = data.get(pos);
        // String t2 = (obj.getTel2() != null) ? obj.getTel2() : "";

        holder.tvNem.setText(obj.getName());
        holder.tvFon.setText(String.format("Phone: %s, %s, %s", obj.getTel1(), obj.getTel2(), obj.getTel3()));
        holder.tvEml.setText(String.format("Email: %s, %s", obj.getEmail1(), obj.getEmail2()));
        holder.tvPla.setText(String.format("Location: %s (%s, %s)", obj.getLocName(), obj.getLat(), obj.getLng()));
        holder.tvStt.setText(String.format("Status: %s ): Coming Soon...", obj.getStatus()));
        holder.tvTim.setText(String.format("%s", obj.getAddDate()));

        holder.tvCol.setOnClickListener(v -> ear.onCol(obj.getTel1()));
        holder.tvMsg.setOnClickListener(v -> ear.onMsg(obj.getEmail1()));
        holder.tvGml.setOnClickListener(v -> ear.onGml(obj.getLat(), obj.getLng(), obj.getLocName()));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNem, tvFon, tvEml, tvPla, tvTim, tvStt, tvCol, tvMsg, tvGml;

        public MyViewHolder(View v) {
            super(v);
            tvNem = v.findViewById(R.id.tv_name);
            tvFon = v.findViewById(R.id.tv_phone);
            tvEml = v.findViewById(R.id.tv_email);
            tvPla = v.findViewById(R.id.tv_place);
            tvTim = v.findViewById(R.id.tv_time);
            tvStt = v.findViewById(R.id.tv_status);
            tvCol = v.findViewById(R.id.tv_col);
            tvMsg = v.findViewById(R.id.tv_msg);
            tvGml = v.findViewById(R.id.tv_gml);
        }
    }
}
