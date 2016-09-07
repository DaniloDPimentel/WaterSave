package com.example.les.watersave;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.example.les.watersave.models.Medicao;
import com.example.les.watersave.models.Mock;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {
    private static final int dialogIdStart = 0, dialogIdEnd = 1;

    private BarChartView historico;
    private int diaInicial, mesInicial, anoInicial;
    private Calendar dataInicial, dataFinal;
    private int diaFinal, mesFinal, anoFinal;
    private TextView tvDataInicio, tvDataFim;
    private Button btnDataInicial, btnDataFinal;
    private BarSet barrasHistorico;
    private List<Medicao> dados;
    private int volumeCaixa;


    private DateFormat formataData = DateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        historico = (BarChartView) findViewById(R.id.graph_historico);
        tvDataInicio = (TextView) findViewById(R.id.tv_data_inicio);
        tvDataFim = (TextView) findViewById(R.id.tv_data_fim);
        barrasHistorico = new BarSet();
        Calendar cal = Calendar.getInstance();
        diaFinal = cal.get(Calendar.DAY_OF_MONTH);
        diaInicial = cal.get(Calendar.DAY_OF_MONTH);
        mesInicial = cal.get(Calendar.MONTH);
        mesFinal = cal.get(Calendar.MONTH);
        anoFinal = cal.get(Calendar.YEAR);
        anoInicial = cal.get(Calendar.YEAR);
        dataInicial = Calendar.getInstance();
        dataFinal = Calendar.getInstance();
        showDateDialogOnClick();

        carregarDados();
        gerarBarras();
        
    }

    private void carregarDados(){
        dados = Mock.Instance.getMedicoes();
        volumeCaixa = Mock.Instance.getVolumeCaixa();
    }

    private void gerarBarras(){
        float consumo = 0, consumoDiario = 0;
        for(int i = 0; i < dados.size()-1; i++){
            if(formataData.format(dados.get(i).getData()).compareTo(
                    formataData.format(dataInicial)) < 0){
                continue;
            }
            if(formataData.format(dados.get(i).getData()).compareTo(
                    formataData.format(dataFinal)) > 0){
                break;
            }

            if(formataData.format(dados.get(i).getData()).compareTo(
                    formataData.format(dados.get(i+1).getData())) == 0){
                consumo = dados.get(i+1).getNivel() - dados.get(i).getNivel();
                if(consumo >= 0){
                    consumo = consumo*volumeCaixa;
                    consumoDiario += consumo;
                }
            }
            else{

            }
        }
    }


    private void atualizaTextView(){
        tvDataInicio.setText(""+diaInicial+"/"+(mesInicial+1)+"/"+anoInicial);
        tvDataFim.setText(""+diaFinal+"/"+(mesFinal+1)+"/"+anoFinal);

        dataInicial.set(anoInicial,mesInicial,diaInicial);
        dataFinal.set(anoFinal,mesFinal,diaFinal);
    }

    private void showDateDialogOnClick(){
        btnDataFinal = (Button) findViewById(R.id.btn_data_fim);
        btnDataInicial = (Button) findViewById(R.id.btn_data_inicio);

        btnDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(HistoricoActivity.this,dtpickerFimListener, anoFinal, mesFinal,diaFinal);
                atualizaTextView();
            }
        });

        btnDataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(HistoricoActivity.this,dtpickerInicioListener, anoInicial,mesInicial,diaInicial);
                atualizaTextView();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener dtpickerInicioListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            anoInicial = year;
            mesInicial = monthOfYear;
            diaInicial = dayOfMonth;
        }
    };

    private DatePickerDialog.OnDateSetListener dtpickerFimListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            anoFinal = year;
            mesFinal = monthOfYear;
            diaFinal = dayOfMonth;
        }
    };
}
