package com.example.tfg.act.base;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class User extends JSONObject  implements Parcelable {

    private int id;
    private String username;
    private String password;
    private int peso;
    private int altura;
    private int edad;
    private int sexo;

    public User(){

    }

    protected User(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();
        peso = in.readInt();
        altura = in.readInt();
        edad = in.readInt();
        sexo = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) { this.altura = altura; }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeInt(peso);
        dest.writeInt(altura);
        dest.writeInt(edad);
        dest.writeInt(sexo);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", peso=" + peso +
                ", altura=" + altura +
                ", edad=" + edad +
                ", sexo=" + sexo +
                '}';
    }
}
