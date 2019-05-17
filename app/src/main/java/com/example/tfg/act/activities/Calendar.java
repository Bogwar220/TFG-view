package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.Util.Util;
import com.example.tfg.act.base.Dia;
import com.example.tfg.act.base.Semana;
import com.example.tfg.act.base.SemanaUser;
import com.example.tfg.act.base.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.tfg.act.Util.Constantes.server;

public class Calendar extends AppCompatActivity implements View.OnClickListener {

    private SemanaUser semUser;
    private User user;
    private Semana semana;
    private Dia dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        TextView tvUsername = findViewById(R.id.tvUsername);
        final TextView tvSemana = findViewById(R.id.tvSemana);

        tvUsername.setText(user.getUsername());
        String endPoint = "?idUser="+user.getId();
        String url = server + "/semUser" + endPoint;

        //TODO: no hace el enlace bien hay que darle 2 veces y no lo entiendo porque
        //get semana para enseñarla en la activity
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest Response 2", response.toString());

                        try {
                            for(int i=0;i<response.length(); i++){

                                JSONObject jsonSemUser = response.getJSONObject(i);
                                int id = jsonSemUser.getInt("id");
                                JSONObject jsonSemana = jsonSemUser.getJSONObject("semana");

                                int id_semana = jsonSemana.getInt("id");
                                String nombre_semana = jsonSemana.getString("nombre");

                                int seleccionado = jsonSemUser.getInt("seleccionado");

                                if(seleccionado == 1){

                                    semana = new Semana();
                                    semana.setId(id_semana);
                                    semana.setNombre(nombre_semana);

                                    semUser = new SemanaUser();
                                    semUser.setSemana(semana);
                                    semUser.setUser(user);
                                    semUser.setId(id);

                                    tvSemana.setText(semUser.getSemana().getNombre());

                                    final int idSemUser = semUser.getId();

                                    dia = new Dia();
                                    dia.setSemana(semana);

                                    CalendarView cvCalendar =findViewById(R.id.cvCalendar);
                                    cvCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                        @Override
                                        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                                            String date = year + "-" + month + "-" + dayOfMonth;
                                            int day = Util.getDayOfWeek(date);
                                            String dayOfWeek = new String();
                                            switch (day){
                                                case 1:
                                                    dayOfWeek = "Martes";
                                                    break;
                                                case 2:
                                                    dayOfWeek = "Miercoles";
                                                    break;
                                                case 3:
                                                    dayOfWeek = "Jueves";
                                                    break;
                                                case 4:
                                                    dayOfWeek = "Viernes";
                                                    break;
                                                case 5:
                                                    dayOfWeek = "Sabado";
                                                    break;
                                                case 6:
                                                    dayOfWeek = "Domingo";
                                                    break;
                                                case 7:
                                                    dayOfWeek = "Lunes";
                                                    break;
                                            }

                                            dia.setNombre(dayOfWeek);

                                            String endpoint = "/dia?idSemUser=" + String.valueOf(idSemUser);
                                            String urlDia = server + endpoint;

                                            RequestQueue requestQueue = Volley.newRequestQueue(Calendar.this);
                                            JsonArrayRequest arrayRequest = new JsonArrayRequest(
                                                    Request.Method.GET,
                                                    urlDia,
                                                    null,
                                                    new Response.Listener<JSONArray>() {
                                                        @Override
                                                        public void onResponse(JSONArray response) {
                                                            Log.e("Rest Response getDia", response.toString());
                                                            try {
                                                                for(int i=0; i<response.length(); i++){
                                                                    JSONObject jsonDia = response.getJSONObject(i);
                                                                    int idDia = jsonDia.getInt("id");
                                                                    String nombreDia = jsonDia.getString("nombre");

                                                                    JSONObject jsonSemana = jsonDia.getJSONObject("semana");
                                                                    int idSemana = jsonSemana.getInt("id");

                                                                    if(nombreDia.equals(dia.getNombre()) && idSemana == dia.getSemana().getId()){
                                                                        dia.setId(idDia);
                                                                    }
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.e("Rest Response getDia", error.toString());
                                                        }
                                                    }
                                            );
                                            requestQueue.add(arrayRequest);
                                            //----!!!!------------
                                            Log.e("diaId",String.valueOf(dia.getId()));
                                            Log.e("diaNombre",dia.getNombre());
                                            Log.e("diaSemanaNombre",dia.getSemana().getNombre());
                                        }
                                    });

                                    //getEjercicios verificar si el usuario tiene algo y añadirlo en caso de que no
                                    String urlEj = server + "/ej";
                                    RequestQueue requestQueue = Volley.newRequestQueue(Calendar.this);
                                    JsonArrayRequest arrayRequest = new JsonArrayRequest(
                                            Request.Method.GET,
                                            urlEj,
                                            null,
                                            new Response.Listener<JSONArray>() {
                                                @Override
                                                public void onResponse(JSONArray response) {
                                                    Log.e("Rest Response getEJ", response.toString());
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.e("Rest Restponse getEj", error.toString());
                                                }
                                            }
                                    );
                                    requestQueue.add(arrayRequest);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );
        requestQueue.add(arrayRequest);

        botones();
    }

     private void botones(){
         Button btEditar = findViewById(R.id.btEditar);
         Button btStart = findViewById(R.id.btStartCalendar);
         Button btAtras = findViewById(R.id.btAtrasCalendar);

         btEditar.setOnClickListener(this);
         btStart.setOnClickListener(this);
         btAtras.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btEditar:

                break;
            case R.id.btStartCalendar:
                Intent intent1 = new Intent(Calendar.this, ListaEjercicios.class);
                intent1.putExtra("user", user);
                intent1.putExtra("dia", dia);
                startActivity(intent1);
                break;
            case R.id.btAtrasCalendar:
                Intent intent = new Intent(Calendar.this, Conectado.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
    }
}
