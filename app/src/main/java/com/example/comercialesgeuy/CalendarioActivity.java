package com.example.comercialesgeuy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.cita.Cita;
import com.example.comercialesgeuy.cita.XMLPullParserHandlerCita;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private FloatingActionButton fltNuevaVisita;
    private ListView lstVisitas;
    private int dia, mes, anio;
    private String fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = findViewById(R.id.calendarView);
        fltNuevaVisita = findViewById(R.id.fltNuevaCita);
        lstVisitas = findViewById(R.id.lstCitas);

        fltNuevaVisita.setEnabled(false);

        listaCitasOn();

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

    private void listaCitasOn() {
        List<Cita> citas = null;

        XMLPullParserHandlerCita parser = new XMLPullParserHandlerCita();
        citas = parser.parseXML();

        ArrayAdapter<Cita> adapter = new ArrayAdapter<Cita> (this,android.R.layout.simple_list_item_1, citas);
        lstVisitas.setAdapter(adapter);
    }

    private void nuevaVisita() {
        Intent intent = new Intent(this, CalendarioNewActivity.class);
        intent.putExtra("fecha", fecha);
        startActivity(intent);
    }
}