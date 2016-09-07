package com.example.les.watersave.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Mock {

    private int volumeCaixa;

    public static final Mock Instance = new Mock();
    private List<Medicao> medicoes;

    private Mock() {
        Medicao medicao;
        volumeCaixa = 1000;
        medicoes = new ArrayList<Medicao>();
        int j = 20;
        Calendar c = Calendar.getInstance();
        for(int i = 0; i < 100; i++){
            medicao = new Medicao(j,c.getTime());
            if(i % 3 == 0) {
                c.add(Calendar.DATE, -1);
                c.add(Calendar.HOUR,4);
                j--;
            } else {
                c.add(Calendar.HOUR, -2);
                j++;
            }
            medicoes.add(medicao);
        }
    }

    public List<com.example.les.watersave.models.Medicao> getMedicoes(){
        return medicoes;
    }

    public void setMedicoes(List<Medicao> medicoes){
        this.medicoes = medicoes;
    }

    public void addMedicao(Medicao medicao){
        medicoes.add(medicao);
    }

    public int getVolumeCaixa(){
        return volumeCaixa;
    }
}
