package com.ethica.test.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "student")
@Data
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private UUID uuid;

  @NotEmpty
  @Column(name = "first_name")
  private String firstName;

  @NotEmpty
  @Column(name = "last_name")
  private String lastName;

  private Integer age;

  @PrePersist
  private void createUuid() {
    this.uuid = UUID.randomUUID();
  }
}
