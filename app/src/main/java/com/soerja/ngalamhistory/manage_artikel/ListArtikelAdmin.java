package com.soerja.ngalamhistory.manage_artikel;

public class ListArtikelAdmin {

    public String judul_artikel, judul_gambar, kontent,uploader, kategori;

    public ListArtikelAdmin() {
    }

    public ListArtikelAdmin(String judul_artikel, String judul_gambar, String kontent, String uploader, String kategori) {
        this.judul_artikel = judul_artikel;
        this.judul_gambar = judul_gambar;
        this.kontent = kontent;
        this.uploader = uploader;
        this.kategori = kategori;
    }

    public String getJudul_artikel() {
        return judul_artikel;
    }

    public void setJudul_artikel(String judul_artikel) {
        this.judul_artikel = judul_artikel;
    }

    public String getJudul_gambar() {
        return judul_gambar;
    }

    public void setJudul_gambar(String judul_gambar) {
        this.judul_gambar = judul_gambar;
    }

    public String getKontent() {
        return kontent;
    }

    public void setKontent(String kontent) {
        this.kontent = kontent;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
