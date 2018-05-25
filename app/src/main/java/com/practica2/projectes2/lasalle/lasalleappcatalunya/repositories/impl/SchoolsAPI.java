package com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.impl;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.SchoolsRepository;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.utils.HttpRequestHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillem on 11/05/2018.
 */

public class SchoolsAPI implements SchoolsRepository {
    private static final String URL = "https://testapi-pprog2.azurewebsites.net/api/schools.php";
    private static final String METHOD_GET_SCHOOLS = "?method=getSchools";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_ADD_SCHOOL = "addSchool";
    private static final String METHOD_DELETE_SCHOOL = "deleteSchool&schoolId=";
    private static final String MSG = "msg";
    private static final String RES = "res";
    private static final String NOMESCOLA = "schoolName";
    private static final String ADDRESS = "schoolAddress";
    private static final String INFANTIL = "isInfantil";
    private static final String PRIMARIA = "isPrimaria";
    private static final String ESO = "isEso";
    private static final String BATX = "isBatxillerat";
    private static final String FP = "isFP";
    private static final String UNI = "isUniversitat";
    private static final String DESC = "description";
    private static final String ID = "id";
    private static final String PROVINCIA = "province";
    private Context context;

    public SchoolsAPI(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<CentreEscolar> getSchools() {
        ArrayList<CentreEscolar> list = null;
        //TODO: Petició a l'API per lat i long per cada escola
        JSONObject json = HttpRequestHelper.getInstance().doHttpRequest(URL + METHOD_GET_SCHOOLS, METHOD_GET, null, null, null, null, null);
        try{
            JSONArray search = json.getJSONArray(MSG);
            list = new ArrayList<>(search.length());
            for (int i = 0; i < search.length(); i++) {
                CentreEscolar centreEscolar = new CentreEscolar();
                centreEscolar.setId(search.getJSONObject(i).getInt(ID));
                centreEscolar.setAdresaEscola(search.getJSONObject(i).getString(ADDRESS));
                centreEscolar.setDescripcio(search.getJSONObject(i).getString(DESC));
                centreEscolar.setEsBatx(search.getJSONObject(i).getInt(BATX) != 0);
                centreEscolar.setEsFP(search.getJSONObject(i).getInt(FP) != 0);
                centreEscolar.setEsUni(search.getJSONObject(i).getInt(UNI) != 0);
                centreEscolar.setEsESO(search.getJSONObject(i).getInt(ESO) != 0);
                centreEscolar.setEsPrimaria(search.getJSONObject(i).getInt(PRIMARIA) != 0);
                centreEscolar.setEsInfantil(search.getJSONObject(i).getInt(INFANTIL) != 0);
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
    public int addSchool(String schoolName, String address, String province, String[] type, String description) {
        int done = 0;
        JSONObject json = HttpRequestHelper.getInstance().doHttpRequest(URL, METHOD_POST, schoolName, address, province, type, description);
        try{
            done = json.getInt(RES);
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Si done val:
            //0 --> Hi ha error
            //1 --> No hi ha error
        return done;
    }

    @Override
    public int deleteSchool(int schoolId) {
        int done = 0;

        Integer auxId = schoolId;
        JSONObject json = HttpRequestHelper.getInstance().doHttpRequest(URL + METHOD_DELETE_SCHOOL + auxId.toString(), METHOD_GET, null, null, null, null, null);
        try{
            done = json.getInt(RES);
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Si done val:
        //0 --> Hi ha error
        //1 --> No hi ha error
        return done;
    }

    @Override
    public CentreEscolar establirLocation(CentreEscolar centreEscolar){
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addresses = geocoder.getFromLocationName(centreEscolar.getAdresaEscola(), 1);
            Address a = null;
            if (addresses != null && addresses.size() > 0) {
                a = addresses.get(0);
            }
            if (a == null){
                centreEscolar.setLatitude(0);
                centreEscolar.setLongitude(0);
            }else {
                centreEscolar.setLatitude(a.getLatitude());
                centreEscolar.setLongitude(a.getLongitude());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return centreEscolar;
    }

}

