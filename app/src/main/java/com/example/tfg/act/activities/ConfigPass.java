package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.example.tfg.act.Util.Util;
import com.example.tfg.act.base.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.tfg.act.Util.Constantes.server;

public class ConfigPass extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private String url = server + "/user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_pass);

        Intent intent = getIntent();

        user = intent.getParcelableExtra("user");

        botones();
    }

    private void botones(){
        Button btCambiarPass = findViewById(R.id.btCambiarPass);
        Button btAtras = findViewById(R.id.btAtrasConfigPass);
        btCambiarPass.setOnClickListener(this);
        btAtras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btCambiarPass:
                EditText etActPass = findViewById(R.id.etActPass);
                EditText etNewPass = findViewById(R.id.etNewPass);
                EditText etNewPassConf = findViewById(R.id.etNewPassConf);

                if(!Util.passConverter(etActPass.getText().toString()).equals(user.getPassword())){
                    Toast.makeText(this, "La contraseña actual no es la verdadera", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etNewPass.length() < 3 ){
                    Toast.makeText(this, "La contraseña tiene que tener por lo menos 4 letras", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!etNewPass.getText().toString().equals(etNewPassConf.getText().toString())){
                    Toast.makeText(this, "Las dos nuevas contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(user.getId()));
                params.put("password", Util.passConverter(etNewPassConf.getText().toString()));

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
                                Toast.makeText(ConfigPass.this, "Contraseña modificada", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ConfigPass.this, Conectado.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response", error.toString());
                                Toast.makeText(ConfigPass.this, "Server problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                requestQueue.add(objectRequest);

                break;
            case R.id.btAtrasConfigPass:
                Intent intent = new Intent(ConfigPass.this, ConfigUser.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
    }
}
