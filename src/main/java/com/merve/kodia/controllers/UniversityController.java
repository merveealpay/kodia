package com.merve.kodia.controllers;

import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UniversityController {

    @GetMapping("/universities")
    public ResponseEntity getUniversities()
    {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/universities/{id}")
    public ResponseEntity getUniversityById(@PathVariable("id") int id)
    {
        return ResponseEntity.ok(id);
    }

    




}
