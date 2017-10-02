package com.example.admin.geoads;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PerfilActivity extends AppCompatActivity {
    private String correoR, contrasenaR;
    TextView tNombre, tpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tNombre= (TextView) findViewById(R.id.tNombre);
        tpass= (TextView) findViewById(R.id.tContrasena);
        Bundle extras = getIntent().getExtras();
        correoR = extras.getString("correo");
        contrasenaR = extras.getString("contrasena");

        tNombre.setText("Correo: "+ correoR);
        tpass.setText("Contraseña: "+ contrasenaR);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.mPrincipal:
                intent =  new Intent(PerfilActivity.this,
                        MainActivity.class);
                intent.putExtra("correo",correoR);
                intent.putExtra("contrasena",contrasenaR);
                startActivity(intent);
                finish();
                break;
            case R.id.mCerrar:
                intent =  new Intent(this,
                        LoginActivity.class);
                intent.putExtra("correo",correoR);
                intent.putExtra("contrasena",contrasenaR);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

