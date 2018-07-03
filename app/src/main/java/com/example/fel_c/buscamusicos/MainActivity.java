package com.example.fel_c.buscamusicos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.*;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.ResponseHandler;
//import cz.msebera.android.httpclient.entity.mime.Header;


public class MainActivity extends AppCompatActivity {

    private Button btnInicio;
    private EditText nombreDeUsuario;
    private EditText contrasena;
    private TextView tvRegistrar;
    private String contrasenaMD5;
    //private AsyncHttpClient cliente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreDeUsuario = findViewById(R.id.etNombreDeUsuario);
        contrasena = findViewById(R.id.etcontrasena);
        btnInicio = findViewById(R.id.btnIngresar);
        tvRegistrar = findViewById(R.id.tvRegistrarse);




        //EJECUCIÓN DE BOTÓN INGRESAR
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //VALIDACIONES
                 if(validarConexion()){
                    if(validarVacios(contrasena.getText().toString())){
                        if(validarVacios(nombreDeUsuario.getText().toString())) {

                            contrasenaMD5 = md5(contrasena.getText().toString());


                            String cadenaUrl;

                            cadenaUrl="http://192.168.0.3/appmusicos2/login.php";

                            AsyncHttpClient client = new AsyncHttpClient();

                            RequestParams params = new RequestParams();
                            params.add("alias",nombreDeUsuario.getText().toString());
                            params.add("clave",contrasenaMD5);

                            client.post(cadenaUrl, params, new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                            if(statusCode==200){

                                                mensaje(1);

                                                //SE ABRE PANTALLA DE INICIO
                                                Handler handler = new Handler();
                                                final Intent intent = new Intent(MainActivity.this, usuarioInicio.class);
                                                mensaje(1);

                                                //ESPERA DE 2 SEGUNDOS
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        startActivity(intent);
                                                    }
                                                }, 2000);


                                            }

                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                        }
                                    });


                        }else{
                            mensaje(0);
                        }
                    }else{
                        mensaje(0);
                    }
                }else{
                     mensaje(2);
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












    //Despliegue de mensajes en pantalla principal
    public void mensaje(int valor){

        switch(valor){
            case 0:
                Toast.makeText(MainActivity.this, "Debe rellenar los campos", Toast.LENGTH_LONG).show();
                break;



            case 1:
                Toast.makeText(MainActivity.this, "Datos correctos... Espere unos segundos", Toast.LENGTH_LONG).show();
                break;


            case 2:
                Toast.makeText(this, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(MainActivity.this, "Default", Toast.LENGTH_LONG).show();
                break;

        }

    }

    //Encriptar contraseña
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }




    //VALIDACIONES

    public boolean validarUsuario(){

        return false;
    }


    public boolean validarVacios(String campo){

        if(campo.equals("")){
            return false;
        }else{
            return true;
        }

    }

    public boolean validarConexion(){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }




}
