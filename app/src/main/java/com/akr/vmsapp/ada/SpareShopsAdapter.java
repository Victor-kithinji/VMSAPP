package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akr.vmsapp.mod.SpareShop;
import com.example.vmsapp.R;

import java.util.List;

public class SpareShopsAdapter extends ArrayAdapter<SpareShop> {

    private List<SpareShop> data;
    private Context ctx;
    private int resource;

    public SpareShopsAdapter(Context context, int resource, List<SpareShop> data) {
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

        SpareShop obj = data.get(pos);
        tvNem.setText(String.format("%s \n%s", obj.getName(), obj.getSpsId()));
        tv1.setText(String.format("P.O Box: %s", obj.getPOBox()));
        tv2.setText(String.format("Tel (%s): %s / %s / %s", obj.getCountryCode(), obj.getTel1(), obj.getTel2(), obj.getTel3()));
        tv3.setText(String.format("Email: %s / %s", obj.getEmail1(), obj.getEmail2()));
        tv4.setText(String.format("Loc: %s (%s,%s) \nFax: %s", obj.getLocName(), (obj.getLat() != null) ? obj.getLat() : "0.00", (obj.getLng() != null) ? obj.getLat() : "0.00", obj.getFaxNumber()));
        tv5.setText(String.format("Status: %s as of %s", obj.getStatus(), obj.getAddDate()));

        return view;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
