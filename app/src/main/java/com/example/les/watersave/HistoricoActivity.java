package com.example.les.watersave;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.ChartView;
import com.example.les.watersave.models.Medicao;
import com.example.les.watersave.models.Mock;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class HistoricoActivity extends AppCompatActivity {
    private static final int dialogIdStart = 0, dialogIdEnd = 1;
    private static final int DIVISOES_Y = 10;

    private BarChartView historico;
    private int diaInicial, mesInicial, anoInicial;
    private Calendar dataInicial, dataFinal;
    private int diaFinal, mesFinal, anoFinal;
    private TextView tvDataInicio, tvDataFim;
    private Button btnDataInicial, btnDataFinal;
    private BarSet barrasHistorico;
    private List<Medicao> dados;
    private int volumeCaixa;
    private float maxConsumo;


    private DateFormat formataData = DateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        historico = (BarChartView) findViewById(R.id.graph_historico);
        tvDataInicio = (TextView) findViewById(R.id.tv_data_inicio);
        tvDataFim = (TextView) findViewById(R.id.tv_data_fim);
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
        atualizaTextView();
    }

    private void setarGrafico(){
        Paint paint = new Paint();
        historico.reset();

        if(maxConsumo == 0) maxConsumo = 1;

        int interval = (int) Math.ceil(maxConsumo / DIVISOES_Y);
        int maxInterval = interval * DIVISOES_Y;

        historico.setAxisBorderValues(0, maxInterval, interval);
        historico.setGrid(ChartView.GridType.HORIZONTAL, DIVISOES_Y, 1, paint);
        historico.addData(barrasHistorico);
        historico.show();
    }

    private void carregarDados(){
        dados = Mock.Instance.getMedicoes();
        volumeCaixa = 1000;
    }

    private int comparar(String data1, String data2){
        if(data1.substring(6,10).compareTo(data2.substring(6,10)) < 0){
            return -1;
        }
        if(data1.substring(6,10).compareTo(data2.substring(6,10)) > 0){
            return 1;
        }
        if(data1.substring(3,5).compareTo(data2.substring(3,5)) < 0){
            return -1;
        }
        if(data1.substring(3,5).compareTo(data2.substring(3,5)) > 0){
            return 1;
        }
        if(data1.substring(0,2).compareTo(data2.substring(0,2)) < 0){
            return -1;
        }
        if(data1.substring(0,2).compareTo(data2.substring(0,2)) > 0){
            return -1;
        }
        return 0;
    }

    private void gerarBarras(){
        barrasHistorico = new BarSet();
        float consumo = 0, consumoDiario = 0;
        maxConsumo = 0;
        for(int i = 0; i < dados.size()-1; i++){
            if(comparar(formataData.format(dados.get(i).getData()),formataData.format(dataInicial.getTime())) < 0){
                continue;
            }
            if(comparar(formataData.format(dados.get(i).getData()),formataData.format(dataFinal.getTime())) > 0){
                continue;
            }

            if(formataData.format(dados.get(i).getData()).compareTo(
                    formataData.format(dados.get(i+1).getData())) == 0){
                consumo = dados.get(i+1).getNivel() - dados.get(i).getNivel();
                if(consumo >= 0){
                    consumo = consumo*volumeCaixa/100f;
                    consumoDiario += consumo;
                }
            }
            else{
                if(consumoDiario > maxConsumo) maxConsumo = consumoDiario;
                Bar b = new Bar(formataData.format(dados.get(i).getData()),consumoDiario);
                b.setColor(52479);
                barrasHistorico.addBar(b);
                consumoDiario = 0;
            }
        }
    }


    private void atualizaTextView(){
        String zero1 = "0",zero2 = "0";
        if(diaInicial >= 10){
            zero1 = "";
        }
        if(mesInicial >= 10){
            zero2 = "";
        }
        tvDataInicio.setText(zero1+diaInicial+"/"+zero2+(mesInicial+1)+"/"+anoInicial);

        zero1 = "0"; zero2 = "0";
        if(diaFinal >= 10){
            zero1 = "";
        }
        if(mesFinal >= 10){
            zero2 = "";
        }
        tvDataFim.setText(zero1+diaFinal+"/"+zero2+(mesFinal+1)+"/"+anoFinal);

        dataInicial.set(Calendar.YEAR,anoInicial);
        dataInicial.set(Calendar.MONTH,mesInicial);
        dataInicial.set(Calendar.DAY_OF_MONTH,diaInicial);
        dataFinal.set(Calendar.YEAR,anoFinal);
        dataFinal.set(Calendar.MONTH,mesFinal);
        dataFinal.set(Calendar.DAY_OF_MONTH,diaFinal);

        carregarDados();
        gerarBarras();
        setarGrafico();
    }

    private void showDateDialogOnClick(){
        btnDataFinal = (Button) findViewById(R.id.btn_data_fim);
        btnDataInicial = (Button) findViewById(R.id.btn_data_inicio);

        btnDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dt = new DatePickerDialog(HistoricoActivity.this,dtpickerFimListener, anoFinal, mesFinal,diaFinal);
                dt.show();

            }
        });

        btnDataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dt = new DatePickerDialog(HistoricoActivity.this,dtpickerInicioListener, anoInicial,mesInicial,diaInicial);
                dt.show();

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
            atualizaTextView();
        }
    };

    private DatePickerDialog.OnDateSetListener dtpickerFimListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            anoFinal = year;
            mesFinal = monthOfYear;
            diaFinal = dayOfMonth;
            atualizaTextView();
        }
    };
}
