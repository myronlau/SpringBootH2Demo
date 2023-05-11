package com.example.springbooth2demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public String getIncidentNumberByDate(String date) {
        List<Incident> incidents = incidentRepository.findByDate(date);
        if (!incidents.isEmpty()) {
            return incidents.get(0).getNumber();
        }
        return null;
    }

}

