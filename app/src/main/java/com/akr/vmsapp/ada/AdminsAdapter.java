package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akr.vmsapp.mod.Admin;
import com.example.vmsapp.R;

import java.util.List;

public class AdminsAdapter extends ArrayAdapter<Admin> {

    private List<Admin> data;
    private Context ctx;
    private int resource;

    public AdminsAdapter(Context context, int resource, List<Admin> data) {
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

        Admin obj = data.get(pos);
        tvNem.setText(String.format("%s %s %s", obj.getFirstname(), obj.getLastname(), !obj.getSurname().isEmpty() || obj.getSurname() != null ? obj.getSurname() : ""));
        tv1.setText(String.format("%s (%s)", obj.getUsername(), obj.getIdNumber()));
        tv2.setText(String.format("%s (%s)", obj.getPhone(), obj.getCountryCode()));
        tv3.setText(obj.getEmail());
        tv4.setText(obj.getIdNumber());
        tv5.setText(obj.getAdmId());

        return view;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
