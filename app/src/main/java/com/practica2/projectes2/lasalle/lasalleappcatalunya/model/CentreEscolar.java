package com.practica2.projectes2.lasalle.lasalleappcatalunya.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.Comparator;

public class CentreEscolar implements Comparable,Parcelable{

    private String nomEscola;
    private String adresaEscola;
    private boolean esInfantil;
    private boolean esPrimaria;
    private boolean esESO;
    private boolean esBatx;
    private boolean esFP;
    private boolean esUni;
    private String descripcio;
    private int id;
    private String provincia;
    private double latitude;
    private double longitude;

    public void setEsInfantil(boolean esInfantil) {
        this.esInfantil = esInfantil;
    }

    public void setEsPrimaria(boolean esPrimaria) {
        this.esPrimaria = esPrimaria;
    }

    public void setEsESO(boolean esESO) {
        this.esESO = esESO;
    }

    public void setEsBatx(boolean esBatx) {
        this.esBatx = esBatx;
    }

    public void setEsFP(boolean esFP) {
        this.esFP = esFP;
    }

    public void setEsUni(boolean esUni) {
        this.esUni = esUni;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public CentreEscolar() {
    }

    public boolean isEsInfantil() {
        return esInfantil;
    }

    public boolean isEsPrimaria() {
        return esPrimaria;
    }

    public boolean isEsESO() {
        return esESO;
    }

    public boolean isEsBatx() {
        return esBatx;
    }

    public boolean isEsFP() {
        return esFP;
    }

    public boolean isEsUni() {
        return esUni;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public int getId() {
        return id;
    }

    public String getProvincia() {
        return provincia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomEscola);
        dest.writeString(adresaEscola);
    }

    private CentreEscolar(Parcel in) {
        nomEscola = in.readString();
        adresaEscola = in.readString();

    }

    public static final Parcelable.Creator<CentreEscolar> CREATOR = new Parcelable.Creator<CentreEscolar>() {
        public CentreEscolar createFromParcel(Parcel in) {
            return new CentreEscolar(in);
        }

        public CentreEscolar[] newArray(int size) {
            return new CentreEscolar[size];
        }
    };
    @Override
    public int hashCode() {
        int result = nomEscola != null ? nomEscola.hashCode() : 0;
        result = 31 * result + (adresaEscola != null ? adresaEscola.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return nomEscola.compareToIgnoreCase(((CentreEscolar) o).getNomEscola());
    }

    public static final Comparator<CentreEscolar> COMPARATOR_ALPHABETIC =
            new Comparator<CentreEscolar>() {
                @Override
                public int compare(CentreEscolar o1, CentreEscolar o2) {
                    if(o1.getNomEscola().compareToIgnoreCase(o2.getNomEscola()) > 0 ){
                        return -1;
                    }
                    return 1;
                }
            };

    public String getNomEscola() {
        return nomEscola;
    }

    public void setNomEscola(String nomEscola) {
        this.nomEscola = nomEscola;
    }

    public String getAdresaEscola() {
        return adresaEscola;
    }

    public void setAdresaEscola(String adresaEscola) {
        this.adresaEscola = adresaEscola;
    }


    public float getColor() {
        if (esUni) {
            return BitmapDescriptorFactory.HUE_BLUE;
        } else if (esBatx) {
            return BitmapDescriptorFactory.HUE_MAGENTA;
        } else if (esFP){
             return BitmapDescriptorFactory.HUE_GREEN;
        } else if (esESO) {
            return BitmapDescriptorFactory.HUE_ORANGE;
        } else if (esPrimaria) {
            return BitmapDescriptorFactory.HUE_YELLOW;
        } else if (esInfantil) {
            return BitmapDescriptorFactory.HUE_VIOLET;
        }
         return BitmapDescriptorFactory.HUE_ROSE;
    }
}
