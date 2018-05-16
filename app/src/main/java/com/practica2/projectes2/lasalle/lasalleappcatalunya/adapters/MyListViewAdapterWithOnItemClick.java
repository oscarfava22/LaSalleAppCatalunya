package com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.List;

public class MyListViewAdapterWithOnItemClick extends BaseAdapter implements AdapterView.OnItemClickListener {
    private List<CentreEscolar> data;
    private Context context;

    public MyListViewAdapterWithOnItemClick(Context context, List<CentreEscolar> data) {
        this.data = data;
        this.context = context;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.activity_item_listview,
                    parent, false);
        } else {
            view = convertView;
        }

        TextView text1 = view.findViewById(R.id.nom_escola_item);
        text1.setText(data.get(position).getNomEscola());

        TextView text2 = view.findViewById(R.id.adresa_escola_item);
        text2.setText(data.get(position).getAdresaEscola());

        TextView text3 = view.findViewById(R.id.estudis_impartits_item);
        //COMENTAT GUILLE
        //text3.setText(data.get(position).getEstudisImpartits());

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);

        //TODO go to the description page

        builder.create().show();
    }

}
