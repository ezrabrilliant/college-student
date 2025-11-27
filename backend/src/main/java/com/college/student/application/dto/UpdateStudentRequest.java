package com.college.student.application.dto;

/**
 * buat update existing student.
 */
public class UpdateStudentRequest {
    
    private String namaDepan;
    private String namaBelakang;
    private String tanggalLahir; // Format: "yyyy-MM-dd"
    
    public UpdateStudentRequest() {
    }
    
    public UpdateStudentRequest(String namaDepan, String namaBelakang, String tanggalLahir) {
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.tanggalLahir = tanggalLahir;
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
