package com.college.student.application.dto;

/**
 * buat create new student.
 */
public class CreateStudentRequest {
    
    private String nomorInduk;
    private String namaDepan;
    private String namaBelakang;
    private String tanggalLahir; // Format: "yyyy-MM-dd"
    
    public CreateStudentRequest() {
    }
    
    public CreateStudentRequest(String nomorInduk, String namaDepan, String namaBelakang, String tanggalLahir) {
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
    
    public String getTanggalLahir() {
        return tanggalLahir;
    }
    
    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
