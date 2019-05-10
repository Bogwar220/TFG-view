package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.act.MainActivity;
import com.example.tfg.act.base.User;

public class Conectado extends AppCompatActivity implements View.OnClickListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectado);

        Intent intent = getIntent();

        user = intent.getParcelableExtra("user");

        TextView tvUser = findViewById(R.id.tvUser);
        tvUser.setText(user.getUsername());

        botones();
    }

    private void botones(){
        Button btConf = findViewById(R.id.btConf);
        btConf.setOnClickListener(this);

        Button btEmpezar = findViewById(R.id.btEmpezar);
        btEmpezar.setOnClickListener(this);

        Button btDesconect = findViewById(R.id.btDesconect);
        btDesconect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btConf:
                Intent intent = new Intent(Conectado.this, ConfigUser.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;

            case R.id.btEmpezar:
                break;

            case R.id.btDesconect:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
