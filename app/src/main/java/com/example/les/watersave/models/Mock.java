package com.example.les.watersave.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Mock {

    private static final double CAIXA_CAP = 1000;

    public static final Mock Instance = new Mock();
    private List<Medicao> medicoes = new ArrayList<>();

    private Mock() {
        int j = 20;
        Calendar c = Calendar.getInstance();
        for(int i = 0; i < 100; i++){
            Medicao medicao = new Medicao(j,c.getTime());
            if(i % 3 == 0) {
                c.add(Calendar.DATE, -1);
                c.add(Calendar.HOUR, 4);
                j--;
            } else {
                c.add(Calendar.HOUR, -2);
                j++;
            }
            medicoes.add(medicao);
        }
    }

    public List<Medicao> getMedicoes() {
        return medicoes;
    }

    public void setMedicoes(List<Medicao> medicoes) {
        this.medicoes = medicoes;
    }

    public void addMedicao(Medicao medicao) {
        medicoes.add(medicao);
    }

    public double getConsumo() {
        Calendar date = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        date.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = dateFormat.format(date.getTime());

        double consumo = 0;
        double refLevel = Double.MAX_VALUE;

        List<Medicao> tempMedicoes = Instance.getMedicoes();
        Collections.sort(tempMedicoes);

        for (Medicao medicao: tempMedicoes) {
            if (dateFormat.format(medicao.getData()).compareTo(startDate) >= 0) {
                double medicaoLevel = CAIXA_CAP / medicao.getNivel();
                if (medicaoLevel > refLevel)
                        consumo += medicaoLevel - refLevel;

                refLevel = medicaoLevel;
            }
        }
        return consumo;
    }

    public double getMediaConsumoDiaria() {
        Calendar date = Calendar.getInstance();
        return getConsumo() / date.get(Calendar.DAY_OF_MONTH);
    }

    public double getEstimativa() {
        Calendar date = Calendar.getInstance();

        double meanDay = getMediaConsumoDiaria();
        int currDay = date.get(Calendar.DAY_OF_MONTH);
        int lastDay = date.getActualMaximum(Calendar.DAY_OF_MONTH);

        return (CAIXA_CAP - (lastDay - currDay) * meanDay) / meanDay;
    }
}
