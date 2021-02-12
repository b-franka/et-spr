package com.ethica.test;

import com.ethica.test.entity.Student;
import com.ethica.test.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
class StudentRepositoryTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private StudentRepository repository;

  @BeforeEach
  public void setUp() {

    repository.deleteAll();

    Student student = new Student();
    student.setLastName("Teszt");
    student.setFirstName("Teszt");
    student.setAge(11);
    entityManager.persist(student);
    Student student1 = new Student();
    student1.setLastName("Kala");
    student1.setFirstName("Pál");
    student1.setAge(17);
    entityManager.persist(student1);
    Student student2 = new Student();
    student2.setLastName("Cserepes");
    student2.setFirstName("Virág");
    student2.setAge(15);
    entityManager.persist(student2);
    Student student3 = new Student();
    student3.setLastName("Patak");
    student3.setFirstName("Part");
    student3.setAge(15);
    entityManager.persist(student3);
    Student student4 = new Student();
    student4.setLastName("Kala2");
    student4.setFirstName("Pál");
    student4.setAge(13);
    entityManager.persist(student4);
  }

  @Test
  void getStudents_all() {
    List<Student> students = repository.findAllByFirstNameAndLastNameAndAge(null, null, null);
    Assertions.assertEquals(5, students.size());
  }

  @Test
  void getStudents_filterByLastName() {
    String lastName = "Kala";
    List<Student> students = repository.findAllByFirstNameAndLastNameAndAge(null, lastName, null);
    Assertions.assertEquals(1, students.size());
    Assertions.assertEquals(lastName, students.get(0).getLastName());
  }

  @Test
  void getStudents_filterByAge() {
    Integer age = 15;
    List<Student> students = repository.findAllByFirstNameAndLastNameAndAge(null, null, age);
    Assertions.assertEquals(2, students.size());
    Assertions.assertEquals(age, students.get(0).getAge());
    Assertions.assertEquals(age, students.get(1).getAge());
  }

  @Test
  void getStudents_filterByFirstName() {
    String firstName = "Pál";
    List<Student> students = repository.findAllByFirstNameAndLastNameAndAge(firstName, null, null);
    Assertions.assertEquals(2, students.size());
    Assertions.assertEquals(firstName, students.get(0).getFirstName());
    Assertions.assertEquals(firstName, students.get(1).getFirstName());
  }

  @Test
  void getStudents_filterByAgeAndFirstName() {
    String firstName = "Pál";
    Integer age = 13;
    List<Student> students = repository.findAllByFirstNameAndLastNameAndAge(firstName, null, age);
    Assertions.assertEquals(1, students.size());
    Assertions.assertEquals(firstName, students.get(0).getFirstName());
    Assertions.assertEquals(age, students.get(0).getAge());
  }

  @Test
  void getStudents_filterByAgeAndFirstNameAndLastName() {
    String firstName = "Pál";
    String lastName = "Kala2";
    Integer age = 13;
    List<Student> students =
        repository.findAllByFirstNameAndLastNameAndAge(firstName, lastName, age);
    Assertions.assertEquals(1, students.size());
    Assertions.assertEquals(firstName, students.get(0).getFirstName());
    Assertions.assertEquals(lastName, students.get(0).getLastName());
    Assertions.assertEquals(age, students.get(0).getAge());
  }

  @Test
  void getStudents_filterByAgeAndFirstNameAndLastName_NotFound() {
    String firstName = "Pál";
    String lastName = "Kala";
    Integer age = 13;
    List<Student> students =
        repository.findAllByFirstNameAndLastNameAndAge(firstName, lastName, age);
    Assertions.assertEquals(0, students.size());
  }

  @Test
  void deleteStudent() {
    Student student = new Student();
    student.setLastName("Teszt");
    student.setFirstName("Teszt");
    student.setAge(11);
    Student persisted = entityManager.persist(student);
    UUID uuid = persisted.getUuid();
    repository.deleteByUuid(uuid);

    Student actual = repository.findByUuid(uuid);

    Assertions.assertNull(actual);
  }

  @Test
  void findByUuid() {
    Student student = new Student();
    student.setLastName("Teszt");
    student.setFirstName("Teszt");
    student.setAge(11);
    Student persisted = entityManager.persist(student);
    UUID uuid = persisted.getUuid();

    Student actual = repository.findByUuid(uuid);

    Assertions.assertNotNull(actual);
  }
}
