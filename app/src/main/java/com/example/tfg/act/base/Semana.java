package com.example.tfg.act.base;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Semana extends JSONObject implements Parcelable {

    private int id;
    private String nombre;

    public Semana(){

    }

    public Semana(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    protected Semana(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
    }

    public static final Creator<Semana> CREATOR = new Creator<Semana>() {
        @Override
        public Semana createFromParcel(Parcel in) {
            return new Semana(in);
        }

        @Override
        public Semana[] newArray(int size) {
            return new Semana[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
    }
}
