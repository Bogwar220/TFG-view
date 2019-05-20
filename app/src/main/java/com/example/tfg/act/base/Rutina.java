package com.example.tfg.act.base;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Rutina extends JSONObject implements Parcelable {
    private int id;
    private int repeticiones;
    private Ejercicio ejercicio;
    private Dia dia;

    public Rutina(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    protected Rutina(Parcel in) {
        id = in.readInt();
        repeticiones = in.readInt();
        ejercicio = in.readParcelable(Ejercicio.class.getClassLoader());
        dia = in.readParcelable(Dia.class.getClassLoader());
    }

    public static final Creator<Rutina> CREATOR = new Creator<Rutina>() {
        @Override
        public Rutina createFromParcel(Parcel in) {
            return new Rutina(in);
        }

        @Override
        public Rutina[] newArray(int size) {
            return new Rutina[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(repeticiones);
        dest.writeParcelable(ejercicio, flags);
        dest.writeParcelable(dia, flags);
    }
}
