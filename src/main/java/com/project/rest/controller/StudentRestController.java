package com.project.rest.controller;

import com.project.model.Student;
import com.project.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api")
public class StudentRestController {
    private static StudentService studentService = null; // dodane, ponieważ metoda z pageable nie działała prawidłowo

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/studenci/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer studentId) {// @PathVariable oznacza, że wartość
        return ResponseEntity.of(studentService.getStudent(studentId)); // parametru przekazywana jest w ścieżce
    }
    @PostMapping(path = "/studenci")
    public ResponseEntity<Void> createStudent(@Valid @RequestBody Student student) {// @RequestBody oznacza, że dane
        // projektu (w formacie JSON) są
        Student createdStudent = studentService.setStudent(student); // przekazywane w ciele żądania
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // link wskazujący utworzony projekt
                .path("/{studentId}").buildAndExpand(createdStudent.getStudentId()).toUri();
        return ResponseEntity.created(location).build(); // zwracany jest kod odpowiedzi 201 - Created
    } // z linkiem location w nagłówku

    @PutMapping("/studenci/{studentId}")
    public ResponseEntity<Void> updateStudent(@Valid @RequestBody Student student,
                                              @PathVariable Integer studentId) {
        return studentService.getStudent(studentId)
                .map(p -> {
                    studentService.setStudent(student);
                    return new ResponseEntity<Void>(HttpStatus.OK); // 200 (można też zwracać 204 - No content)
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }
    @DeleteMapping("/studenci/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId) {
        return studentService.getStudent(studentId).map(p -> {
            studentService.deleteStudent(studentId);
            return new ResponseEntity<Void>(HttpStatus.OK); // 200
        }).orElseGet(() -> ResponseEntity.notFound().build()); // 404 - Not found
    }
    //Przykład żądania wywołującego metodę: http://localhost:8080/api/projekty?page=0&size=10&sort=nazwa,desc
    @GetMapping(value = "/studenci")
    public static Page<Student> getStudenci(Pageable pageable) { // @RequestHeader HttpHeaders headers – jeżeli potrzebny
        return studentService.getStudenci(pageable); // byłby nagłówek, wystarczy dodać drugą zmienną z adnotacją
    }
}
