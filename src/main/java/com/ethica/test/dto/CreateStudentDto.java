package com.ethica.test.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateStudentDto {

  private String firstName;
  private String lastName;
  private Integer age;
}
