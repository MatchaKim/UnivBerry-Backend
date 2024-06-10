package com.bezkoder.springjwt.product.controller;

import com.bezkoder.springjwt.product.entity.University;
import com.bezkoder.springjwt.product.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping("/all")
    public ResponseEntity<List<University>> getAllUniversities() {
        List<University> universities = universityRepository.findAll();
        return ResponseEntity.ok(universities);
    }

    @GetMapping("/search")
    public ResponseEntity<List<University>> searchUniversitiesByName(@RequestParam String name) {
        List<University> universities = universityRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok().body(universities);
    }
}
