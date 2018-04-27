package com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;

public class MyListViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private ArrayList<CentreEscolar> data;
    private Context context;
    private Toolbar toolbar;

    public MyListViewAdapter(ArrayList<CentreEscolar> data, Context context, Toolbar toolbar) {
        this.data = data;
        this.toolbar = toolbar;
        this.context = context;

        /*
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
         */

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
        text3.setText(data.get(position).getEstudisImpartits());

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);

        builder.setTitle(context.getString(R.string.TitleDialogDelete));

        builder.setMessage(context.getString(R.string.AcceptToDelete) + " "
                +

                data.get(position).getNomEscola() + "?");

        builder.setPositiveButton(context.getString(R.string.Delete),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(position);
                        notifyDataSetChanged();
                        toolbar.setTitle(context.getString(R.string.app_name) + " (" + data.size() +")"); //inicialize with 0 tasks at first
                    }
                });
        builder.setNegativeButton(context.getString(R.string.Cancel), null);

        builder.create().show();
    }
}
