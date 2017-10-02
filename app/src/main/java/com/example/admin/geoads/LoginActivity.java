package com.example.admin.geoads;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private String correoR="0", contrasenaR="0", contrasena, correo;
    EditText eCorreo, eContrasena;
    TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eCorreo=(EditText) findViewById(R.id.eCorreo);
        eContrasena= (EditText) findViewById(R.id.eContrasena);
        mensaje= (TextView) findViewById(R.id.mensaje);

    }

    public void iniciar(View view) {

        if (eCorreo.getText().toString().isEmpty() || eContrasena.getText().toString().isEmpty()) {
            mensaje.setText("Comprobar todos los campos.");
        } else {
            correo = eCorreo.getText().toString().trim();
            contrasena = eContrasena.getText().toString();
            if (contrasena.equals(contrasenaR)) {
                if (validarEmail(correo)) {
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    intent.putExtra("correo", correo);
                    intent.putExtra("contrasena", contrasena);
                    startActivity(intent);

            }
                else{
                        Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
                }

            }

        }


    private boolean validarEmail(String email) {
        return email.matches(correoR);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK){
            correoR = data.getExtras().getString("correo");
            contrasenaR = data.getExtras().getString("contrasena");
            Toast.makeText(this, correoR, Toast.LENGTH_SHORT).show();
            Log.d("correo", correoR); //visualizar en el monitor las variables
            Log.d("contrasena",contrasenaR);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Registrese(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivityForResult(intent, 1234);
    }
}
