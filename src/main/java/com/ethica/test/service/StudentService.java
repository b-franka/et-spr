package com.ethica.test.service;

import com.ethica.test.dto.CreateStudentDto;
import com.ethica.test.dto.StudentDto;
import com.ethica.test.entity.Student;
import com.ethica.test.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

  private final StudentRepository studentRepository;
  private final ModelMapper mapper;

  public StudentService(StudentRepository studentRepository, ModelMapper mapper) {
    this.studentRepository = studentRepository;
    this.mapper = mapper;
  }

  public StudentDto getByUuid(UUID uuid) {
    Logger.getLogger(StudentService.class.getName())
        .log(Level.INFO, this.studentRepository.findByUuid(uuid).toString());
    Student student = this.studentRepository.findByUuid(uuid);
    return this.mapper.map(student, StudentDto.class);
  }

  public List<StudentDto> getStudents(String firstName, String lastName, Integer age) {
    List<Student> students =
        this.studentRepository.findAllByFirstNameAndLastNameAndAge(firstName, lastName, age);
    return students.stream()
        .map(s -> this.mapper.map(s, StudentDto.class))
        .collect(Collectors.toList());
  }

  public void updateStudent(StudentDto studentDto) {
    Student student = this.studentRepository.findByUuid(studentDto.getUuid());
    Student updated = this.mapper.map(studentDto, Student.class);
    updated.setId(student.getId());
    this.studentRepository.save(updated);
  }

  public void updateStudentFields(StudentDto studentDto) {
    Student student = this.studentRepository.findByUuid(studentDto.getUuid());
    updateFields(studentDto, student);
    this.studentRepository.save(student);
  }

  public void deleteStudentByUuid(UUID uuid) {
    this.studentRepository.deleteByUuid(uuid);
  }

  public UUID createStudent(CreateStudentDto studentDto) {
    Student student = this.mapper.map(studentDto, Student.class);
    Student saved = this.studentRepository.save(student);
    return saved.getUuid();
  }

  private void updateFields(StudentDto dto, Student studentEntity) {
    Integer age = dto.getAge();
    if (age != null) {
      studentEntity.setAge(age);
    }
    String firstName = dto.getFirstName();
    if (firstName != null && !firstName.isEmpty()) {
      studentEntity.setFirstName(firstName);
    }
    String lastName = dto.getLastName();
    if (lastName != null && !lastName.isEmpty()) {
      studentEntity.setLastName(lastName);
    }
  }
}
