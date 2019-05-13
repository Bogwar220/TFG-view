package com.example.tfg.act.base;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class SemanaUser extends JSONObject implements Parcelable {
    private int id;
    private Semana semana;
    private User user;

    public SemanaUser(){

    }

    public SemanaUser(int id, Semana semana, User user) {
        this.id = id;
        this.semana = semana;
        this.user = user;
    }

    protected SemanaUser(Parcel in) {
        id = in.readInt();
        semana = in.readParcelable(Semana.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<SemanaUser> CREATOR = new Creator<SemanaUser>() {
        @Override
        public SemanaUser createFromParcel(Parcel in) {
            return new SemanaUser(in);
        }

        @Override
        public SemanaUser[] newArray(int size) {
            return new SemanaUser[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Semana getSemana() {
        return semana;
    }

    public void setSemana(Semana semana) {
        this.semana = semana;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(semana, flags);
        dest.writeParcelable(user, flags);
    }
}
