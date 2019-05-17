package com.example.tfg.act.base;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Dia extends JSONObject implements Parcelable {
    private int id;
    private String nombre;
    private Semana semana;

    public Dia(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Semana getSemana() {
        return semana;
    }

    public void setSemana(Semana semana) {
        this.semana = semana;
    }

    public Dia(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        semana = in.readParcelable(Semana.class.getClassLoader());
    }

    public static final Creator<Dia> CREATOR = new Creator<Dia>() {
        @Override
        public Dia createFromParcel(Parcel in) {
            return new Dia(in);
        }

        @Override
        public Dia[] newArray(int size) {
            return new Dia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeParcelable(semana, flags);
    }
}
