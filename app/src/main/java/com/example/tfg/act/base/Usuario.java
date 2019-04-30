package com.example.tfg.act.base;

import org.json.JSONObject;

public class Usuario extends JSONObject {

    private String nombre;
    private String password;

    public Usuario(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String pass) {
        this.password = pass;
    }
}
