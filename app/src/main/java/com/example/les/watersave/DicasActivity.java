package com.example.les.watersave;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.les.watersave.adapters.DicasRecyclerAdapter;
import com.example.les.watersave.models.Dica;

import java.util.ArrayList;
import java.util.List;

public class DicasActivity extends AppCompatActivity {

    private List<Dica> dicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        popularItem();
        initializeRecyclerView(dicas);
    }

    void initializeRecyclerView(List<Dica> dicas) {
        RecyclerView listDicas = (RecyclerView) findViewById(R.id.dicas_list);

        listDicas.setAdapter(new DicasRecyclerAdapter(getApplicationContext(), dicas));
        listDicas.setLayoutManager(new LinearLayoutManager(this));
        listDicas.setHasFixedSize(true);
        listDicas.setNestedScrollingEnabled(false);
    }

    public void popularItem(){
        dicas = new ArrayList<>();

        if(dicas.size() == 0) {
            dicas.add(new Dica(
                    getResources().getString(R.string.dica_banho),
                    getResources().getString(R.string.dica_banho_desc)
            ));
            dicas.add(new Dica(
                    getResources().getString(R.string.dica_louca),
                    getResources().getString(R.string.dica_louca_desc)
            ));
            dicas.add(new Dica(
                    getResources().getString(R.string.dica_carro),
                    getResources().getString(R.string.dica_carro_desc)
            ));
            dicas.add(new Dica(
                    getResources().getString(R.string.dica_torneira),
                    getResources().getString(R.string.dica_torneira_desc)
            ));
        }
    }
}
