package com.example.tfg.act;

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
import com.example.tfg.act.Util.PassConverter;
import com.example.tfg.act.activities.Conectado;
import com.example.tfg.act.activities.Creacion;
import com.example.tfg.act.base.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.tfg.act.Util.Constantes.server;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue requestQueue;

    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       botones();
    }

    public void botones(){
        Button btLogIn = findViewById(R.id.btLogIn);
        btLogIn.setOnClickListener(this);

        Button btSignUp = findViewById(R.id.btSignIn);
        btSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btLogIn:

                //---GET---
                EditText etUser = findViewById(R.id.etUser);
                EditText etPass = findViewById(R.id.etPass);

                String pass = etPass.getText().toString();

                pass = PassConverter.passConverter(pass);

                String endPoint = "?username="+etUser.getText().toString()+"&password="+pass;
                String url = server + "/user";
                url = url + endPoint;

                requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.e("Rest Response", response.toString());

                                    JSONObject jsonUser = new JSONObject(String.valueOf(response));
                                    user.setId(jsonUser.getInt("id"));
                                    user.setUsername(jsonUser.getString("username"));
                                    user.setPassword(jsonUser.getString("password"));
                                    user.setPeso(jsonUser.getInt("peso"));
                                    user.setAltura(jsonUser.getInt("altura"));
                                    user.setEdad(jsonUser.getInt("edad"));
                                    user.setSexo(jsonUser.getInt("sexo"));

                                    Toast.makeText(MainActivity.this, user.getUsername() + " connectado", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this, Conectado.class);
                                    intent.putExtra("user", user);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response", error.toString());
                                Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                requestQueue.add(objectRequest);

                break;
            case R.id.btSignIn:

                startActivity(new Intent(this, Creacion.class));
                break;
                //---DELETE---
//                EditText etUserDel = findViewById(R.id.etUser);
//
//                String endPointDel = "?nombre="+ etUserDel.getText().toString();
//                url = url + endPointDel;
//
//                RequestQueue requestQueueDel = Volley.newRequestQueue(this);
//                JsonObjectRequest objectRequestDel = new JsonObjectRequest(
//                        Request.Method.DELETE,
//                        url,
//                        null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Log.e("Rest Response", response.toString());
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("Rest Response", error.toString());
//                            }
//                        }
//                );
//
//                requestQueueDel.add(objectRequestDel);
        }
    }
}