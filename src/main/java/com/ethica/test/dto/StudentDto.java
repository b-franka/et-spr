package com.ethica.test.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentDto {

  private UUID uuid;
  private String firstName;
  private String lastName;
  private Integer age;
}
