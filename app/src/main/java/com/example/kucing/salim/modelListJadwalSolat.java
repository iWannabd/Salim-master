package com.example.kucing.salim;

/**
 * Created by kucing on 10/01/16.
 */
public class modelListJadwalSolat {
    private String solatna;
    private  String waktuna;
    private int image;

    public void setSolatna(String solatna){
        this.solatna = solatna;
    }

    public void setWaktuna(String waktuna){
        this.waktuna = waktuna;
    }

    public void setImage(int gambar){
        image = gambar;
    }

    public int getImage() {
        return image;
    }

    public String getSolatna() {
        return solatna;
    }

    public String getWaktuna() {
        return waktuna;
    }
}
