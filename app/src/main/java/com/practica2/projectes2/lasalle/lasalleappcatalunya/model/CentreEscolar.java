package com.practica2.projectes2.lasalle.lasalleappcatalunya.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.Comparator;

public class CentreEscolar implements Comparable, Parcelable {

    private String nomEscola;
    private String adresaEscola;
    private boolean[] type = new boolean[6];
    private String descripcio;
    private int id;
    private String provincia;
    private double latitude;
    private double longitude;

    public void setEsInfantil(boolean esInfantil) {
        type[0] = esInfantil;
    }

    public void setEsPrimaria(boolean esPrimaria) {
        type[1] = esPrimaria;
    }

    public void setEsESO(boolean esESO) {
        type[2] = esESO;
    }

    public void setEsBatx(boolean esBatx) {
        type[3] = esBatx;
    }

    public void setEsFP(boolean esFP) {
        type[4] = esFP;
    }

    public void setEsUni(boolean esUni) {
        type[5] = esUni;
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
        return type[0];
    }

    public boolean isEsPrimaria() {
        return type[1];
    }

    public boolean isEsESO() {
        return type[2];

    }

    public boolean isEsBatx() {
        return type[3];
    }

    public boolean isEsFP() {
        return type[4];
    }

    public boolean isEsUni() {
        return type[5];
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

    protected CentreEscolar(Parcel in) {
        nomEscola = in.readString();
        adresaEscola = in.readString();
        type = in.createBooleanArray();
        descripcio = in.readString();
        id = in.readInt();
        provincia = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomEscola);
        dest.writeString(adresaEscola);
        dest.writeBooleanArray(type);
        dest.writeString(descripcio);
        dest.writeInt(id);
        dest.writeString(provincia);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CentreEscolar> CREATOR = new Parcelable.Creator<CentreEscolar>() {
        @Override
        public CentreEscolar createFromParcel(Parcel in) {
            return new CentreEscolar(in);
        }

        @Override
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
        if (type[5]) {
            return BitmapDescriptorFactory.HUE_BLUE;
        } else if (type[4]) {
            return BitmapDescriptorFactory.HUE_MAGENTA;
        } else if (type[3]){
             return BitmapDescriptorFactory.HUE_GREEN;
        } else if (type[2]) {
            return BitmapDescriptorFactory.HUE_ORANGE;
        } else if (type[1]) {
            return BitmapDescriptorFactory.HUE_YELLOW;
        } else if (type[0]) {
            return BitmapDescriptorFactory.HUE_VIOLET;
        }
         return BitmapDescriptorFactory.HUE_ROSE;
    }
}
