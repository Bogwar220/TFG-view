package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.Util.AdapterRutinas;
import com.example.tfg.act.Util.AdapterSemanas;
import com.example.tfg.act.base.Dia;
import com.example.tfg.act.base.Semana;
import com.example.tfg.act.base.SemanaUser;
import com.example.tfg.act.base.User;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.tfg.act.Util.Constantes.server;

public class EditarSemana extends AppCompatActivity implements View.OnClickListener {
    private SemanaUser semUser;
    private User user;
    private Dia dia;

    private ListView lvSemanas;
    private ArrayList<SemanaUser> semUsers;

    private SemanaUser newSemUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_semana);

        Intent intent = getIntent();

        semUser = intent.getParcelableExtra("semUser");
        user = intent.getParcelableExtra("user");
        dia = intent.getParcelableExtra("dia");

        TextView tvUser = findViewById(R.id.tvUser);
        tvUser.setText(user.getUsername());

        String endPoint = "?idUser="+user.getId();
        String url = server + "/semUser" + endPoint;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("editarSemana", response.toString());
                        String[] nombreSemanas = new String[response.length()];
                        semUsers = new ArrayList<SemanaUser>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonSemUser = response.getJSONObject(i);
                                int idSemUser = jsonSemUser.getInt("id");
                                int seleccionado = jsonSemUser.getInt("seleccionado");

                                JSONObject jsonUser = jsonSemUser.getJSONObject("user");
                                JSONObject jsonSemana = jsonSemUser.getJSONObject("semana");

                                int idSemana = jsonSemana.getInt("id");
                                String nombreSemana = jsonSemana.getString("nombre");

                                nombreSemanas[i] = nombreSemana;

                                newSemUser = new SemanaUser();
                                newSemUser.setId(idSemUser);
                                semUsers.add(newSemUser);
                            }

                            lvSemanas = findViewById(R.id.lvSemanas);
                            lvSemanas.setAdapter(new AdapterSemanas(EditarSemana.this, nombreSemanas, semUsers));

                            lvSemanas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                                    semUsers.get(i).setSeleccionado(1);

                                    if(semUsers.get(i).getId() != semUser.getId()){
                                        String url = server + "/semUser";

                                        Map<String, String> params = new HashMap<>();
                                        params.put("id", String.valueOf(semUser.getId()));
                                        params.put("seleccionado", String.valueOf(0));

                                        JSONObject jsonSemUser = new JSONObject(params);

                                        RequestQueue requestQueue1 = Volley.newRequestQueue(EditarSemana.this);
                                        JsonObjectRequest objectRequest = new JsonObjectRequest(
                                                Request.Method.PUT,
                                                url,
                                                jsonSemUser,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.e("RestResponse putSemUser", response.toString());
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {

                                                    }
                                                }
                                        );
                                        requestQueue1.add(objectRequest);

                                        Map<String, String> params1 = new HashMap<>();
                                        params1.put("id", String.valueOf(semUsers.get(i).getId()));
                                        params1.put("seleccionado", String.valueOf(1));

                                        JSONObject jsonNewSemUser = new JSONObject(params1);

                                        RequestQueue requestQueue2 = Volley.newRequestQueue(EditarSemana.this);
                                        JsonObjectRequest objectRequest1 = new JsonObjectRequest(
                                                Request.Method.PUT,
                                                url,
                                                jsonNewSemUser,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.e("RestPutNewSemUser", response.toString());
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.e("RestPutNewSemUser", error.toString());
                                                    }
                                                }
                                        );
                                        requestQueue2.add(objectRequest1);
                                    }

                                    Intent intent1 = new Intent(EditarSemana.this, Calendar.class);
                                    intent1.putExtra("user", user);
                                    startActivity(intent1);
                                }
                            });
                        } catch (JSONException e) {
                                e.printStackTrace();
                        }
                    }
                },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("editarSemana", error.toString());
                }
            }
        );
        requestQueue.add(arrayRequest);

        botones();
    }

    private void botones(){
        ImageButton btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btAdd:
                Intent intent = new Intent(EditarSemana.this, AddSemana.class);
                intent.putExtra("user", user);
                intent.putExtra("dia", dia);
                intent.putExtra("semUser", semUser);
                startActivity(intent);
                break;
        }
    }
}
