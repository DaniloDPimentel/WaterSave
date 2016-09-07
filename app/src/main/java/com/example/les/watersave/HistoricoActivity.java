package com.example.les.watersave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.example.les.watersave.Models.Medicao;
import com.example.les.watersave.Models.Mock;

import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private BarChartView historico;
    private BarSet barrasHistorico;
    private List<Medicao> dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        historico = (BarChartView) findViewById(R.id.graph_historico);
        barrasHistorico = new BarSet();
        carregarDados();
        Log.d("Teste","nivel: "+dados.get(0).getNivel());
    }

    private void carregarDados(){
        dados = Mock.Instance.getMedicoes();
    }
}
