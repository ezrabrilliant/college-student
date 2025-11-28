package com.college.student.application.dto;

/**
 * Response DTO for Student detail (used for edit form)
 * Contains raw data fields only
 */
public class StudentDetailResponse {
    
    private final String nomorInduk;
    private final String namaDepan;
    private final String namaBelakang;
    private final String tanggalLahir;
    
    public StudentDetailResponse(String nomorInduk, String namaDepan, 
                                  String namaBelakang, String tanggalLahir) {
        this.nomorInduk = nomorInduk;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.tanggalLahir = tanggalLahir;
    }
    
    public String getNomorInduk() {
        return nomorInduk;
    }
    
    public String getNamaDepan() {
        return namaDepan;
    }
    
    public String getNamaBelakang() {
        return namaBelakang;
    }
    
    public String getTanggalLahir() {
        return tanggalLahir;
    }
}
