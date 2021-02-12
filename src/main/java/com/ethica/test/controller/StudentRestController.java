package com.ethica.test.controller;

import com.ethica.test.dto.CreateStudentDto;
import com.ethica.test.dto.StudentDto;
import com.ethica.test.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

  public StudentRestController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping(VERSION_1_PATH + UUID_PATH_VARIABLE)
  @ResponseBody
  public StudentDto getStudentById(@PathVariable(name = UUID_VAR_NAME) UUID uuid) {
    return this.studentService.getByUuid(uuid);
  }

  @GetMapping(VERSION_1_PATH)
  @ResponseBody
  public List<StudentDto> getStudents(
      @RequestParam(name = FIRST_NAME_VAR_NAME, required = false) String firstName,
      @RequestParam(name = LAST_NAME_VAR_NAME, required = false) String lastName,
      @RequestParam(name = AGE_VAR_NAME, required = false) Integer age) {
    return this.studentService.getStudents(firstName, lastName, age);
  }

  @PutMapping(VERSION_1_PATH)
  public void updateStudent(@RequestBody StudentDto studentDto) {
    this.studentService.updateStudent(studentDto);
  }

  @PatchMapping(VERSION_1_PATH)
  public void updateStudentFields(@RequestBody StudentDto studentDto) {
    this.studentService.updateStudentFields(studentDto);
  }

  @PostMapping(VERSION_1_PATH)
  public UUID createStudent(@RequestBody CreateStudentDto studentDto) {
    return this.studentService.createStudent(studentDto);
  }

  @DeleteMapping(VERSION_1_PATH + UUID_PATH_VARIABLE)
  public void deleteStudent(@PathVariable(name = UUID_VAR_NAME) UUID uuid) {
    this.studentService.deleteStudentByUuid(uuid);
  }
}
