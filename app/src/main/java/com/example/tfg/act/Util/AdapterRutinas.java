package com.example.tfg.act.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfg.R;

public class AdapterRutinas extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    String[] datos;

    public AdapterRutinas(Context context, String[] datos){
        this.context = context;
        this.datos = datos;

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.rutinas, null);
        TextView tvNombreRutina = view.findViewById(R.id.tvNombreRutina);
        tvNombreRutina.setText(datos[i]);
        return view;
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
