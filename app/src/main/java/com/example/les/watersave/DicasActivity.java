package com.example.les.watersave;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.les.watersave.adapters.DicasRecyclerAdapter;
import com.example.les.watersave.models.Dica;

import java.util.ArrayList;
import java.util.List;

public class DicasActivity extends AppCompatActivity {
    private List<Dica> itens;
    private DicasRecyclerAdapter adapter;
    private static Context mContext;
    private RecyclerView listDicas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populaItem();
        initializeRecyclerView(itens);
    }

    void initializeRecyclerView(List<Dica> list) {
        listDicas = (RecyclerView) findViewById(R.id.dicas_list);
        listDicas.setAdapter(new DicasRecyclerAdapter(getApplicationContext(),list));
        listDicas.setLayoutManager(new LinearLayoutManager(this));
        listDicas.setHasFixedSize(true);
        listDicas.setNestedScrollingEnabled(false);
    }

    public void populaItem(){
        itens = new ArrayList<>();

        if(itens.size() == 0){
            itens.add(new Dica("banho","Tome banhos mais rápidos. Cada minuto a menos no chuveiro pode evitar o desperdício de 23 litros de água, dependendo do chuveiro."));
            itens.add(new Dica("torneira","Se você tem uma torneira pingando, conserte‑a logo – o desperdício de água pode chegar a 2.000 litros por mês. Não tente apertar mais a torneira, pois isto desgastará a arruela e agravará o vazamento."));
            itens.add(new Dica("louca","Ao lavar a louça manualmente, enxágue os pratos na pia cheia de água em vez de sob a torneira. Você pode economizar até 15 litros por minuto"));
            itens.add(new Dica("carro"," Ao lavar o carro, use balde e esponja no lugar da mangueira. Se utilizar apenas seis baldes, a economia chegará a 150 litros por lavagem"));
        }
    }

}
