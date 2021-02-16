package com.example.comercialesgeuy.partners;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PartnerActivity extends AppCompatActivity implements RecyclerAdapter.RecyclerItemClick, SearchView.OnQueryTextListener {

    private RecyclerView rcvPartners;
    private RecyclerAdapter adapter;
    private List<Partner> partnerList;
    private FloatingActionButton btnAddPartner;

    private List<Partner> lstPartners;
    private DBSQLite dbSQLite;
    private SQLiteDatabase database;

    private final int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        btnAddPartner = findViewById(R.id.btnAddPartner);
        rcvPartners = findViewById(R.id.rvPartners);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        initValues();

        btnAddPartner.setOnClickListener(v -> nuevoPartner());
    }

    private void nuevoPartner() {
        Intent intent = new Intent(this, PartnerNewActivity.class);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    //rellenar y mostrar partner
    private void initValues(){
        rcvPartners.setAdapter(null);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvPartners.setLayoutManager(manager);

        partnerList = leerPartners1();
        adapter = new RecyclerAdapter(partnerList, this);
        rcvPartners.setAdapter(adapter);
    }

    private List<Partner> leerPartners1() {
        lstPartners = new ArrayList<>();
        int comercId = ((MyAppVariables) this.getApplication()).getComercialId();
        lstPartners = dbSQLite.leerPartners(comercId);
        return lstPartners;
    }

    public void itemClick(Partner partner) {
        Intent intent = new Intent(this, PartnerInfoActivity.class);
        intent.putExtra("partner", partner);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            initValues();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}

