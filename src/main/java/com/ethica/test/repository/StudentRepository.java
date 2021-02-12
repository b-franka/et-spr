package com.ethica.test.repository;

import com.ethica.test.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  Student findByUuid(UUID uuid);

  @Query(
      "SELECT s FROM Student s WHERE (:firstName is null or s.firstName = :firstName) and (:lastName is null"
          + " or s.lastName = :lastName) and (:age is null or s.age = :age)")
  List<Student> findAllByFirstNameAndLastNameAndAge(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("age") Integer age);

  void deleteByUuid(UUID uuid);
}
