package com.college.student.infrastructure.persistence;

import com.college.student.domain.entity.Student;
import com.college.student.domain.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JsonStudentRepository implements StudentRepository {
    
    private final ObjectMapper objectMapper;
    private final String dataFilePath;
    private List<Student> students;
    
    public JsonStudentRepository(@Value("${app.data.path}") String dataFilePath) {
        this.dataFilePath = dataFilePath;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.students = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        loadFromFile();
    }
    
    private void loadFromFile() {
        try {
            File file = new File(dataFilePath);
            if (file.exists()) {
                students = objectMapper.readValue(file, new TypeReference<List<Student>>() {});
            } else {
                students = new ArrayList<>();
                saveToFile();
            }
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
            students = new ArrayList<>();
        }
    }

    private void saveToFile() {
        try {
            File file = new File(dataFilePath);
            // Create parent directories if not exists
            file.getParentFile().mkdirs();
            objectMapper.writeValue(file, students);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }
    
    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }
    
    @Override
    public Optional<Student> findById(String nomorInduk) {
        return students.stream()
                .filter(s -> s.getNomorInduk().equals(nomorInduk))
                .findFirst();
    }
    
    @Override
    public Student save(Student student) {
        Optional<Student> existingStudent = findById(student.getNomorInduk());
        
        if (existingStudent.isPresent()) {
            int index = students.indexOf(existingStudent.get());
            students.set(index, student);
        } else {
            students.add(student);
        }
        
        saveToFile();
        return student;
    }
    
    @Override
    public void deleteById(String nomorInduk) {
        students.removeIf(s -> s.getNomorInduk().equals(nomorInduk));
        saveToFile();
    }
    
    @Override
    public boolean existsById(String nomorInduk) {
        return students.stream()
                .anyMatch(s -> s.getNomorInduk().equals(nomorInduk));
    }
}
