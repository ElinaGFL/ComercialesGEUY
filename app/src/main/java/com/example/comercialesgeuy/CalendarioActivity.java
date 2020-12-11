package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class CalendarioActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        calendarView = (CalendarView)findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {
                month++; //porque se conta los meses desde 0

                Toast.makeText(getApplicationContext(),
                        "Año: " + year + "\n" +
                                "Mes: " + month + "\n" +
                                "Dia: " + dayOfMonth,
                        Toast.LENGTH_SHORT).show();
            }});
    }
}