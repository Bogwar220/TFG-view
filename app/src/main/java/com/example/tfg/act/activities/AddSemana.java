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
import com.example.tfg.act.Util.Contadores;
import com.example.tfg.act.base.Semana;
import com.example.tfg.act.base.SemanaUser;
import com.example.tfg.act.base.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.tfg.act.Util.Constantes.server;

public class AddSemana extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private SemanaUser semUser;
    private Semana semana;

    SemanaUser newSemUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semana);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        semUser = intent.getParcelableExtra("semUser");

        botones();
    }

    private void botones(){
        Button btAtras = findViewById(R.id.btAtrasAddSemana);
        Button btNext = findViewById(R.id.btNext);

        btNext.setOnClickListener(this);
        btAtras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btNext:
                EditText etNombre = findViewById(R.id.etNombreSemana);

                String url = server + "/semana";

                //post de semana
                final Map<String, String> paramsSemana = new HashMap<>();
                paramsSemana.put("nombre", String.valueOf(etNombre.getText()));
                JSONObject jsonSemana = new JSONObject(paramsSemana);

                semana = new Semana();
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonSemana,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("RestPostSemana", response.toString());
                            try {
                                int idSemana = response.getInt("id");
                                semana.setId(idSemana);

                                Map<String, String> paramsUser = new HashMap<>();
                                paramsUser.put("id", String.valueOf(user.getId()));
                                JSONObject jsonUser = new JSONObject(paramsUser);

                                Map<String, JSONObject> paramsSemUser = new HashMap<>();
                                paramsSemUser.put("user", jsonUser);
                                paramsSemUser.put("semana", response);
                                JSONObject jsonSemUser = new JSONObject(paramsSemUser);
                                Log.e("jsonSemUser", jsonSemUser.toString());

                                String urlSemUser = server + "/semUser";
                                RequestQueue requestQueue = Volley.newRequestQueue(AddSemana.this);
                                JsonObjectRequest objectRequest2 = new JsonObjectRequest(
                                        Request.Method.POST,
                                        urlSemUser,
                                        jsonSemUser,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    Log.e("semUserPost", response.toString());
                                                    int idNewSemUser = response.getInt("id");
                                                    newSemUser = new SemanaUser();
                                                    newSemUser.setId(idNewSemUser);

                                                    Contadores.dia = 1;

                                                    Intent intent = new Intent(AddSemana.this, AddDia.class);
                                                    intent.putExtra("user", user);
                                                    intent.putExtra("semana", semana);
                                                    intent.putExtra("semUser", newSemUser);
                                                    startActivity(intent);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.e("RestResponse", error.toString());
                                            }
                                        }
                                );
                                requestQueue.add(objectRequest2);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("RestPostSemana", error.toString());
                        }
                    }
                );
                requestQueue.add(objectRequest);
                break;
            case R.id.btAtrasAddSemana:
                Intent intent7 = new Intent(AddSemana.this, EditarSemana.class);
                intent7.putExtra("user", user);
                intent7.putExtra("semUser", semUser);
                startActivity(intent7);
                break;
        }
    }
}
