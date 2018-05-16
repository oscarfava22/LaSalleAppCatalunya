package com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.impl;

import android.content.Context;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.SchoolsRepository;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.utils.HttpRequestHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Guillem on 11/05/2018.
 */

public class SchoolsAPI implements SchoolsRepository {
    private static final String URL = "https://testapi-pprog2.azurewebsites.net/api/schools.php?method=";
    private static final String METHOD_GET = "getSchools";
    private static final String METHOD_ADD = "addSchool";
    private static final String METHOD_DELETE = "deleteSchool";
    private static final String MSG = "msg";
    private static final String NOMESCOLA = "schoolName";
    private static final String ADDRESS = "scholAddress";
    private static final String INFANTIL = "isInfantil";
    private static final String PRIMARIA = "isPrimary";
    private static final String ESO = "isEso";
    private static final String BATX = "isBatxillerat";
    private static final String FP = "isFP";
    private static final String UNI = "isUniversitat";
    private static final String DESC = "description";
    private static final String ID = "id";
    private static final String PROVINCIA = "province";

    @Override
    public ArrayList<CentreEscolar> getSchools() {
        ArrayList<CentreEscolar> list = null;
        //TODO: Petició a l'API per lat i long per cada escola
        JSONObject json = HttpRequestHelper.getInstance().doHttpRequest(URL,METHOD_GET);
        try{
            JSONArray search = json.getJSONArray(MSG);
            list = new ArrayList<>(search.length());
            for (int i = 0; i < search.length(); i++) {
                CentreEscolar centreEscolar = new CentreEscolar();
                centreEscolar.setId(search.getJSONObject(i).getInt(ID));
                centreEscolar.setAdresaEscola(search.getJSONObject(i).getString(ADDRESS));
                centreEscolar.setDescripcio(search.getJSONObject(i).getString(DESC));
                centreEscolar.setEsBatx(search.getJSONObject(i).getBoolean(BATX));
                centreEscolar.setEsFP(search.getJSONObject(i).getBoolean(FP));
                centreEscolar.setEsUni(search.getJSONObject(i).getBoolean(UNI));
                centreEscolar.setEsESO(search.getJSONObject(i).getBoolean(ESO));
                centreEscolar.setEsPrimaria(search.getJSONObject(i).getBoolean(PRIMARIA));
                centreEscolar.setEsInfantil(search.getJSONObject(i).getBoolean(INFANTIL));
                centreEscolar.setNomEscola(search.getJSONObject(i).getString(NOMESCOLA));
                centreEscolar.setProvincia(search.getJSONObject(i).getString(PROVINCIA));
                list.add(centreEscolar);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String addSchool(String school, String address, String province, String[] type, String description) {
        return null;
    }

    @Override
    public String deleteSchool(int schoolId) {
        return null;
    }
}

