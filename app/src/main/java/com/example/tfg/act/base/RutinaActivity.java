package com.example.tfg.act.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.activities.ListaEjercicios;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.tfg.act.Util.Constantes.server;

public class RutinaActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private Dia dia;
    private Rutina rutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        dia = intent.getParcelableExtra("dia");
        rutina = intent.getParcelableExtra("rutina");

        TextView tvEjercicioNombre = findViewById(R.id.tvEjercicioName);
        TextView tvEjercicioDescripcion = findViewById(R.id.tvEjercicioDescripcion);
        TextView tvRepeticiones = findViewById(R.id.tvRepeticiones);

        tvEjercicioNombre.setText(rutina.getEjercicio().getNombre());
        tvEjercicioDescripcion.setText(rutina.getEjercicio().getDescripcion());
        tvRepeticiones.setText(String.valueOf(rutina.getRepeticiones()) + " Repeticiones");

        botones();
    }

    private void botones(){
        Button btHecho = findViewById(R.id.btHecho);
        Button btAtras = findViewById(R.id.btAtras);

        btHecho.setOnClickListener(this);
        btAtras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btHecho:
                rutina.setRepeticiones(0);
                String url = server + "/rut";

                Map<String, String>params = new HashMap<>();
                params.put("id", String.valueOf(rutina.getId()));
                params.put("repeticiones",String.valueOf(rutina.getRepeticiones()));

                JSONObject rutina = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(RutinaActivity.this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.PUT,
                        url,
                        rutina,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Rest Response", response.toString());
                                Toast.makeText(RutinaActivity.this, "Ejercicio Acabado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RutinaActivity.this, ListaEjercicios.class);
                                intent.putExtra("user", user);
                                intent.putExtra("dia", dia);
                                startActivity(intent);
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
                break;
            case R.id.btAtras:
                Intent intent = new Intent(RutinaActivity.this, ListaEjercicios.class);
                intent.putExtra("user", user);
                intent.putExtra("dia", dia);
                startActivity(intent);
                break;
        }
    }
}
