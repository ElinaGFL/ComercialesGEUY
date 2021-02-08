package com.example.comercialesgeuy.cita;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private FloatingActionButton fltNuevaVisita;
    private ListView lstCitas;
    private String date_time;
    DBSQLite dbSQLite;
    SQLiteDatabase database;
    //request code para intent
    final private int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = findViewById(R.id.calendarView);
        fltNuevaVisita = findViewById(R.id.fltNuevaCita);
        lstCitas = findViewById(R.id.lstCitas);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        bdCitasOn();

        lstCitas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarioActivity.this);
                builder.setTitle("Modificar la cita");
                builder.setMessage("Quires modoficar o borrar la cita?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {
                        Cita cita = (Cita) parent.getAdapter().getItem(pos);
                        int resp = dbSQLite.deleteCita(cita);

                        if(resp > 0) {
                            Toast.makeText(getApplicationContext(), "Ha borrado la cita", Toast.LENGTH_SHORT).show();
                            bdCitasOn();
                        }else {
                            Toast.makeText(getApplicationContext(), "No se ha podido borrar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                /*builder.setNegativeButton("Modificar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int ii) {
                            Cita cita = (Cita) parent.getAdapter().getItem(pos);



                            int resp = dbSQLite.modificarCita(cita);

                            if(resp > 0) {
                                Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                bdCitasOn();
                            }else {
                                Toast.makeText(getApplicationContext(), "Not", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                );*/
                builder.show();

                return true;
            }
        });

        fltNuevaVisita.setOnClickListener(v -> recogerFecha());
    }

    private void recogerFecha() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        //recoger fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    //recoger tiempo
                    TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarioActivity.this,
                            (view1, hourOfDay, minute) -> {
                                //respetar el formato ISO 8601, éste estándar define el formato para almacenar la fecha y hora: YYYY-MM-DD HH:MM:SS
                                date_time = checkDigit(dayOfMonth) + "-" + checkDigit((monthOfYear + 1)) + "-" + checkDigit(year) + " " + checkDigit(hourOfDay) + ":" + checkDigit(minute) + ":00";
                                nuevaCita();
                            }, mHour, mMinute, true);
                    timePickerDialog.show();

                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private void bdCitasOn() {
        //vaciar el adapter
        lstCitas.setAdapter(null);
        //recoger ID de comercial logeado
        int comercId = ((MyAppVariables) this.getApplication()).getComercialId();
        //recoger la lista de citas segun ID de comercial
        List<Cita> listaCitas = dbSQLite.leerCitas(comercId);
        //crear adaptador y rellenar con lista de citas
        ArrayAdapter<Cita> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCitas);
        //"enchufar" el adaptador
        lstCitas.setAdapter(adapter);
    }

    private void nuevaCita() {
        Intent intent = new Intent(this, CalendarioNewActivity.class);
        intent.putExtra("fecha", date_time);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Se ha añadido la cita", Toast.LENGTH_SHORT).show();
                bdCitasOn();
                //finish();
                //startActivity(getIntent());
            }
            /*
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
             */
        }
    }
}