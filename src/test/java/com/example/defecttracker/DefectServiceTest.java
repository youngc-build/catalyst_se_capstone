package com.example.defecttracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class DefectServiceTest {

    @Autowired
    private DefectService defectService;

    @Test
    public void testCreateDefect() {
        Defect defect = new Defect("Test Defect", "This is a test defect", "Medium", "Open");
        Defect createdDefect = defectService.createDefect(defect);
        assertNotNull(createdDefect.getId());
        assertEquals("Test Defect", createdDefect.getTitle());
    }

    @Test
    public void testUpdateDefect() {
        Defect defect = new Defect("Test Defect", "This is a test defect", "Medium", "Open");
        Defect createdDefect = defectService.createDefect(defect);
        
        // Update the defect
        createdDefect.setTitle("Updated Defect");
        createdDefect.setDescription("This is an updated defect");
        createdDefect.setSeverity("High");
        createdDefect.setStatus("Closed");
        
        Defect updatedDefect = defectService.updateDefect(createdDefect.getId(), createdDefect);
        assertEquals("Updated Defect", updatedDefect.getTitle());
        assertEquals("High", updatedDefect.getSeverity());
        assertEquals("Closed", updatedDefect.getStatus());
    }

    // @Test
    // public void testDeleteDefect() {
    //     Defect defect = new Defect("Test Defect", "This is a test defect", "Medium", "Open");
    //     Defect createdDefect = defectService.createDefect(defect);
        
    //     // Delete the defect
    //     defectService.deleteDefect(createdDefect.getId());
        
    //     // Verify the defect is deleted
    //     try {
    //         defectService.getDefectById(createdDefect.getId());
    //         fail("Expected exception for non-existent defect");
    //     } catch (IllegalArgumentException e) {
    //         assertEquals("Defect with id " + createdDefect.getId() + " not found", e.getMessage());
    //     }
    // }
}
