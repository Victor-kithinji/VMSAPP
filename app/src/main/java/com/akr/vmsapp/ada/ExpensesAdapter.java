package com.akr.vmsapp.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akr.vmsapp.mod.Expense;
import com.example.vmsapp.R;

import java.util.List;

public class ExpensesAdapter extends ArrayAdapter<Expense> {

    private List<Expense> data;
    private Context ctx;
    private int resource;

    public ExpensesAdapter(Context context, int resource, List<Expense> data) {
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

        Expense obj = data.get(pos);
        tvNem.setText(obj.getExpId());
        tv1.setText(String.format("Grg ID: %s", obj.getGrgId()));
        tv2.setText(String.format("Mec ID: %s", obj.getMecId()));
        tv3.setText(String.format("Spp ID: %s", obj.getSppId()));
        tv4.setText(String.format("Desc: %s", obj.getDescription()));
        tv5.setText(String.format("Amt: %s on %s", obj.getAmount(), obj.getAddDate()));

        return view;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
