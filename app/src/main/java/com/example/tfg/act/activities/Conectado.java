package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.MainActivity;
import com.example.tfg.act.base.User;

import org.json.JSONArray;

import static com.example.tfg.act.Util.Constantes.server;

public class Conectado extends AppCompatActivity implements View.OnClickListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectado);

        Intent intent = getIntent();

        user = intent.getParcelableExtra("user");

        TextView tvUser = findViewById(R.id.tvUser);
        tvUser.setText(user.getUsername());

        //get semanas para ver si hay algo añadido para este usuario o no
        String urlSemana = server + "/semana";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlSemana,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e ("Rest Response 1", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e ("Rest Response", error.toString());
                    }
                }
        );

        requestQueue.add(arrayRequest);

        botones();
    }

    private void botones(){
        Button btConf = findViewById(R.id.btConf);
        btConf.setOnClickListener(this);

        Button btEmpezar = findViewById(R.id.btEmpezar);
        btEmpezar.setOnClickListener(this);

        Button btDesconect = findViewById(R.id.btDesconnect);
        btDesconect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btConf:
                Intent intent = new Intent(Conectado.this, ConfigUser.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;

            case R.id.btEmpezar:
                Intent intent1 = new Intent(Conectado.this, Calendar.class);
                intent1.putExtra("user", user);
                startActivity(intent1);
                break;

            case R.id.btDesconnect:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
