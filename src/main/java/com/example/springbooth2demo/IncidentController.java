package com.example.springbooth2demo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/incidents")
public class IncidentController {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private IncidentService incidentService;

    @GetMapping("")
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable("id") Long id) {
        Optional<Incident> incident = incidentRepository.findById(id);
        if (incident.isPresent()) {
            return ResponseEntity.ok(incident.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<?> createIncident(@RequestBody @Valid Incident incident, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        return ResponseEntity.ok(incidentRepository.save(incident));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIncident(@PathVariable("id") Long id, @RequestBody @Valid Incident updatedIncident, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Optional<Incident> incident = incidentRepository.findById(id);
        if (incident.isPresent()) {
            Incident existingIncident = incident.get();
            existingIncident.setDate(updatedIncident.getDate());
            existingIncident.setNumber(updatedIncident.getNumber());
            return ResponseEntity.ok(incidentRepository.save(existingIncident));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncident(@PathVariable("id") Long id) {
        Optional<Incident> incident = incidentRepository.findById(id);
        if (incident.isPresent()) {
            incidentRepository.delete(incident.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/byDate/{date}")
    public ResponseEntity<?> getIncidentByDate(@PathVariable("date") String date) {
//        List<Incident> incidents = incidentRepository.findByDate(date);
//        if (!incidents.isEmpty()) {
//            return ResponseEntity.ok(incidents.get(0).getNumber());
//        }
//        return ResponseEntity.notFound().build();
        String number = incidentService.getIncidentNumberByDate(date);
        if (number != null) {
            return ResponseEntity.ok(number);
        }
        return ResponseEntity.notFound().build();
    }

}
