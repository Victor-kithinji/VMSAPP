package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akr.vmsapp.mod.Model;
import com.example.vmsapp.R;

import java.util.List;

public class ModelsAdapter extends ArrayAdapter<Model> {

    private List<Model> data;
    private Context ctx;
    private int resource;

    public ModelsAdapter(Context context, int resource, List<Model> data) {
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

        Model obj = data.get(pos);
        tvNem.setText(obj.getName());

        return view;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
