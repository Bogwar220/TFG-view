package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.MainActivity;
import com.example.tfg.act.Util.AdapterAddDia;
import com.example.tfg.act.Util.AdapterRutinas;
import com.example.tfg.act.Util.Contadores;
import com.example.tfg.act.base.Dia;
import com.example.tfg.act.base.Ejercicio;
import com.example.tfg.act.base.Rutina;
import com.example.tfg.act.base.Semana;
import com.example.tfg.act.base.SemanaUser;
import com.example.tfg.act.base.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tfg.act.Util.Constantes.server;

public class AddDia extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private SemanaUser semUser;
    private Semana semana;
    private Dia sentDia;

    private Dia dia;
    private Ejercicio selectedEj;
    private Rutina rutina;

    private ArrayList<Ejercicio> ejercicios;
    private ArrayList<Rutina> rutinas;

    private Spinner spEjercicios;
    private ListView lvEjercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dia);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        semUser = intent.getParcelableExtra("semUser");
        semana = intent.getParcelableExtra("semana");
        sentDia = intent.getParcelableExtra("dia");

        Log.e("semanaGet", String.valueOf(semana.getId()));
        TextView tvNombreDia = findViewById(R.id.tvNombreDia);
        tvNombreDia.setText(Contadores.getDia(Contadores.dia));

        if(sentDia == null){
            dia = new Dia();
            //post dia con solo la semana

            Map<String, Integer> paramsSemana = new HashMap<>();
            paramsSemana.put("id", semana.getId());
            JSONObject jsonSemana = new JSONObject(paramsSemana);

            Map<String, JSONObject> paramsDia = new HashMap<>();
            paramsDia.put("semana", jsonSemana);
            JSONObject jsonDia = new JSONObject(paramsDia);

            String urlDia = server + "/dia";
            RequestQueue requestQueue1 = Volley.newRequestQueue(this);
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    urlDia,
                    jsonDia,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int idDIa = response.getInt("id");
                                dia.setId(idDIa);
                                dia.setNombre(Contadores.getDia(Contadores.dia));

                                Map<String, String> paramsDia = new HashMap<>();
                                paramsDia.put("id", String.valueOf(dia.getId()));
                                paramsDia.put("nombre", dia.getNombre());
                                JSONObject jsonDia = new JSONObject(paramsDia);
                                Log.e("diaProblem", jsonDia.toString());

                                //put dia para anadirle el nombre
                                String urlDia = server + "/dia";
                                RequestQueue requestQueue = Volley.newRequestQueue(AddDia.this);
                                JsonObjectRequest objectRequest1 = new JsonObjectRequest(
                                        Request.Method.PUT,
                                        urlDia,
                                        jsonDia,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.e("postYPutDia", response.toString());
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.e("postYPutDia", error.toString());
                                            }
                                        }
                                );
                                requestQueue.add(objectRequest1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("postDia", error.toString());
                        }
                    }
            );
            requestQueue1.add(objectRequest);
        }else{
            //get rutinas con idDia
            Log.e("diaParaRut", String.valueOf(sentDia.getId()));
            String endpoint = "/rut?idDia=" + String.valueOf(sentDia.getId());
            String urlRut = server + endpoint;

            RequestQueue requestQueue2 = Volley.newRequestQueue(AddDia.this);
            JsonArrayRequest arrayRequest1 = new JsonArrayRequest(
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

                                    JSONObject jsonEjercicio = jsonRutina.getJSONObject("ejercicio");
                                    int idEjercicio = jsonEjercicio.getInt("id");
                                    String nombreEjercicio = jsonEjercicio.getString("nombre");
                                    nombresEjercicios[i] = repeticiones + "x " + nombreEjercicio;

                                    Ejercicio ejercicio = new Ejercicio();
                                    ejercicio.setId(idEjercicio);
                                    ejercicio.setNombre(nombreEjercicio);

                                    rutina = new Rutina();
                                    rutina.setId(idRutina);
                                    rutina.setRepeticiones(repeticiones);
                                    rutina.setEjercicio(ejercicio);
                                    rutina.setDia(dia);

                                    rutinas.add(rutina);
                                }
                                //LISTVIEW

                                lvEjercicios = findViewById(R.id.lvEjercicios);
                                lvEjercicios.setAdapter(new AdapterRutinas(AddDia.this, nombresEjercicios));

                                lvEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                                        Log.e("rutin", rutina.getEjercicio().getNombre());
                                        Intent intent1 = new Intent(view.getContext(), RutinaActivity.class);
                                        intent1.putExtra("semUser", semUser);
                                        intent1.putExtra("user", user);
                                        intent1.putExtra("dia", sentDia);
                                        intent1.putExtra("semana", semana);
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
            requestQueue2.add(arrayRequest1);
        }
        //get todos los ejercicios
        String urlEj = server + "/ej";
        RequestQueue requestQueue = Volley.newRequestQueue(AddDia.this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
            Request.Method.GET,
            urlEj,
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ejercicios = new ArrayList<Ejercicio>();
                    Log.e("getAllEj", response.toString());
                    try {
                        for(int i=0; i<response.length(); i++){
                            JSONObject jsonEj = response.getJSONObject(i);
                            int idEj = jsonEj.getInt("id");
                            String nombreEj = jsonEj.getString("nombre");
                            String descripcionEj = jsonEj.getString("descripcion");

                            Ejercicio newEj = new Ejercicio();
                            newEj.setId(idEj);
                            newEj.setNombre(nombreEj);
                            newEj.setDescripcion(descripcionEj);

                            ejercicios.add(newEj);
                        }
                        spEjercicios = findViewById(R.id.spEjercicios);
                        ArrayAdapter<Ejercicio> nombresEjerciciosAdapter = new ArrayAdapter<Ejercicio>(
                                AddDia.this,
                                android.R.layout.simple_spinner_item,
                                ejercicios
                        );
                        nombresEjerciciosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spEjercicios.setAdapter(nombresEjerciciosAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
        );
        requestQueue.add(arrayRequest);

        botones();
    }

    private void botones(){
        Button btGuardar = findViewById(R.id.btAnadir);
        Button btAnadir = findViewById(R.id.btAnadirEj);
        Button btAnadirALista = findViewById(R.id.btGuardar);

        btGuardar.setOnClickListener(this);
        btAnadir.setOnClickListener(this);
        btAnadirALista.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btAnadirEj:
                Intent intent1 = new Intent(AddDia.this, AddEjercicio.class);
                intent1.putExtra("user", user);
                intent1.putExtra("semUser", semUser);
                intent1.putExtra("semana", semana);
                if(sentDia == null){
                    intent1.putExtra("dia", dia);
                }else{
                    intent1.putExtra("dia", sentDia);
                }

                startActivity(intent1);
                break;
            case R.id.btAnadir:
                Log.e("semanaSent", String.valueOf(semana.getId()));
                if(Contadores.dia != 7){
                    Contadores.dia = Contadores.dia + 1;
                    Intent intent = new Intent(AddDia.this, AddDia.class);
                    intent.putExtra("user", user);
                    intent.putExtra("semUser", semUser);
                    intent.putExtra("semana", semana);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(AddDia.this, EditarSemana.class);
                    intent.putExtra("user", user);
                    intent.putExtra("semUser", semUser);
                    intent.putExtra("semana", semana);
                    startActivity(intent);
                }
                break;
            case R.id.btGuardar:
                //post rutina
                final EditText etRepeticiones = findViewById(R.id.etRepeticiones);
                Map<String, Integer> paramsDia = new HashMap<>();

                if(sentDia == null){
                    Log.e("idDiaBtGuardar", String.valueOf(dia.getId()));
                    paramsDia.put("id", dia.getId());

                }else{
                    Log.e("idDiaBtGuardar", String.valueOf(sentDia.getId()));
                    paramsDia.put("id", sentDia.getId());
                }
                JSONObject jsonDia = new JSONObject(paramsDia);

                //jsonEjericio
                selectedEj = (Ejercicio) spEjercicios.getSelectedItem();
                Log.e("idEjBtGuardar", String.valueOf(selectedEj.getId()));

                Map<String, Integer> paramsEj = new HashMap<>();
                paramsEj.put("id", selectedEj.getId());
                JSONObject jsonEj = new JSONObject(paramsEj);

                //jsonRutina
                Map<String, JSONObject> paramsRutina = new HashMap<>();
                paramsRutina.put("dia", jsonDia);
                paramsRutina.put("ejercicio", jsonEj);
                JSONObject jsonRutina = new JSONObject(paramsRutina);

                String urlRutina = server + "/rut";
                RequestQueue requestQueue = Volley.newRequestQueue(AddDia.this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        urlRutina,
                        jsonRutina,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                               //putRutina
                                try {
                                    String urlRutina = server + "/rut";

                                    Rutina rutina = new Rutina();
                                    rutina.setId(response.getInt("id"));

                                    Map<String, Integer> paramsRutina = new HashMap<>();
                                    paramsRutina.put("id", rutina.getId());
                                    
                                    int reps = 0;
                                    if(String.valueOf(etRepeticiones.getText()) == null){
                                        reps = 0;
                                    }else{
                                        reps = Integer.parseInt(String.valueOf(etRepeticiones.getText()));
                                    }
                                    paramsRutina.put("repeticiones", reps);
                                    JSONObject jsonRutina = new JSONObject(paramsRutina);

                                    RequestQueue requestQueue1 = Volley.newRequestQueue(AddDia.this);
                                    JsonObjectRequest objectRequest1 = new JsonObjectRequest(
                                            Request.Method.PUT,
                                            urlRutina,
                                            jsonRutina,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    Log.e("putRutina", response.toString());

                                                    Intent intent = new Intent(AddDia.this, AddDia.class);
                                                    intent.putExtra("user", user);
                                                    intent.putExtra("semUser", semUser);
                                                    intent.putExtra("semana", semana);

                                                    if(sentDia == null){
                                                        intent.putExtra("dia", dia);
                                                    }else{
                                                        intent.putExtra("dia", sentDia);
                                                    }
                                                    startActivity(intent);
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.e("putRutina", error.toString());
                                                }
                                            }
                                    );
                                    requestQueue1.add(objectRequest1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("postRutina", error.toString());
                            }
                        }
                );
                requestQueue.add(objectRequest);
                break;
        }
    }
}
