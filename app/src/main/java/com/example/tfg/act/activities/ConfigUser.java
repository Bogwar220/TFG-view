package com.example.tfg.act.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.base.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.tfg.act.Util.Constantes.server;

public class ConfigUser extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private String url = server + "/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_user);

        Intent intent = getIntent();

        user = intent.getParcelableExtra("user");

        TextView tvUser = findViewById(R.id.tvUser);
        TextView tvPeso = findViewById(R.id.tvPeso);
        TextView tvAltura = findViewById(R.id.tvAltura);

        tvUser.setText(tvUser.getText().toString() + user.getUsername());
        tvPeso.setText(tvPeso.getText().toString() + String.valueOf(user.getPeso()) + "kg");
        tvAltura.setText(tvAltura.getText().toString() + String.valueOf(user.getAltura()) + "cm");

        botones();
    }

    private void botones(){
        Button btAtras = findViewById(R.id.btAtras);
        btAtras.setOnClickListener(this);

        Button btChangePass = findViewById(R.id.btChangePass);
        btChangePass.setOnClickListener(this);

        Button btChange = findViewById(R.id.btChange);
        btChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btAtras:
                Intent intent = new Intent(ConfigUser.this, Conectado.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;

            case R.id.btChangePass:
                //esto va a empezar un nuevo activity
                break;

            case R.id.btChange:

                EditText etPeso = findViewById(R.id.etPeso);
                EditText etAltura = findViewById(R.id.etAltura);

                //condiciones peso
                if(!etPeso.getText().toString().equals("")){

                    if(Integer.parseInt(etPeso.getText().toString()) < 20){
                        Toast.makeText(this, "Peso pana!!! Mentirosooo", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(Integer.parseInt(etPeso.getText().toString())> 500){
                        Toast.makeText(this, "Pero que eres? Un elefante?", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    user.setPeso(Integer.parseInt(etPeso.getText().toString()));
                }

                //condiciones altura
                if(!etAltura.getText().toString().equals("")) {
                    if(Integer.parseInt(etAltura.getText().toString()) < 100){
                        Toast.makeText(this, "Venga AntMan deja de mentir!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(Integer.parseInt(etAltura.getText().toString())> 260){
                        Toast.makeText(this, "Y que tal el tiempo por alli arriba?", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(Integer.parseInt(etAltura.getText().toString())< user.getAltura()){
                        Toast.makeText(this, "Encogiste?", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    user.setAltura(Integer.parseInt(etAltura.getText().toString()));
                }

                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(user.getId()));
                params.put("peso", etPeso.getText().toString());
                params.put("altura", etAltura.getText().toString());

                JSONObject usuario = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.PUT,
                        url,
                        usuario,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Rest Response", response.toString());
                                Toast.makeText(ConfigUser.this, "Usuario modificado", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(ConfigUser.this, Conectado.class);
                                intent1.putExtra("user", user);
                                startActivity(intent1);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response", error.toString());
                                Toast.makeText(ConfigUser.this, "Server problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                requestQueue.add(objectRequest);
                break;
        }
    }
}
