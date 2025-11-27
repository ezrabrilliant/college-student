package com.college.student.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.Period;

public class Student {
    
    private String nomorInduk;
    private String namaDepan;
    private String namaBelakang;
    private LocalDate tanggalLahir;
    
    public Student() {
    }
    
    public Student(String nomorInduk, String namaDepan, String namaBelakang, LocalDate tanggalLahir) {
        this.nomorInduk = nomorInduk;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.tanggalLahir = tanggalLahir;
    }
    
    public String getNomorInduk() {
        return nomorInduk;
    }
    
    public void setNomorInduk(String nomorInduk) {
        this.nomorInduk = nomorInduk;
    }
    
    public String getNamaDepan() {
        return namaDepan;
    }
    
    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }
    
    public String getNamaBelakang() {
        return namaBelakang;
    }
    
    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }
    
    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }
    
    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
    
    /**
     * Gabung nama depan dan belakang
     * @JsonIgnore buat mencegah Jackson serialize ke file JSON
     */
    @JsonIgnore
    public String getNamaLengkap() {
        if (namaBelakang == null || namaBelakang.trim().isEmpty()) {
            return namaDepan;
        }
        return namaDepan + " " + namaBelakang;
    }
    
    /**
     * Hitung usia berdasarkan tanggal lahir
     * @JsonIgnore buat mencegah Jackson serialize ke file JSON
     */
    @JsonIgnore
    public int getUsia() {
        if (tanggalLahir == null) {
            return 0;
        }
        return Period.between(tanggalLahir, LocalDate.now()).getYears();
    }
}