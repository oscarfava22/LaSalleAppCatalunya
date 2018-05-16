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
    private ArrayList<CentreEscolar> cityAllArrayList;
    private ArrayList<CentreEscolar> filteredArraylist;
    private MyListViewAdapterWithOnItemClick adapter;
    private ArrayList<String> options; //conte totes les opcions de l'array

    public ListViewFragment() {
        options = new ArrayList<>();
        options.add("Barcelona");
        options.add("Girona");
        options.add("Lleida");
        options.add("Tarragona");
    }

    public void setDataArray(ArrayList<CentreEscolar> cityArrayList) {
        this.filteredArraylist = cityArrayList;
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    public void setFilteredListView(int option){ //0 = Barcelona,etc.
        filteredArraylist.clear();
        for (CentreEscolar centreEscolar: cityAllArrayList) {
            if(centreEscolar.getProvincia().equals(options.get(option))){
                filteredArraylist.add(centreEscolar);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_for_fragment, container, false);

        listView = view.findViewById(R.id.listview);

        filteredArraylist = new ArrayList<>();

        adapter = new MyListViewAdapterWithOnItemClick(getActivity(), filteredArraylist);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(adapter);

        return view;
    }
}
