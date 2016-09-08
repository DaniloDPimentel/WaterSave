package com.example.les.watersave.models;

import java.util.Date;

public class Medicao implements Comparable<Medicao> {

    private Long id;

    private int nivel;
    private Date data;

    public Medicao(int nivel,Date data) {
        this.nivel = nivel;
        this.data = data;
    }

    public int getNivel() {
        return nivel;
    }

    public Date getData() {
        return data;
    }

    @Override
    public int compareTo(Medicao another) {
        return data.compareTo(another.data);
    }
}
