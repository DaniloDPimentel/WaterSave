package com.example.les.watersave.Models;

import java.util.Date;

/**
 * Created by Renato on 07/09/2016.
 */
public class Medicao {

    private Long id;
    private int nivel;
    private Date data;

    public Medicao(int nivel,Date data){
        this.nivel = nivel;
        this.data = data;
    }

    public int getNivel(){
        return nivel;
    }

    public Date getData(){
        return data;
    }
}
