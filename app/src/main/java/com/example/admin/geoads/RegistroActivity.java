package com.example.admin.geoads;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.regex.Pattern;


public class RegistroActivity extends AppCompatActivity implements Serializable {

    private String correo, contrasena,contrasena2, emailpatron="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText eCorreo, eContrasena, eRepContrasena;
    TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        eRepContrasena = (EditText) findViewById(R.id.eRepContrasena);
        mensaje= (TextView) findViewById(R.id.mensajederegistro);
    }

    public void registrar(View view) {


        if(eCorreo.getText().toString().isEmpty() || eContrasena.getText().toString().isEmpty() || eRepContrasena.getText().toString().isEmpty()){
            mensaje.setText("Comprobar todos los campos.");
        }
        else {
            correo=eCorreo.getText().toString().trim();
            contrasena=eContrasena.getText().toString();
            contrasena2=eRepContrasena.getText().toString();
            if(contrasena.equals(contrasena2)){
                if (validarEmail(correo)){
                    Intent intent = new Intent();
                    intent.putExtra("correo", correo);
                    intent.putExtra("contrasena", contrasena);
                    setResult(RESULT_OK, intent);
                    finish();                }
                else{
                    mensaje.setText("Email invalido");
                }

            }
            else{
                mensaje.setText("Contraseñas no coinciden.");
            }

        }

    }
    private boolean validarEmail(String email) {
        return email.matches(emailpatron);
    }
}
