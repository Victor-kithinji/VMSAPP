package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akr.vmsapp.mod.Country;
import com.example.vmsapp.R;

import java.util.List;

public class CountriesAdapter extends ArrayAdapter<Country> {

    private List<Country> data;
    private Context ctx;
    private int resource;
    private OnItemClickListener oicl;

    public CountriesAdapter(Context context, int resource, List<Country> data) {
        super(context, resource, data);
        this.ctx = context;
        this.resource = resource;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setListener(OnItemClickListener listener) {
        oicl = listener;
    }

    @NonNull
    @Override
    public View getView(final int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(ctx);

        View view = li.inflate(resource, null, false);
        TextView tvNem = view.findViewById(R.id.tv_name);
        TextView tvCod = view.findViewById(R.id.tv_country_code);

        Country hero = data.get(pos);
        tvNem.setText(hero.getName());
        tvCod.setText(hero.getPhonePrefix());

        view.setOnClickListener(view1 -> {
            if (oicl != null) {
                if (pos != ListView.INVALID_POSITION) {
                    oicl.onItemClick(pos);
                }
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
