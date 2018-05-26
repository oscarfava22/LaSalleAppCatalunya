package com.practica2.projectes2.lasalle.lasalleappcatalunya.model;

import java.util.List;

public class CentersManager {

    private List<CentreEscolar> centers;
    private static CentersManager instance;

    public static CentersManager getInstance () {

        if (instance == null) {
            instance = new CentersManager();
        }
        return instance;
    }

    public List<CentreEscolar> getCenters () {
        return centers;
    }

    public void setCenters (List<CentreEscolar> centers) {
        this.centers = centers;
    }
}
