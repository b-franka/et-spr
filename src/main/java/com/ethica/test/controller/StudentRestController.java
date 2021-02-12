package com.ethica.test.controller;

import com.ethica.test.dto.CreateStudentDto;
import com.ethica.test.dto.StudentDto;
import com.ethica.test.entity.Student;
import com.ethica.test.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentRestController {

  private static final String VERSION_1_PATH = "/v1";
  private static final String UUID_PATH_VARIABLE = "/{uuid}";
  private static final String UUID_VAR_NAME = "uuid";
  private static final String AGE_VAR_NAME = "age";
  private static final String LAST_NAME_VAR_NAME = "last-name";
  private static final String FIRST_NAME_VAR_NAME = "first-name";

  private final StudentService studentService;
  private final ModelMapper mapper;

  public StudentRestController(StudentService studentService, ModelMapper mapper) {
    this.studentService = studentService;
    this.mapper = mapper;
  }

  @GetMapping(VERSION_1_PATH + UUID_PATH_VARIABLE)
  @ResponseBody
  public ResponseEntity<StudentDto> getStudentById(@PathVariable(name = UUID_VAR_NAME) UUID uuid) {
    Student student = this.studentService.getByUuid(uuid);
    if (student == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(this.mapper.map(student, StudentDto.class), HttpStatus.OK);
  }

  @GetMapping(VERSION_1_PATH)
  @ResponseBody
  public List<StudentDto> getStudents(
      @RequestParam(name = FIRST_NAME_VAR_NAME, required = false) String firstName,
      @RequestParam(name = LAST_NAME_VAR_NAME, required = false) String lastName,
      @RequestParam(name = AGE_VAR_NAME, required = false) Integer age) {
    List<Student> students = this.studentService.getStudents(firstName, lastName, age);
    return students.stream().map(student -> this.mapper.map(student, StudentDto.class)).collect(Collectors.toList());
  }

  @PutMapping(VERSION_1_PATH)
  public void updateStudent(@RequestBody StudentDto studentDto) {
    Student updated = this.mapper.map(studentDto, Student.class);
    this.studentService.updateStudent(updated);
  }

  @PatchMapping(VERSION_1_PATH)
  public void updateStudentFields(@RequestBody StudentDto studentDto) {
    Student student = this.studentService.getByUuid(studentDto.getUuid());
    updateFields(studentDto, student);
    this.studentService.updateStudentFields(student);
  }

  @PostMapping(VERSION_1_PATH)
  public ResponseEntity<UUID> createStudent(@RequestBody CreateStudentDto studentDto) {
    Student student = this.mapper.map(studentDto, Student.class);
    return new ResponseEntity<>(this.studentService.createStudent(student), HttpStatus.OK);
  }

  @DeleteMapping(VERSION_1_PATH + UUID_PATH_VARIABLE)
  public void deleteStudent(@PathVariable(name = UUID_VAR_NAME) UUID uuid) {
    this.studentService.deleteStudentByUuid(uuid);
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
