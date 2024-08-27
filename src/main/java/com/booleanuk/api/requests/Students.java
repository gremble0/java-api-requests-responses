package com.booleanuk.api.requests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Students {
  // TODO: Optional
  private final List<Student> students = new ArrayList<>() {
    {
      this.add(new Student("Nathan", "King"));
      this.add(new Student("Dave", "Ames"));
    }
  };

  @PostMapping(value = "/students")
  @ResponseStatus(HttpStatus.CREATED)
  public Student create(@RequestBody Student student) {
    this.students.add(student);

    return student;
  }

  @GetMapping(value = "/students")
  public List<Student> getAll() {
    return this.students;
  }

  @GetMapping(value = "/students/{firstName}")
  public Student getByFirstName(@PathVariable String firstName) {
    return this.students
        .stream()
        .filter(student -> student.getFirstName().equals(firstName))
        .findFirst()
        .orElse(null);
  }

  @PutMapping(value = "students/{firstName}")
  public Student updateByFirstName(@PathVariable String firstName, @RequestBody Student newStudent) {
    return this.students.stream()
        .filter(student -> student.getFirstName().equals(firstName))
        .findFirst()
        .map(oldStudent -> newStudent)
        .orElse(null);
  }

  @DeleteMapping(value = "students/{firstName}")
  public Student deleteByFirstName(@PathVariable String firstName) {
    return this.students.stream()
        .filter(student -> student.getFirstName().equals(firstName))
        .findFirst()
        .map(studentToRemove -> {
          this.students.remove(studentToRemove);
          return studentToRemove;
        })
        .orElse(null);
  }
}
