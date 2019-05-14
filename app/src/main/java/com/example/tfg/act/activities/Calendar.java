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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        TextView tvUsername = findViewById(R.id.tvUsername);
        final TextView tvSemana = findViewById(R.id.tvSemana);

        tvUsername.setText(user.getUsername());
        String endPoint = "?idUser="+user.getId();
        String url = server + "/semUser" + endPoint;

        //TODO: no hace el enlace bien hay que darle 2 veces y no lo entiendo porque

        requestQueue = Volley.newRequestQueue(this);
        arrayRequest = new JsonArrayRequest(
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

                                semana = new Semana();
                                semana.setId(id_semana);
                                semana.setNombre(nombre_semana);

                                if(seleccionado == 1){
                                    semUser = new SemanaUser();
                                    semUser.setId(id);
                                    semUser.setSemana(semana);
                                    semUser.setUser(user);
                                    tvSemana.setText(semUser.getSemana().getNombre());
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
         Button btStart = findViewById(R.id.btStart);
         Button btAtras = findViewById(R.id.btAtras);

         btEditar.setOnClickListener(this);
         btStart.setOnClickListener(this);
         btAtras.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btEditar:

                break;
            case R.id.btStart:

                break;
            case R.id.btAtras:
                Intent intent = new Intent(Calendar.this, Conectado.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
    }
}
