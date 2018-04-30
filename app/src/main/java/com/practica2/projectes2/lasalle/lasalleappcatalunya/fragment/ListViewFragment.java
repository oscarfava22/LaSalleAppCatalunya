package com.practica2.projectes2.lasalle.lasalleappcatalunya.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.MyListViewAdapterWithOnItemClick;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;

public class ListViewFragment extends Fragment{
    private ListView listView;
    private String tipus; //cargar solo los elementos deseados
    private ArrayList<CentreEscolar> cityArrayList;
    private MyListViewAdapterWithOnItemClick adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_for_fragment, container, false);

        listView = view.findViewById(R.id.listview);

        // Creamos el array con el modelo de datos.
        //cityArrayList = new WeatherManagerFromFile().getRecipesFromFile(getActivity());

        adapter = new MyListViewAdapterWithOnItemClick(getActivity(), cityArrayList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(adapter);

        return view;
    }
}
