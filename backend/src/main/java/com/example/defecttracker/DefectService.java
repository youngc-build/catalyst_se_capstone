package com.example.defecttracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefectService {
    @Autowired
    private DefectRepository defectRepository;

    public List<Defect> getAllDefects() {
        return defectRepository.findAll();
    }

    public Defect createDefect(Defect defect) {
        return defectRepository.save(defect);
    }

    public Defect updateDefect(Long id, Defect defectDetails) {
        Defect defect = defectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Defect with id " + id + " not found"));
        defect.setTitle(defectDetails.getTitle());
        defect.setDescription(defectDetails.getDescription());
        defect.setSeverity(defectDetails.getSeverity());
        defect.setStatus(defectDetails.getStatus());
        defect.setUpdatedAt(LocalDateTime.now());
        return defectRepository.save(defect);
    }

    public void deleteDefect(Long id) {
        defectRepository.deleteById(id);
    }
}
