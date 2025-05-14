package com.example.belajar_spring.model;

public class Jurusan {
    private Long id;
    private String namaJurusan;

    // Constructor
    public Jurusan() {
    }

    public Jurusan(Long id, String namaJurusan) {
        this.id = id;
        this.namaJurusan = namaJurusan;
    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNamaJurusan() {
        return namaJurusan;
    }
    public void setNamaJurusan(String namaJurusan) {
        this.namaJurusan = namaJurusan;
    }
}