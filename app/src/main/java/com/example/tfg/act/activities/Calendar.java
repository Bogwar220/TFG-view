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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.base.Semana;
import com.example.tfg.act.base.SemanaUser;
import com.example.tfg.act.base.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.tfg.act.Util.Constantes.server;

public class Calendar extends AppCompatActivity implements View.OnClickListener {

    private SemanaUser semUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user");

        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvSemana = findViewById(R.id.tvSemana);

        tvUsername.setText(user.getUsername());
        String endPoint = "?idUser="+user.getId();
        String url = server + "/semUser" + endPoint;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("Rest Response", response.toString());

                            //TODO: esto hay que mirar como cambiarlo a JSONArray
                            //TODO: y conseguir de coger cada objeto en parte y acceder al unico que necesito de momento
                            //TODO: el que tiene seleccionado a 1
                            JSONObject jsonSemUser = new JSONObject(String.valueOf(response));
                            semUser.setId(jsonSemUser.getInt("id"));
                            semUser.setUser((User) jsonSemUser.getJSONObject("user"));
                            semUser.setSemana((Semana) jsonSemUser.getJSONObject("semana"));

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

        requestQueue.add(objectRequest);

        tvSemana.setText(semUser.getSemana().getNombre());

        botones();
    }

     private void botones(){
         Button btEditar = findViewById(R.id.btEditar);
         Button btStart = findViewById(R.id.btStart);

         btEditar.setOnClickListener(this);
         btStart.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btEditar:

                break;
            case R.id.btStart:

                break;
        }
    }
}
