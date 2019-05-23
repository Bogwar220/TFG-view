package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.base.Dia;
import com.example.tfg.act.base.Semana;
import com.example.tfg.act.base.SemanaUser;
import com.example.tfg.act.base.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.tfg.act.Util.Constantes.server;

public class AddEjercicio extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private SemanaUser semUser;
    private Dia dia;
    private Semana semana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ejercicio);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        semUser = intent.getParcelableExtra("semUser");
        dia = intent.getParcelableExtra("dia");
        semana = intent.getParcelableExtra("semana");

        botones();
    }

    private void botones(){
        Button btGuardarEjercicio = findViewById(R.id.btGuardar);
        Button btAtrasEjercicio = findViewById(R.id.btAtrasEjercicio);

        btGuardarEjercicio.setOnClickListener(this);
        btAtrasEjercicio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btGuardar:
                EditText etNombreEjercicio = findViewById(R.id.etNombreEjercicio);
                EditText etDescripcionEjercicio = findViewById(R.id.etDescripcionEjercicio);

                Map<String, String> paramsEjercicio = new HashMap<>();
                paramsEjercicio.put("nombre", String.valueOf(etNombreEjercicio.getText()));
                paramsEjercicio.put("descripcion", String.valueOf(etDescripcionEjercicio.getText()));
                JSONObject jsonEjercicio = new JSONObject(paramsEjercicio);

                String urlEjercicio = server + "/ej";

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        urlEjercicio,
                        jsonEjercicio,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("postEjercicio", response.toString());

                                Intent intent = new Intent(AddEjercicio.this, AddDia.class);
                                intent.putExtra("user", user);
                                intent.putExtra("semUser", semUser);
                                intent.putExtra("dia", dia);
                                intent.putExtra("semana", semana);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("postEjercicio", error.toString());
                            }
                        }
                );
                requestQueue.add(objectRequest);
                break;
            case R.id.btAtrasEjercicio:
                Intent intent = new Intent(AddEjercicio.this, AddDia.class);
                intent.putExtra("user", user);
                intent.putExtra("semUser", semUser);
                intent.putExtra("dia", dia);
                intent.putExtra("semana", semana);
                break;
        }
    }
}
