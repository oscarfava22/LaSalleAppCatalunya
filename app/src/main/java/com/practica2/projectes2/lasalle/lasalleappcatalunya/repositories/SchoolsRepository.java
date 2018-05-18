package com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;

/**
 * Created by Guillem on 11/05/2018.
 */

public interface SchoolsRepository {
    ArrayList<CentreEscolar> getSchools();
    //Si addSchool o deleteSchool retorna:
    //0 --> Hi ha error
    //1 --> No hi ha error
    int addSchool(String schoolName, String address, String province, String[] type, String description);
    int deleteSchool(int schoolId);
    public void establirLocation(CentreEscolar centreEscolar);
}
