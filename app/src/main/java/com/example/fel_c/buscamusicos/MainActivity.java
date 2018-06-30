package com.example.fel_c.buscamusicos;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity{

    Button btnInicio;
    EditText nombreDeUsuario;
    EditText contraseña;
    TextView tvRegistrar;

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreDeUsuario = (EditText)findViewById(R.id.etNombreDeUsuario);
        contraseña = (EditText)findViewById(R.id.etContraseña);
        btnInicio = (Button)findViewById(R.id.btnIngresar);
        tvRegistrar = (TextView)findViewById(R.id.tvRegistrarse);


        //EJECUCIÓN DE BOTÓN INGRESAR
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //VALIDACIONES
                if(validarVacios(nombreDeUsuario.getText().toString())) {
                    if(validarVacios(contraseña.getText().toString())){

                        mensaje(1);

                        //SE ABRE PANTALLA DE INICIO
                        final Intent intent = new Intent (MainActivity.this,usuarioInicio.class);
                        mensaje(1);
                            //ESPERA DE 3 SEGUNDOS
                            handler.postDelayed(new Runnable() {
                                    public void run() {
                                        startActivity(intent);
                                    }
                            }, 3000);




                    }else{
                        mensaje(0);
                    }
                }else{
                    mensaje(0);
                }

            }
        });

        //EJECUCIÓN "REGISTRARSE"
        tvRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,registro.class);
                startActivity(intent);
            }
        });

    }


    public boolean validarVacios(String campo){

        if(campo.equals("")){
            return false;
        }else{
            return true;
        }

    }


    //Despliegue de mensajes en pantalla principal
    public void mensaje(int valor){

        switch(valor){
            case 0:
                Toast.makeText(MainActivity.this, "Debe rellenar los campos", Toast.LENGTH_LONG).show();
                break;



            case 1:
                Toast.makeText(MainActivity.this, "DATOS CORRECTOS", Toast.LENGTH_LONG).show();
                break;



            default:
                Toast.makeText(MainActivity.this, "Default", Toast.LENGTH_LONG).show();
                break;

        }

    }

    //Tiempo de espera
    /*public void esperar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

        }, milisegundos);
    }*/

}
