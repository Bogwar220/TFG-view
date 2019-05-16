package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.MainActivity;
import com.example.tfg.act.Util.Util;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.tfg.act.Util.Constantes.server;


public class Creacion extends AppCompatActivity implements View.OnClickListener {

    private String url = server + "/user";

    private int sexo = 0;

    private int dia = 0;
    private int mes = 0;
    private int anio = 0;

    private LocalDate fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion);

        botones();
    }

    private void botones(){
        Button btCrear = findViewById(R.id.btCrear);
        btCrear.setOnClickListener(this);

        Button btAtras = findViewById(R.id.btAtrasCreacion);
        btAtras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btCrear:

                //---POST---
                EditText etUsername = findViewById(R.id.etUser);
                EditText etPass = findViewById(R.id.etPass);
                EditText etConfPass = findViewById(R.id.etConfPass);
                EditText etPeso = findViewById(R.id.etPeso);
                EditText etAltura = findViewById(R.id.etAltura);
                EditText etDia = findViewById(R.id.etDia);
                EditText etMes = findViewById(R.id.etMes);
                EditText etAnio = findViewById(R.id.etAnio);

                RadioButton rbMujer = findViewById(R.id.rbMujer);
                RadioButton rbHombre = findViewById(R.id.rbHombre);
                rbMujer.setOnClickListener(this);
                rbHombre.setOnClickListener(this);

                //condiciones username
                if (etUsername.getText().toString().equals("")) {
                    Toast.makeText(this, "Tienes que introducir un nombre de username", Toast.LENGTH_SHORT).show();
                    return;
                }

                //condiciones password
                if(etPass.getText().toString().length() < 3){
                    Toast.makeText(this, "La contraseña debe de tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                //condiciones peso
                if(etPeso.getText().toString().equals("")){
                    Toast.makeText(this, "Tienes que introducir tu peso", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(etPeso.getText().toString()) < 20){
                    Toast.makeText(this, "Peso pana!!! Mentirosooo", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(etPeso.getText().toString())> 500){
                    Toast.makeText(this, "Pero que eres? Un elefante?", Toast.LENGTH_SHORT).show();
                    return;
                }

                //condiciones altura
                if(etAltura.getText().toString().equals("")) {
                    Toast.makeText(this, "Tienes que introducir altura", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(etAltura.getText().toString()) < 100){
                    Toast.makeText(this, "Venga AntMan deja de mentir!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(etAltura.getText().toString())> 260){
                    Toast.makeText(this, "Y que tal el tiempo por alli arriba?", Toast.LENGTH_SHORT).show();
                    return;
                }

                //condiciones fecha
                if(etDia.getText().toString().equals("")) {
                    Toast.makeText(this, "Cual es tu dia de nacimiento?", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(etDia.getText().toString())< 0 || Integer.parseInt(etDia.getText().toString()) > 31){
                    Toast.makeText(this, "El dia no existe en el planeta Tierra", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etMes.getText().toString().equals("")) {
                    Toast.makeText(this, "En que mes naciste?", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(etMes.getText().toString())< 0 || Integer.parseInt(etMes.getText().toString())> 12){
                    Toast.makeText(this, "El mes no existe en el planeta Tierra", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etAnio.getText().toString().equals("")) {
                    Toast.makeText(this, "Cuantos años tienes?", Toast.LENGTH_SHORT).show();
                    return;
                }

                // no cambia el sexo
                if(rbHombre.isChecked())
                    sexo = 1;

                if(etPass.getText().toString().equals(etConfPass.getText().toString())) {

                    Calendar now = Calendar.getInstance();
                    Calendar dob = Calendar.getInstance();

                    String fecha = etDia.getText().toString() + "/" +
                            etMes.getText().toString() + "/" +
                            etAnio.getText().toString();

                    try {
                        Date date =  new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
                        dob.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dob.after(now)) {
                        throw new IllegalArgumentException("Can't be born in the future");
                    }
                    int year1 = now.get(Calendar.YEAR);
                    int year2 = dob.get(Calendar.YEAR);
                    int age = year1 - year2;
                    int month1 = now.get(Calendar.MONTH);
                    int month2 = dob.get(Calendar.MONTH);
                    if (month2 > month1) {
                        age--;
                    } else if (month1 == month2) {
                        int day1 = now.get(Calendar.DAY_OF_MONTH);
                        int day2 = dob.get(Calendar.DAY_OF_MONTH);
                        if (day2 > day1) {
                            age--;
                        }
                    }

                    if(age < 18){
                        Toast.makeText(this, "Tienes que ser mayor de edad", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(age > 110){
                        Toast.makeText(this, "Mejor descansas viejo con tu edad de "+String.valueOf(age), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String pass = etPass.getText().toString();
                    pass = Util.passConverter(pass);

                    Map<String, String> params = new HashMap<>();
                    params.put("username", etUsername.getText().toString());
                    params.put("password", pass);
                    params.put("peso" , etPeso.getText().toString());
                    params.put("altura", etAltura.getText().toString());
                    params.put("edad", String.valueOf(age));
                    params.put("sexo", String.valueOf(sexo));

                    JSONObject usuario = new JSONObject(params);

                    RequestQueue requestQueueAdd = Volley.newRequestQueue(this);
                    JsonObjectRequest objectRequestAdd = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            usuario,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("Rest Response", response.toString());
                                    Toast.makeText(Creacion.this, "User creado", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Creacion.this,MainActivity.class));
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Rest Response", error.toString());
                                    Toast.makeText(Creacion.this, "Server problem", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    requestQueueAdd.add(objectRequestAdd);

                }else {

                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btAtrasCreacion:

                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }
}
