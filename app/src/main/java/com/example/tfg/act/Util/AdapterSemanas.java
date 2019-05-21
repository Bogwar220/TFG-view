package com.example.tfg.act.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.act.base.Semana;
import com.example.tfg.act.base.SemanaUser;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class AdapterSemanas extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    String[] nombres;
    ArrayList<SemanaUser> semUsers;

    public AdapterSemanas(Context context, String[] nombres, ArrayList<SemanaUser> semUsers){
        this.context = context;
        this.nombres = nombres;
        this.semUsers = semUsers;

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return nombres.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.rutinas, null);
        TextView tvNombre = view.findViewById(R.id.tvNombreRutina);
        tvNombre.setText(nombres[i]);
        return view;
    }
}
