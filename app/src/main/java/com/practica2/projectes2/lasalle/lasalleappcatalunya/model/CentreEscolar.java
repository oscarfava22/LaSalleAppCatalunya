package com.practica2.projectes2.lasalle.lasalleappcatalunya.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

public class CentreEscolar implements Comparable,Parcelable{

    private String nomEscola;
    private String adresaEscola;
    private String estudisImpartits;

    public CentreEscolar(String nomEscola, String adresaEscola, String estudisImpartits) {
        this.nomEscola = nomEscola;
        this.adresaEscola = adresaEscola;
        this.estudisImpartits = estudisImpartits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomEscola);
        dest.writeString(adresaEscola);
        dest.writeString(estudisImpartits);
    }

    private CentreEscolar(Parcel in) {
        nomEscola = in.readString();
        adresaEscola = in.readString();
        estudisImpartits = in.readString();

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

    public String getEstudisImpartits() {
        return estudisImpartits;
    }

    public void setEstudisImpartits(String estudisImpartits) {
        this.estudisImpartits = estudisImpartits;
    }
}
