package com.ethica.test.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateStudentDto {

  @NotNull @NotEmpty private String firstName;

  @NotNull @NotEmpty private String lastName;
  private Integer age;
}