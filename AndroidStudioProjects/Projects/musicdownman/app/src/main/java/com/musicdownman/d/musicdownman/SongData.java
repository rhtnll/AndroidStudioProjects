package com.musicdownman.d.musicdownman;

/**
 * Created by zzy on 2018/2/14.
 */

public class SongData {
    private String Songname;
    private String Singername;
    private String size_128;
    private String size_320;
    private String size_aac;
    private String size_ape;
    private String size_dts;
    private String size_flac;
    private String size_ogg;
    private String size_try;
    private String album_mid;
    private String media_mid;

   /* public SongData(String songname, String songername, String size_128, String size_320, String size_aac, String size_ape, String size_dts, String size_flac, String size_ogg, String size_try, String album_mid, String media_mid) {
        Songname = songname;
        Songername = songername;
        this.size_128 = size_128;
        this.size_320 = size_320;
        this.size_aac = size_aac;
        this.size_ape = size_ape;
        this.size_dts = size_dts;
        this.size_flac = size_flac;
        this.size_ogg = size_ogg;
        this.size_try = size_try;
        this.album_mid = album_mid;
        this.media_mid = media_mid;
    }*/

    public String getSongname() {
        return Songname;
    }

    public void setSongname(String songname) {
        Songname = songname;
    }

    public String getSingername() {
        return Singername;
    }

    public void setSingername(String songername) {
        Singername = songername;
    }

    public String getSize_128() {
        return size_128;
    }

    public void setSize_128(String size_128) {
        this.size_128 = size_128;
    }

    public String getSize_320() {
        return size_320;
    }

    public void setSize_320(String size_320) {
        this.size_320 = size_320;
    }

    public String getSize_aac() {
        return size_aac;
    }

    public void setSize_aac(String size_aac) {
        this.size_aac = size_aac;
    }

    public String getSize_ape() {
        return size_ape;
    }

    public void setSize_ape(String size_ape) {
        this.size_ape = size_ape;
    }

    public String getSize_dts() {
        return size_dts;
    }

    public void setSize_dts(String size_dts) {
        this.size_dts = size_dts;
    }

    public String getSize_flac() {
        return size_flac;
    }

    public void setSize_flac(String size_flac) {
        this.size_flac = size_flac;
    }

    public String getSize_ogg() {
        return size_ogg;
    }

    public void setSize_ogg(String size_ogg) {
        this.size_ogg = size_ogg;
    }

    public String getSize_try() {
        return size_try;
    }

    public void setSize_try(String size_try) {
        this.size_try = size_try;
    }

    public String getAlbum_mid() {
        return album_mid;
    }

    public void setAlbum_mid(String album_mid) {
        this.album_mid = album_mid;
    }

    public String getMedia_mid() {
        return media_mid;
    }

    public void setMedia_mid(String media_mid) {
        this.media_mid = media_mid;
    }
}
