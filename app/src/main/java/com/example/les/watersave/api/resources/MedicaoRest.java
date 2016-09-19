package com.example.les.watersave.api.resources;


import android.content.Context;

import com.android.volley.Response.Listener;
import com.example.les.watersave.R;
import com.example.les.watersave.api.shared.ObjectRequest;
import com.example.les.watersave.api.shared.RequestManager;
import com.example.les.watersave.models.Medicao;
import com.android.volley.Response.ErrorListener;

public class MedicaoRest {

    private Context cxt;

    public MedicaoRest(Context cxt) {
        this.cxt = cxt;
    }

    public void list(Listener<Medicao[]> successResponse,
                     ErrorListener errorResponse, String chipid) {
        ObjectRequest<Medicao[]> objRequest = new ObjectRequest<>(
                String.format(cxt.getString(R.string.get_medicoes), chipid),
                Medicao[].class,
                null,
                successResponse,
                errorResponse
        );
        objRequest.setTag(
                cxt.getString(R.string.medicao_tag)
        );
        RequestManager.getInstance(cxt).addToRequestQueue(objRequest);
    }
}
