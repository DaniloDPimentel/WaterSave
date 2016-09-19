package com.example.les.watersave.models;

import java.util.Date;

public class Medicao implements Comparable<Medicao> {

    private Long chipid;

    private int nivel;
    private Date data_da_medicao;

    public Medicao(int nivel,Date data) {
        this.nivel = nivel;
        this.data_da_medicao = data;
    }

    public int getNivel() {
        return nivel;
    }

    public Date getData_da_medicao() {
        return data_da_medicao;
    }

    @Override
    public int compareTo(Medicao another) {
        return data_da_medicao.compareTo(another.data_da_medicao);
    }
}
