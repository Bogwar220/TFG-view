package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.Util.AdapterRutinas;
import com.example.tfg.act.base.Dia;
import com.example.tfg.act.base.Semana;
import com.example.tfg.act.base.SemanaUser;
import com.example.tfg.act.base.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.tfg.act.Util.Constantes.server;

public class EditarSemana extends AppCompatActivity {
    private SemanaUser semUser;
    private User user;
    private Dia dia;

    private ListView lvSemanas;
    private ArrayList<Semana> semanas;

    private SemanaUser newSemUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_semana);

        final Intent intent = getIntent();

        semUser = intent.getParcelableExtra("semUser");
        user = intent.getParcelableExtra("user");
        dia = intent.getParcelableExtra("dia");

        TextView tvUser = findViewById(R.id.tvUser);
        tvUser.setText(user.getUsername());

        String endPoint = "?idUser="+semUser.getUser().getId();
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
                        semanas = new ArrayList<Semana>();
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
                                newSemUser.setSeleccionado(1);

                                semUser.setSeleccionado(0);

                                lvSemanas = findViewById(R.id.lvSemanas);
                                lvSemanas.setAdapter(new AdapterRutinas(EditarSemana.this, nombreSemanas));
                                lvSemanas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                        //TODO: el put de semUser

                                        Intent intent1 = new Intent(EditarSemana.this, Calendar.class);
                                        intent1.putExtra("user", user);
                                        startActivity(intent1);
                                    }
                                });
                            }
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

        /*
           //LISTVIEW

                            lvRutinas = findViewById(R.id.lvRutinas);
                            lvRutinas.setAdapter(new AdapterRutinas(ListaEjercicios.this, nombresEjercicios));

                            lvRutinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                    Log.e("rutin", rutina.getEjercicio().getNombre());
                                    Intent intent1 = new Intent(view.getContext(), RutinaActivity.class);
                                    intent1.putExtra("rutina", rutinas.get(i));
                                    intent1.putExtra("user", user);
                                    intent1.putExtra("dia", dia);
                                    startActivity(intent1);
                                }
                            });
         */

    }
}
