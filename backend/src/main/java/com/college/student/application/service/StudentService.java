package com.college.student.application.service;

import com.college.student.application.dto.CreateStudentRequest;
import com.college.student.application.dto.StudentResponse;
import com.college.student.application.dto.UpdateStudentRequest;
import com.college.student.domain.entity.Student;
import com.college.student.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class buat Student business logic.
 */
@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    /**
     * Get all students and convert to response DTO
     */
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get student by Nomor Induk
     */
    public StudentResponse getStudentById(String nomorInduk) {
        Student student = studentRepository.findById(nomorInduk)
                .orElseThrow(() -> new RuntimeException("Student not found with NIM: " + nomorInduk));
        return toResponse(student);
    }
    
    /**
     * Create a new student
     */
    public StudentResponse createStudent(CreateStudentRequest request) {
        // Check if NIM already exists
        if (studentRepository.existsById(request.getNomorInduk())) {
            throw new RuntimeException("Student with NIM " + request.getNomorInduk() + " already exists");
        }
        
        // Validate required fields
        if (request.getNomorInduk() == null || request.getNomorInduk().trim().isEmpty()) {
            throw new RuntimeException("Nomor Induk is required");
        }
        if (request.getNamaDepan() == null || request.getNamaDepan().trim().isEmpty()) {
            throw new RuntimeException("Nama Depan is required");
        }
        if (request.getTanggalLahir() == null || request.getTanggalLahir().trim().isEmpty()) {
            throw new RuntimeException("Tanggal Lahir is required");
        }
        
        // Convert request to entity
        Student student = new Student(
                request.getNomorInduk(),
                request.getNamaDepan(),
                request.getNamaBelakang(),
                LocalDate.parse(request.getTanggalLahir(), dateFormatter)
        );
        
        // Save and return response
        Student savedStudent = studentRepository.save(student);
        return toResponse(savedStudent);
    }
    
    /**
     * Update existing student
     */
    public StudentResponse updateStudent(String nomorInduk, UpdateStudentRequest request) {
        // Check if student exists
        Student existingStudent = studentRepository.findById(nomorInduk)
                .orElseThrow(() -> new RuntimeException("Student not found with NIM: " + nomorInduk));
        
        // Update fields
        if (request.getNamaDepan() != null && !request.getNamaDepan().trim().isEmpty()) {
            existingStudent.setNamaDepan(request.getNamaDepan());
        }
        existingStudent.setNamaBelakang(request.getNamaBelakang()); // Can be null
        if (request.getTanggalLahir() != null && !request.getTanggalLahir().trim().isEmpty()) {
            existingStudent.setTanggalLahir(LocalDate.parse(request.getTanggalLahir(), dateFormatter));
        }
        
        // Save and return response
        Student updatedStudent = studentRepository.save(existingStudent);
        return toResponse(updatedStudent);
    }
    
    /**
     * Delete student by Nomor Induk
     */
    public void deleteStudent(String nomorInduk) {
        // Check if student exists
        if (!studentRepository.existsById(nomorInduk)) {
            throw new RuntimeException("Student not found with NIM: " + nomorInduk);
        }
        studentRepository.deleteById(nomorInduk);
    }
    
    /**
     * Convert Student entity to StudentResponse DTO
     */
    private StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getNomorInduk(),
                student.getNamaLengkap(),
                student.getUsia()
        );
    }
}
