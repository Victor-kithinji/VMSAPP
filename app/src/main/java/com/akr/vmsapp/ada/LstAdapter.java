package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akr.vmsapp.mod.Country;
import com.example.vmsapp.R;

import java.util.List;

public class LstAdapter extends ArrayAdapter<Country> {

    private List<Country> data;
    private Context ctx;
    private int resource;

    public LstAdapter(Context context, int resource, List<Country> data) {
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
        /*TextView tv1 = view.findViewById(R.id.tv_1);
        TextView tv2 = view.findViewById(R.id.tv_2);
        TextView tv3 = view.findViewById(R.id.tv_3);
        TextView tv4 = view.findViewById(R.id.tv_4);
        TextView tv5 = view.findViewById(R.id.tv_5);*/

        Country obj = data.get(pos);
        tvNem.setText(obj.getName());
        /*tv1.setText(obj.getName());
        tv2.setText(obj.getName());
        tv3.setText(obj.getName());
        tv4.setText(obj.getName());
        tv5.setText(obj.getName());*/

        return view;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
