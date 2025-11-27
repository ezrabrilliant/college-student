package com.college.student.presentation.controller;

import com.college.student.application.dto.CreateStudentRequest;
import com.college.student.application.dto.StudentResponse;
import com.college.student.application.dto.UpdateStudentRequest;
import com.college.student.application.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller buat Student API;
 * - Terima HTTP request
 * - Panggil Service
 * - Return HTTP response
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    private final StudentService studentService;
    
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    /**
     * GET /api/students
     * Ambil semua data mahasiswa
     */
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    /**
     * GET /api/students/{nomorInduk}
     * Ambil satu data mahasiswa berdasarkan NIM
     */
    @GetMapping("/{nomorInduk}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable String nomorInduk) {
        StudentResponse student = studentService.getStudentById(nomorInduk);
        return ResponseEntity.ok(student);
    }
    
    /**
     * POST /api/students
     * Tambah data mahasiswa baru
     */
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody CreateStudentRequest request) {
        StudentResponse createdStudent = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }
    
    /**
     * PUT /api/students/{nomorInduk}
     * Update data mahasiswa
     */
    @PutMapping("/{nomorInduk}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable String nomorInduk,
            @RequestBody UpdateStudentRequest request) {
        StudentResponse updatedStudent = studentService.updateStudent(nomorInduk, request);
        return ResponseEntity.ok(updatedStudent);
    }
    
    /**
     * DELETE /api/students/{nomorInduk}
     * Hapus data mahasiswa
     */
    @DeleteMapping("/{nomorInduk}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String nomorInduk) {
        studentService.deleteStudent(nomorInduk);
        return ResponseEntity.noContent().build();
    }
}
