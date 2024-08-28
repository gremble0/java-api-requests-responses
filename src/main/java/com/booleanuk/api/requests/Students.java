package com.booleanuk.api.requests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class Students {
  private final List<Student> students = new ArrayList<>() {
    {
      this.add(new Student("Nathan", "King"));
      this.add(new Student("Dave", "Ames"));
    }
  };

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Student create(@RequestBody Student student) {
    this.students.add(student);

    return student;
  }

  @GetMapping
  public List<Student> getAll() {
    return this.students;
  }

  @GetMapping(value = "/{firstName}")
  public Optional<Student> getByFirstName(@PathVariable String firstName) {
    return this.students
        .stream()
        .filter(student -> student.getFirstName().equals(firstName))
        .findFirst();
  }

  @PutMapping(value = "/{firstName}")
  public Optional<Student> updateByFirstName(@PathVariable String firstName, @RequestBody Student newStudent) {
    return this.students.stream()
        .filter(student -> student.getFirstName().equals(firstName))
        .findFirst()
        .map(oldStudent -> {
          oldStudent.setLastName(newStudent.getLastName());
          return newStudent;
        });
  }

  @DeleteMapping(value = "/{firstName}")
  public Optional<Student> deleteByFirstName(@PathVariable String firstName) {
    return this.students.stream()
        .filter(student -> student.getFirstName().equals(firstName))
        .findFirst()
        .map(studentToRemove -> {
          this.students.remove(studentToRemove);
          return studentToRemove;
        });
  }
}
