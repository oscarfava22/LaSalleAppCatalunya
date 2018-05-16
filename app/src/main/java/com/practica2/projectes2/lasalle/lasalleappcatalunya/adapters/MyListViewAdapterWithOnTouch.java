package com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;

public class MyListViewAdapterWithOnTouch extends BaseAdapter implements View.OnTouchListener {

    private ArrayList<CentreEscolar> data;
    private Context context;
    private Toolbar toolbar;
    private ListView listView;
    private GestureDetector gestureDetector;

    public MyListViewAdapterWithOnTouch(ArrayList<CentreEscolar> data, Context context, Toolbar toolbar, ListView listView) {
        this.data = data;
        this.listView = listView;
        this.toolbar = toolbar;
        this.context = context;
        gestureDetector = new GestureDetector(context, new GestureListener());
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
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void onSwipeRight(int pos) {
        //Do what you want after swiping left to right

    }

    public void onSwipeLeft(final int pos) {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.deleteSure));
        builder.setPositiveButton(context.getString(R.string.Delete),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(pos);
                        notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton(context.getString(R.string.Cancel), null);
        builder.create().show();
    }

    public void click(){
           //TODO enviar a la info
        }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        private int getPostion(MotionEvent e1) {
            return listView.pointToPosition((int) e1.getX(), (int) e1.getY());
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY)
                    && Math.abs(distanceX) > SWIPE_THRESHOLD
                    && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight(getPostion(e1));
                else
                    onSwipeLeft(getPostion(e1));
                return true;
            }
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            click();
            return true;
        }
    }
}
