package com.example.tfg.act.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.act.base.Ejercicio;

import java.util.ArrayList;

public class AdapterAddDia extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    String[] nombres;
    ArrayList<Ejercicio> ejercicios;
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
        TextView tvNombreRutina = view.findViewById(R.id.tvNombreRutina);
        tvNombreRutina.setText(nombres [i]);
        return view;
    }
}
