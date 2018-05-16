package com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.CentreEscolar;

import java.util.ArrayList;

/**
 * Created by Guillem on 11/05/2018.
 */

public interface SchoolsRepository {
    ArrayList<CentreEscolar> getSchools();
    String addSchool(String school, String address, String province, String[] type, String description);
    String deleteSchool(int schoolId);
}
