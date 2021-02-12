package com.ethica.test.service;

import com.ethica.test.entity.Student;
import com.ethica.test.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public Student getByUuid(UUID uuid) {
    return this.studentRepository.findByUuid(uuid);
  }

  public List<Student> getStudents(String firstName, String lastName, Integer age) {
    return this.studentRepository.findAllByFirstNameAndLastNameAndAge(firstName, lastName, age);
  }

  public void updateStudent(Student updated) {
    Student student = this.studentRepository.findByUuid(updated.getUuid());
    updated.setId(student.getId());
    this.studentRepository.save(updated);
  }

  public void updateStudentFields(Student student) {
    this.studentRepository.save(student);
  }

  public void deleteStudentByUuid(UUID uuid) {
    this.studentRepository.deleteByUuid(uuid);
  }

  public UUID createStudent(Student student) {
    Student saved = this.studentRepository.save(student);
    return saved.getUuid();
  }
}
