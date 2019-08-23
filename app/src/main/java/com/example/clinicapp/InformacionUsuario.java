package com.example.clinicapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//Al igual que la clase de I. Clinica, coge datos del usuario conectado en la APP y rellena los Text View
public class InformacionUsuario extends AppCompatActivity {

    TextView txtnombre;
    TextView txtfecha;
    TextView txtaltura;
    TextView txtpeso;
    TextView txttiposangre;

    VarGlobales global;
    BDClinica bdclinica;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_usuario);

        global = new VarGlobales();

        bdclinica = new BDClinica(this, "BDClinica", null, 1);
        db = bdclinica.getReadableDatabase();

        txtnombre = findViewById(R.id.txt_nombre);
        txtfecha = findViewById(R.id.txt_fecha);
        txtaltura = findViewById(R.id.txt_altura);
        txtpeso = findViewById(R.id.txt_peso);
        txttiposangre = findViewById(R.id.txt_tiposangre);

        try {
            rellenarInformacion();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void rellenarInformacion() throws ParseException {

        Cursor cursor = db.rawQuery("SELECT * FROM InformacionUsuario where usuarioid = " + global.getUSuario(), null);

        //String to date
        Date date;
        String pattern = "ddMMyyyy";
        String pattern2 = "dd/MM/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);


        while (cursor.moveToNext()) {
            date = formatter.parse(cursor.getString(3));
            //Date to string
            DateFormat df = new SimpleDateFormat(pattern2);
            String datestring = df.format(date);
            txtnombre.setText("Nombre: " + cursor.getString(1) + " " + cursor.getString(2));
            txtfecha.setText("Fecha de nacimiento: " + datestring);
            txtaltura.setText("Altura: " + cursor.getString(5));
            txtpeso.setText("Peso: " + cursor.getString(4));
            txttiposangre.setText("Tipo de Sangre: " + cursor.getString(6));
        }

        db.close();
        cursor.close();
    }

    public void volver(View v) {
        finish();
    }
}
