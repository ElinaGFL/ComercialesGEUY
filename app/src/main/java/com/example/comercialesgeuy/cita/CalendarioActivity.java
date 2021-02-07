package com.example.comercialesgeuy.cita;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private FloatingActionButton fltNuevaVisita;
    private ListView lstCitas;
    private int dia, mes, anio, hora, minuto;
    private String fecha;

    Button button3;

    DBSQLite dbSQLite;
    SQLiteDatabase database;

    List<Cita> listaCitas;
    ArrayAdapter<Cita> adapter;

    int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = findViewById(R.id.calendarView);
        fltNuevaVisita = findViewById(R.id.fltNuevaCita);
        lstCitas = findViewById(R.id.lstCitas);

        button3 = findViewById(R.id.button3);

        dbSQLite = new DBSQLite(this); //this, CITAS, cursor, 1
        database = dbSQLite.getWritableDatabase();

        //listaCitasOn();
        bdCitasOn();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepick = new DatePickerDialog(CalendarioActivity.this, (view, year, month, dayOfMonth) -> {

                    // Date results here

                    TimePickerDialog timepick = new TimePickerDialog(CalendarioActivity.this, new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            //tv_time.setText(hourOfDay + ":" + minute);

                            hora = hourOfDay;
                            minuto = minute;
                        }
                    }, hora, minuto, false);
                    timepick.show();
                    timepick.setTitle("select time");
                    timepick.show();
                },anio,mes,dia);
                datepick.setTitle("select date");
                datepick.show();
            }
        });

        calendarView.setOnDateChangeListener((CalendarView view, int year, int month, int dayOfMonth) -> {
            month++; //porque se conta los meses desde 0

            dia = dayOfMonth;
            mes = month;
            anio = year;

            fecha = dia + "/" + mes + "/" + anio;

            TimePickerDialog timepick = new TimePickerDialog(CalendarioActivity.this, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay,
                                      int minute) {

                    //tv_time.setText(hourOfDay + ":" + minute);

                    hora = hourOfDay;
                    minuto = minute;
                }
            }, hora, minuto, false);
            timepick.show();
            timepick.setTitle("select time");
            timepick.show();
        });

        lstCitas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarioActivity.this);
                builder.setTitle("Borrar la cita");
                builder.setMessage("Are you sure you want to delete?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {

                        Cita cita = new Cita();
                        cita = (Cita) parent.getAdapter().getItem(pos);

                        Log.d("mLog", "cita id = " +  cita.getId());

                        int deleteItem = database.delete(dbSQLite.TABLE_CITAS, dbSQLite.CITAS_KEY_ID + "=" + cita.getId(), null);

                        Log.d("mLog", "updates rows count = " + deleteItem);

                        if(deleteItem > 0)
                            Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Not", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int ii) {
                            dialog.dismiss();
                        }
                    }
                );
                builder.show();

                return true;
            }
        });

        fltNuevaVisita.setOnClickListener(v -> nuevaCita());
    }

    private void bdCitasOn() {
        lstCitas.setAdapter(null);
        listaCitas = new ArrayList<>();
        Cita cita;

        Cursor cursor = database.query(DBSQLite.TABLE_CITAS, null, null, null, null, null, null);

        //moveToFirst() перемещает курсор на первую строку в результате запроса и заодно проверяет есть ли вообще записи в нем
        if (cursor.moveToFirst()) {

            //далее мы получаем порядковые номера столбцов и курсор по их именам с помощью метода getColumnIndex()
            //эти номера потом мы используем для чтения данных с помощью getInt() и getString() и выводим данные в лог
            int idIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_ID);
            int fechaIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_FECHA);
            int horaIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_HORA);
            int cabeceraIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_CABECERA);
            int textoIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_TEXTO);

            do {
                cita = new Cita();

                cita.setId(cursor.getInt(idIndex));
                cita.setFecha(cursor.getString(fechaIndex));
                cita.setHora(cursor.getString(horaIndex));
                cita.setCabecera(cursor.getString(cabeceraIndex));
                cita.setTexto(cursor.getString(textoIndex));
                listaCitas.add(cita);
                /*
                Log.d("mLog", "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", fecha = " + cursor.getString(fechaIndex) +
                        ", cabecera = " + cursor.getString(cabeceraIndex) +
                        ", texto = " + cursor.getString(textoIndex));
                 */
                //cursor.moveToNext() перебираем все строки в курсоре пока не добираемся до последней
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //освобождаем память, т.к. курсор уже не будет нигде использоваться
        cursor.close();

        adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1, listaCitas);
        lstCitas.setAdapter(adapter);
    }

    /*
    private void listaCitasOn() {
        List<Cita> citas;

        XMLPullParserHandlerCita parser = new XMLPullParserHandlerCita();
        citas = parser.parseXML();

        ArrayAdapter<Cita> adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1, citas);
        lstVisitas.setAdapter(adapter);
    }
     */

    private void nuevaCita() {

        Intent intent = new Intent(this, CalendarioNewActivity.class);
        intent.putExtra("fecha", fecha);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Se ha añadido la cita", Toast.LENGTH_SHORT).show();
                bdCitasOn();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}