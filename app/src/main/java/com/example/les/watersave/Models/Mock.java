package com.example.les.watersave.Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Renato on 07/09/2016.
 */
public class Mock {
    public static final Mock Instance = new Mock();

    private List<Medicao> medicoes;

    private Mock(){
        medicoes = new ArrayList<Medicao>();
        Medicao medicao;
        Calendar c = Calendar.getInstance();
        int j = 20;
        for(int i = 0; i < 100; i++){
            medicao = new Medicao(j,c.getTime());
            if(i%3 == 0) {
                c.add(Calendar.DATE, -1);
                c.add(Calendar.HOUR,4);
                j--;
            } else{
                c.add(Calendar.HOUR, -2);
                j++;
            }
            medicoes.add(medicao);
        }
    }

    public List<Medicao> getMedicoes(){
        return medicoes;
    }

    public void setMedicoes(List<Medicao> medicoes){
        this.medicoes = medicoes;
    }

    public void addMedicao(Medicao medicao){
        medicoes.add(medicao);
    }
}
