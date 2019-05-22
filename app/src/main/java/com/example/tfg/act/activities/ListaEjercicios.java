package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.tfg.act.base.Ejercicio;
import com.example.tfg.act.base.Rutina;
import com.example.tfg.act.base.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.tfg.act.Util.Constantes.server;

public class ListaEjercicios extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private Dia dia;
    private Rutina rutina;

    private ArrayList<Rutina> rutinas;

    private ListView lvRutinas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ejercicios);


        final Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        dia = intent.getParcelableExtra("dia");

        TextView tvDiaName = findViewById(R.id.tvDiaName);
        tvDiaName.setText(dia.getNombre());

        //get rutinas con idDia
        String endpoint = "/rut?idDia=" + String.valueOf(dia.getId());
        String urlRut = server + endpoint;

        RequestQueue requestQueue = Volley.newRequestQueue(ListaEjercicios.this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlRut,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest Response getRut", response.toString());
                        try {
                            String[] nombresEjercicios = new String[response.length()];
                            rutinas = new ArrayList<Rutina>();
                            for (int i=0; i<response.length(); i++){
                                JSONObject jsonRutina = response.getJSONObject(i);
                                int idRutina = jsonRutina.getInt("id");
                                int repeticiones = jsonRutina.getInt("repeticiones");

                                JSONObject jsonDia = jsonRutina.getJSONObject("dia");
                                JSONObject jsonEjercicio = jsonRutina.getJSONObject("ejercicio");
                                int idEjercicio = jsonEjercicio.getInt("id");
                                String nombreEjercicio = jsonEjercicio.getString("nombre");
                                String descripcionEjercicio = jsonEjercicio.getString("descripcion");
                                nombresEjercicios[i] = repeticiones + "x " + nombreEjercicio;

                                Ejercicio ejercicio = new Ejercicio();
                                ejercicio.setId(idEjercicio);
                                ejercicio.setNombre(nombreEjercicio);
                                ejercicio.setDescripcion(descripcionEjercicio);

                                rutina = new Rutina();
                                rutina.setId(idRutina);
                                rutina.setRepeticiones(repeticiones);
                                rutina.setEjercicio(ejercicio);
                                rutina.setDia(dia);

                                rutinas.add(rutina);
                            }
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response getRut", error.toString());
                    }
                }
        );
        requestQueue.add(arrayRequest);
        botones();
    }

    private void botones(){
        Button btAtrasList = findViewById(R.id.btAtrasList);

        btAtrasList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btAtrasList:
                Intent intent = new Intent(ListaEjercicios.this, Calendar.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
    }
}
