package com.example.tfg.act;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg.R;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String server = "http://192.168.34.18:8080";
    private String url = server + "/user";

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

                String endPoint = "?nombre="+etUser.getText().toString()+"&pass="+etPass.getText().toString();
                url = url + endPoint;

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Rest Response", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response" , error.toString());
                            }
                        }
                );

                requestQueue.add(objectRequest);
                break;
            case R.id.btSignIn:

                //---PUT---
                EditText etUserPut = findViewById(R.id.etUser);
                EditText etPassPut = findViewById(R.id.etPass);

                Map<String, String> params = new HashMap<>();
                params.put("nombre",etUserPut.getText().toString());
                params.put("password",etPassPut.getText().toString());

                JSONObject usuario = new JSONObject(params);

                RequestQueue requestQueueAdd = Volley.newRequestQueue(this);
                JsonObjectRequest objectRequestAdd = new JsonObjectRequest(
                        Request.Method.PUT,
                        url,
                        usuario,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Rest Response", response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response" , error.toString());
                            }
                        }
                );
                requestQueueAdd.add(objectRequestAdd);


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

                //---POST---
//                EditText etUserAdd = findViewById(R.id.etUser);
//                EditText etPassAdd = findViewById(R.id.etPass);
//
//                Map<String, String> params = new HashMap<>();
//                params.put ("nombre", etUserAdd.getText().toString());
//                params.put ("password", etPassAdd.getText().toString());
//
//                JSONObject usuario = new JSONObject(params);
//
//                RequestQueue requestQueueAdd = Volley.newRequestQueue(this);
//                JsonObjectRequest objectRequestAdd = new JsonObjectRequest(
//                        Request.Method.POST,
//                        url,
//                        usuario,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Log.e("Rest Response", response.toString());
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("Rest Response" , error.toString());
//                            }
//                        }
//                );
//                requestQueueAdd.add(objectRequestAdd);

                break;
        }
    }
}