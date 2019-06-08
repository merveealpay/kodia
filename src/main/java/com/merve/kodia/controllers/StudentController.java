package com.merve.kodia.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class StudentController {

    @GetMapping("/students")
    public ResponseEntity getStudents()
    {
        return  ResponseEntity.ok().build();
    }

    @PostMapping("/students")
    public ResponseEntity addStudents(RequestEntity request)
    {
        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/students/{id}") //gonderilen id'ye karsılık ogrenci donduren fonksıyon.
    public ResponseEntity getStudentById(@PathVariable("id") int id)
    {
        return ResponseEntity.ok(id);
    }





}
