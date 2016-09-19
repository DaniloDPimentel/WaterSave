package com.example.les.watersave.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.les.watersave.R;
import com.example.les.watersave.models.Dica;

import java.util.List;

public class DicasRecyclerAdapter extends RecyclerView.Adapter<DicasRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Dica> mDataset;

    public DicasRecyclerAdapter(Context context, List<Dica> mDataset) {
        this.context = context;
        this.mDataset = mDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        // Dados Dica
        public TextView txtNome;
        public ImageView imgDica;

        public ViewHolder(View v) {
            super(v);

            txtNome = (TextView) v.findViewById(R.id.text_dica);
            imgDica = (ImageView) v.findViewById(R.id.img_Dica);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DicasRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dica_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Dica dica = mDataset.get(position);
        holder.txtNome.setText(dica.getText());

        try {
            holder.imgDica.setImageResource(dica.getImageResourse(context));
        } catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
