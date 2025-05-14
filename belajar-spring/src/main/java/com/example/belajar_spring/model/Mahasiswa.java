package com.example.belajar_spring.model;

public class Mahasiswa {
    private Long id;
    private String nama;
    private Jurusan jurusan;

    // Constructor
    public Mahasiswa() {
    }

    public Mahasiswa(Long id, String nama, Jurusan jurusan) {
        this.id = id;
        this.nama = nama;
        this.jurusan = jurusan;
    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public Jurusan getJurusan() {
        return jurusan;
    }
    public void setJurusan(Jurusan jurusan) {
        this.jurusan = jurusan;
    }
}