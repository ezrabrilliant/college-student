package com.college.student.application.dto;

/**
 * requirement:
 * - Nomor Induk Mahasiswa
 * - Nama Lengkap
 * - Usia
 */
public class StudentResponse {
    
    private String nomorInduk;
    private String namaLengkap;
    private int usia;
    
    public StudentResponse() {
    }
    
    public StudentResponse(String nomorInduk, String namaLengkap, int usia) {
        this.nomorInduk = nomorInduk;
        this.namaLengkap = namaLengkap;
        this.usia = usia;
    }
    
    public String getNomorInduk() {
        return nomorInduk;
    }
    
    public void setNomorInduk(String nomorInduk) {
        this.nomorInduk = nomorInduk;
    }
    
    public String getNamaLengkap() {
        return namaLengkap;
    }
    
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
    
    public int getUsia() {
        return usia;
    }
    
    public void setUsia(int usia) {
        this.usia = usia;
    }
}
