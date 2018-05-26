package com.practica2.projectes2.lasalle.lasalleappcatalunya.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.R;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.activities.PantallaDeCentres;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.adapters.MyListViewAdapterWithOnTouch;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;

public class ListViewFragment extends Fragment {

    private ListView listView;
    private ArrayList<CentreEscolar> cityAllArrayList;
    private ArrayList<CentreEscolar> filteredArraylist;
    private MyListViewAdapterWithOnTouch adapter;
    private ArrayList<String> options; //conte totes les opcions de l'array
    private String estudis;
    private Context context;
    private int currentOption;


    public ListViewFragment() {
        filteredArraylist = new ArrayList<>();
        options = new ArrayList<>();
        currentOption = 0;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setDataArray(ArrayList<CentreEscolar> cityArrayList, String estudis) {
        this.cityAllArrayList = cityArrayList;
        this.estudis = estudis;
    }

    public  void filterByType(){
        if(filteredArraylist != null && options.size() != 0){
            if(filteredArraylist.size() != 0) {
                if (estudis.equals(getActivity().getString(R.string.othrs))) {
                    int i = 0;
                    while (i < filteredArraylist.size()) {
                        if (!filteredArraylist.get(i).isEsFP() && !filteredArraylist.get(i).isEsUni() && !filteredArraylist.get(i).isEsBatx()) {
                            filteredArraylist.remove(filteredArraylist.get(i));
                        }else{
                            i++;
                        }
                    }
                } else if (estudis.equals(getActivity().getString(R.string.all))) {
                    //do nothing
                } else if (estudis.equals(getActivity().getString(R.string.schoola))) {

                    int i = 0;
                    while( i < filteredArraylist.size()){
                        if (!filteredArraylist.get(i).isEsPrimaria() && !filteredArraylist.get(i).isEsInfantil() && !filteredArraylist.get(i).isEsESO()) {
                            filteredArraylist.remove(filteredArraylist.get(i));
                        }else{
                            i++;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void setFilteredListView(int option){ //0 = Barcelona,etc.
        currentOption = option;

        if(context != null){
            PantallaDeCentres activity = (PantallaDeCentres) context;
            cityAllArrayList = activity.getEscolesList();
            estudis = activity.getName(this);
        }

        if(cityAllArrayList != null && options.size() != 0){
            if(cityAllArrayList.size() != 0){
                filteredArraylist.clear();
                for (int i = 0; i < cityAllArrayList.size(); i++) {
                    if(cityAllArrayList.get(i).getProvincia().equals(options.get(option))){
                        filteredArraylist.add(cityAllArrayList.get(i));
                    }
                }
                adapter.notifyDataSetChanged();
                filterByType();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_for_fragment, container, false);

        listView = view.findViewById(R.id.listview);

        adapter = new MyListViewAdapterWithOnTouch(filteredArraylist, getActivity(),null, listView);

        listView.setAdapter(adapter);

        listView.setOnTouchListener(adapter);

        context = getActivity();

        options.add(getString(R.string.barcelona));
        options.add(getString(R.string.girona));
        options.add(getString(R.string.lleida));
        options.add(getString(R.string.tarragona));

        setFilteredListView(currentOption);

        return view;
    }
}
