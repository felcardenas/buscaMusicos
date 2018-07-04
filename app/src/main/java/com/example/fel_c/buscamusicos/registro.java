package com.example.fel_c.buscamusicos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class registro extends AppCompatActivity {

    EditText etAlias;
    EditText etCorreo;
    ImageButton ibFecha;
    TextView tvFecha;
    Spinner spEstilos;
    Spinner spRegion;
    Spinner spInstrumento;
    Spinner spComuna;

    int dia,mes,año;
    private String comuna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etAlias = findViewById(R.id.etAlias);
        etCorreo = findViewById(R.id.etCorreo);
        ibFecha = findViewById(R.id.ibFechaDeNacimiento);
        tvFecha = findViewById(R.id.tvFechaDeNacimiento);
        spEstilos = findViewById(R.id.spEstilo);
        spRegion = findViewById(R.id.spRegion);
        spInstrumento = findViewById(R.id.spInstrumento);
        spComuna = findViewById(R.id.spComuna);


        //EJECUTA EL BOTÓN DE EDITAR FECHA
        ibFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                año = c.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(registro.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String stringMonth, stringDay;


                        month = month + 1;
                        stringMonth = ""+month;
                        stringDay = ""+dayOfMonth;

                        if(month < 10){

                            stringMonth = "0" + month;

                        }
                        if(dayOfMonth < 10){

                            stringDay  = "0" + dayOfMonth;
                        }

                        tvFecha.setText(stringDay + "/" + stringMonth + "/" + year);
                    }
                },dia,mes,año);
                datePickerDialog.show();

            }
        });

        spRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String query = null;
                try {
                    query = URLEncoder.encode("\""+parent.getItemAtPosition(position).toString()+"\"","utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String urlComuna = "http://192.168.0.3/appmusicos/obtenerComunas.php?region="+query;



                Log.d("VERIFICACION",urlComuna);
                final String cadenaComuna = "NOMBRE_COMUNA";
                obtenerDatos(urlComuna,cadenaComuna);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String urlEstilos = "http://192.168.0.3/appmusicos/obtenerEstilos.php";
        String urlInstrumentos = "http://192.168.0.3/appmusicos/obtenerInstrumentos.php";
        String urlRegion = "http://192.168.0.3/appmusicos/obtenerRegiones.php";


        final String cadenaEstilo = "NOMBRE_ESTILO";
        final String cadenaInstrumento = "NOMBRE_INSTRUMENTO";
        final String cadenaRegion = "NOMBRE_REGION";



        obtenerDatos(urlEstilos,cadenaEstilo);
        obtenerDatos(urlInstrumentos,cadenaInstrumento);
        obtenerDatos(urlRegion,cadenaRegion);




     }











    //LLAMADO PHP
    public void obtenerDatos(String url, final String cadena){

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode==200) {

                    cargarSpinner(new String(responseBody), cadena);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    //LLENADO DE SPINNER
    private ArrayList<String> cargarSpinner(String response, String cadena) {

        ArrayList<String> listado = new ArrayList<String>();
        try{
            JSONArray jsonArray = new JSONArray(response);

            String texto;

            for (int i = 0; i < jsonArray.length(); i++) {

                texto = jsonArray.getJSONObject(i).getString(cadena);
                Log.d("texto",texto);

                listado.add(texto);
            }


            switch(cadena){
                case "NOMBRE_ESTILO":
                        spEstilos.setAdapter(new ArrayAdapter<String>(registro.this, android.R.layout.simple_dropdown_item_1line,listado));
                    break;

                case "NOMBRE_INSTRUMENTO":
                        spInstrumento.setAdapter(new ArrayAdapter<String>(registro.this, android.R.layout.simple_dropdown_item_1line,listado));
                    break;

                case "NOMBRE_REGION":
                        spRegion.setAdapter(new ArrayAdapter<String>(registro.this, android.R.layout.simple_dropdown_item_1line,listado));
                    break;
                case "NOMBRE_COMUNA":
                        spComuna.setAdapter(new ArrayAdapter<String>(registro.this, android.R.layout.simple_dropdown_item_1line,listado));
                    break;
            }



        }catch(Exception e){
            e.printStackTrace();
        }

        return listado;

    }


    //DESPLIEGUE DE MENSAJES PANTALLA REGISTRO
    public void mensaje(int valor){

        switch(valor){
            case 0:
                Toast.makeText(registro.this, "Debe rellenar los campos", Toast.LENGTH_LONG).show();
                break;



            case 1:
                Toast.makeText(registro.this, "Datos correctos... Espere unos segundos", Toast.LENGTH_LONG).show();
                break;


            case 2:
                Toast.makeText(registro.this, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(registro.this, "Default", Toast.LENGTH_LONG).show();
                break;

        }

    }

    //ENCRIPTAR CONTRASEÑA
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
