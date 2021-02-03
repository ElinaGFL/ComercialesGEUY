package com.example.comercialesgeuy.cita;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private FloatingActionButton fltNuevaVisita;
    private ListView lstVisitas;
    private int dia, mes, anio;
    private String fecha;

    DBSQLite dbSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = findViewById(R.id.calendarView);
        fltNuevaVisita = findViewById(R.id.fltNuevaCita);
        lstVisitas = findViewById(R.id.lstCitas);

        fltNuevaVisita.setEnabled(false);

        //listaCitasOn();
        bdCitasOn();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            month++; //porque se conta los meses desde 0

            dia = dayOfMonth;
            mes = month;
            anio = year;

            fecha = dia + "/" + mes + "/" + anio;

            fltNuevaVisita.setEnabled(true);
        });

        fltNuevaVisita.setOnClickListener(v -> nuevaVisita());
    }

    private void bdCitasOn() {
        List<Cita> ListaCitas = new ArrayList<>();
        Cita cita;

        dbSQLite = new DBSQLite(this);
        SQLiteDatabase database = dbSQLite.getWritableDatabase();

        Cursor cursor = database.query(DBSQLite.TABLE_CITAS, null, null, null, null, null, null);

        //moveToFirst() перемещает курсор на первую строку в результате запроса и заодно проверяет есть ли вообще записи в нем
        if (cursor.moveToFirst()) {


            //далее мы получаем порядковые номера столбцов и курсор по их именам с помощью метода getColumnIndex()
            //эти номера потом мы используем для чтения данных с помощью getInt() и getString() и выводим данные в лог
            int idIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_ID);
            int fechaIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_FECHA);
            int cabeceraIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_CABECERA);
            int textoIndex = cursor.getColumnIndex(DBSQLite.CITAS_KEY_TEXTO);

            do {
                cita = new Cita();

                cita.setFecha(cursor.getString(fechaIndex));
                cita.setCabecera(cursor.getString(cabeceraIndex));
                cita.setTexto(cursor.getString(textoIndex));
                ListaCitas.add(cita);
                /*
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

        ArrayAdapter<Cita> adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1, ListaCitas);
        lstVisitas.setAdapter(adapter);
    }

    private void listaCitasOn() {
        List<Cita> citas;

        XMLPullParserHandlerCita parser = new XMLPullParserHandlerCita();
        citas = parser.parseXML();

        ArrayAdapter<Cita> adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1, citas);
        lstVisitas.setAdapter(adapter);
    }

    private void nuevaVisita() {
        Intent intent = new Intent(this, CalendarioNewActivity.class);
        intent.putExtra("fecha", fecha);
        startActivity(intent);
    }
}