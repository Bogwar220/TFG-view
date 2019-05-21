package com.example.tfg.act.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.act.Util.AdapterAddDia;
import com.example.tfg.act.base.Dia;
import com.example.tfg.act.base.SemanaUser;
import com.example.tfg.act.base.User;

public class AddDia extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private Dia dia;
    private SemanaUser semUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dia);

        Intent intent = getIntent();
        String nombreDia = intent.getParcelableExtra("nombreDia");

        user = intent.getParcelableExtra("user");
        dia = intent.getParcelableExtra("dia");
        semUser = intent.getParcelableExtra("semUser");

        TextView tvNombreDia = findViewById(R.id.tvNombreDia);
        tvNombreDia.setText(nombreDia);

        //TODO: Get de las rutinas de este dia que hay que crear anteriormente es posible utilizar
        // esta actividad para la parte de editacion no solamente la parte de anadir


        //-----------ListView------------------------------------
        ListView lvEjercicios = findViewById(R.id.lvEjercicios);
        //lvEjercicios.setAdapter(new AdapterAddDia(this, ));
        botones();
    }

    private void botones(){
        Button btGuardar = findViewById(R.id.btAnadir);
        Button btAtras = findViewById(R.id.btAtrasAddDia);
        Button btAnadir = findViewById(R.id.btAnadirEj);

        btGuardar.setOnClickListener(this);
        btAtras.setOnClickListener(this);
        btAnadir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btAnadirEj:

                break;
            case R.id.btAnadir:

                break;
            case R.id.btAtrasAddDia:
                Intent intent = new Intent(AddDia.this, AddSemana.class);
                intent.putExtra("user", user);
                intent.putExtra("dia",dia);
                intent.putExtra("semUser", semUser);
                startActivity(intent);
                break;
        }
    }
}
