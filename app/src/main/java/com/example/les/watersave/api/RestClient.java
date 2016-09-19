package com.example.les.watersave.api;


import android.content.Context;

import com.example.les.watersave.api.resources.MedicaoRest;

public class RestClient {

    private Context cxt;
    public MedicaoRest medicao;

    public RestClient(Context cxt) {
        this.cxt = cxt;
        medicao = new MedicaoRest(cxt);
    }

    public Context getContext() {
        return cxt;
    }
}
