package com.college.student.domain.repository;

import com.college.student.domain.entity.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll();
    
    Optional<Student> findById(String nomorInduk);
    
    Student save(Student student);
    
    void deleteById(String nomorInduk);
    
    boolean existsById(String nomorInduk);
}
