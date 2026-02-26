package com.example.defecttracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/defects")
public class DefectController {
    @Autowired
    private DefectService defectService;

    @GetMapping
    public List<Defect> getAllDefects() {
        return defectService.getAllDefects();
    }

    @PostMapping
    public ResponseEntity<Defect> createDefect(@RequestBody Defect defect) {
        Defect createdDefect = defectService.createDefect(defect);
        return ResponseEntity.status(201).body(createdDefect);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Defect> updateDefect(@PathVariable Long id, @RequestBody Defect defectDetails) {
        Defect updatedDefect = defectService.updateDefect(id, defectDetails);
        return ResponseEntity.ok(updatedDefect);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDefect(@PathVariable Long id) {
        defectService.deleteDefect(id);
        return ResponseEntity.noContent().build();
    }
}
