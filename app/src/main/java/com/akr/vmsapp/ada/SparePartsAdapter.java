package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akr.vmsapp.mod.SparePart;
import com.example.vmsapp.R;

import java.util.List;

public class SparePartsAdapter extends ArrayAdapter<SparePart> {

    private List<SparePart> data;
    private Context ctx;
    private int resource;

    public SparePartsAdapter(Context context, int resource, List<SparePart> data) {
        super(context, resource, data);
        this.ctx = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(final int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(ctx);

        View view = li.inflate(resource, null, false);

        TextView tvNem = view.findViewById(R.id.tv_name);
        TextView tv1 = view.findViewById(R.id.tv_1);
        TextView tv2 = view.findViewById(R.id.tv_2);
        TextView tv3 = view.findViewById(R.id.tv_3);
        TextView tv4 = view.findViewById(R.id.tv_4);
        TextView tv5 = view.findViewById(R.id.tv_5);

        SparePart obj = data.get(pos);
        tvNem.setText(obj.getName());
        tv1.setText(String.format("Spp ID: %s", obj.getSppId()));
        tv2.setText(String.format("Sps ID: %s", obj.getSpsId()));
        tv3.setText(String.format("Mod ID: %s", obj.getModelId()));
        tv4.setText(String.format("Price: %s | Service Cost: %s", obj.getPrice(), obj.getRosCost()));
        tv5.setText(String.format("Add Date: %s", obj.getAddDate()));

        return view;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
