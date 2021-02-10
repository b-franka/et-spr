package com.ethica.test.repository;

import com.ethica.test.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Long, Student> {

    Optional<Student> FindByUuid(UUID uuid);
    List<Student> FindAllByAgeOrLastNameOrFirstName(int age);

}
