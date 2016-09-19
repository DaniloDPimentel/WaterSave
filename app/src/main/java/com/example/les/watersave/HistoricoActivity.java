package com.example.les.watersave;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.ChartView;
import com.example.les.watersave.models.Medicao;
import com.example.les.watersave.models.Mock;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {
    private static final int DIVISOES_Y = 10;

    private BarChartView historico;
    private BarSet barrasHistorico;

    private Calendar firstDate, lastDate;
    private int lastDay, lastMonth, lastYear;
    private int firstDay, firstMonth, firstYear;
    private TextView txViewFirstDate, txViewLastDate;

    private int volumeCaixa;
    private float maxConsumo;
    private List<Medicao> medicoes;

    private DateFormat formatDate = DateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        Calendar c = Calendar.getInstance();
        historico = (BarChartView) findViewById(R.id.graph_historico);

        txViewLastDate = (TextView) findViewById(R.id.tv_data_fim);
        txViewFirstDate = (TextView) findViewById(R.id.tv_data_inicio);

        lastDay = c.get(Calendar.DAY_OF_MONTH);
        firstDay = c.get(Calendar.DAY_OF_MONTH);

        lastMonth = c.get(Calendar.MONTH);
        firstMonth = c.get(Calendar.MONTH);

        lastYear = c.get(Calendar.YEAR);
        firstYear = c.get(Calendar.YEAR);

        lastDate = c;
        firstDate = c;

        showDateDialogOnClick();
        carregarDados();
        updateTextView();
    }

    private void setGrafico() {
        Paint paint = new Paint();
        historico.reset();

        if (maxConsumo == 0) maxConsumo = 1;

        int interval = (int) Math.ceil(maxConsumo / DIVISOES_Y);
        int maxInterval = interval * DIVISOES_Y;

        historico.setAxisBorderValues(0, maxInterval, interval);
        historico.setGrid(ChartView.GridType.HORIZONTAL, DIVISOES_Y, 1, paint);

        if (barrasHistorico.size() != 0) {
            historico.addData(barrasHistorico);
            historico.show();
        }
    }

    private void carregarDados() {
        medicoes = Mock.Instance.getMedicoes();
        volumeCaixa = 1000;
    }

    private void gerarBarras(){
        barrasHistorico = new BarSet();

        maxConsumo = 0;
        float consumo, consumoDiario = 0;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        for (int i = 0; i < medicoes.size()-1; i++) {
            c1.setTime(medicoes.get(i).getData_da_medicao());
            c2.setTime(medicoes.get(i + 1).getData_da_medicao());

            if(c1.get(Calendar.DAY_OF_YEAR) < firstDate.get(Calendar.DAY_OF_YEAR) &&
                    c1.get(Calendar.YEAR) == firstDate.get(Calendar.YEAR)) {
                continue;
            }
            if(c1.get(Calendar.DAY_OF_YEAR) > lastDate.get(Calendar.DAY_OF_YEAR) &&
                    c1.get(Calendar.YEAR) == lastDate.get(Calendar.YEAR)) {
                continue;
            }
            if(c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR) &&
                    c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
                consumo = medicoes.get(i+1).getNivel() - medicoes.get(i).getNivel();
                if(consumo >= 0) {
                    consumo = consumo * volumeCaixa / 100f;
                    consumoDiario += consumo;
                }
            } else {
                if(consumoDiario > maxConsumo) maxConsumo = consumoDiario;

                Bar b = new Bar(formatDate.format(medicoes.get(i).getData_da_medicao()), consumoDiario);
                b.setColor(52479);
                barrasHistorico.addBar(b);
                consumoDiario = 0;
            }
        }
    }

    private void updateTextView() {
        String zero1 = "0", zero2 = "0";
        if(firstDay >= 10) {
            zero1 = "";
        }
        if(firstMonth >= 10) {
            zero2 = "";
        }
        txViewFirstDate.setText(zero1 + firstDay + "/" + zero2 + (firstMonth + 1) + "/" + firstYear);

        zero1 = "0";
        zero2 = "0";
        if(lastDay >= 10)
            zero1 = "";
        if(lastMonth >= 10)
            zero2 = "";

        txViewLastDate.setText(zero1 + lastDay + "/" + zero2 + (lastMonth +1) + "/" + lastYear);

        firstDate.set(Calendar.YEAR, firstYear);
        firstDate.set(Calendar.MONTH, firstMonth);
        firstDate.set(Calendar.DAY_OF_MONTH, firstDay);

        lastDate.set(Calendar.YEAR, lastYear);
        lastDate.set(Calendar.MONTH, lastMonth);
        lastDate.set(Calendar.DAY_OF_MONTH, lastDay);
    }

    private void showDateDialogOnClick() {
        Button btnDataFinal = (Button) findViewById(R.id.btn_data_fim);
        Button btnDataInicial = (Button) findViewById(R.id.btn_data_inicio);

        btnDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dt = new DatePickerDialog(HistoricoActivity.this,dtpickerFimListener, lastYear, lastMonth, lastDay);
                dt.show();
            }
        });

        btnDataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dt = new DatePickerDialog(HistoricoActivity.this,dtpickerInicioListener, firstYear, firstMonth, firstDay);
                dt.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener dtpickerInicioListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (year > lastYear) {
                Toast.makeText(getApplicationContext(), getString(R.string.data_inicial_error), Toast.LENGTH_LONG).show();
                return;
            }
            if(monthOfYear > lastMonth) {
                Toast.makeText(getApplicationContext(), getString(R.string.data_inicial_error), Toast.LENGTH_LONG).show();
                return;
            }
            if(dayOfMonth > lastDay) {
                Toast.makeText(getApplicationContext(), getString(R.string.data_inicial_error), Toast.LENGTH_LONG).show();
                return;
            }

            firstYear = year;
            firstMonth = monthOfYear;
            firstDay = dayOfMonth;

            updateTextView();
            gerarBarras();
            setGrafico();
        }
    };

    private DatePickerDialog.OnDateSetListener dtpickerFimListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (year < firstYear) {
                Toast.makeText(getApplicationContext(), getString(R.string.data_final_error), Toast.LENGTH_LONG).show();
                return;
            }
            if (monthOfYear < firstMonth) {
                Toast.makeText(getApplicationContext(), getString(R.string.data_final_error), Toast.LENGTH_LONG).show();
                return;
            }
            if (dayOfMonth < firstDay) {
                Toast.makeText(getApplicationContext(), getString(R.string.data_final_error), Toast.LENGTH_LONG).show();
                return;
            }

            lastYear = year;
            lastMonth = monthOfYear;
            lastDay = dayOfMonth;

            updateTextView();
            gerarBarras();
            setGrafico();
        }
    };
}
