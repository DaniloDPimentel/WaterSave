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

    private List<Dica> itens;
    private RecyclerView listDicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        popularItem();
        initializeRecyclerView(itens);
    }

    void initializeRecyclerView(List<Dica> list) {
        listDicas = (RecyclerView) findViewById(R.id.dicas_list);
        listDicas.setAdapter(new DicasRecyclerAdapter(getApplicationContext(),list));
        listDicas.setLayoutManager(new LinearLayoutManager(this));
        listDicas.setHasFixedSize(true);
        listDicas.setNestedScrollingEnabled(false);
    }

    public void popularItem(){
        itens = new ArrayList<>();

        if(itens.size() == 0) {
            itens.add(new Dica(
                    getResources().getString(R.string.dica_banho),
                    getResources().getString(R.string.dica_banho_desc)
            ));
            itens.add(new Dica(
                    getResources().getString(R.string.dica_louca),
                    getResources().getString(R.string.dica_louca_desc)
            ));
            itens.add(new Dica(
                    getResources().getString(R.string.dica_carro),
                    getResources().getString(R.string.dica_carro_desc)
            ));
            itens.add(new Dica(
                    getResources().getString(R.string.dica_torneira),
                    getResources().getString(R.string.dica_torneira_desc)
            ));
        }
    }

}
