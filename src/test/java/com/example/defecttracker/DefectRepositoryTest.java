package com.example.defecttracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
public class DefectRepositoryTest {

    @Autowired
    private DefectRepository defectRepository;

    @BeforeEach
    void setUp() {
        // Clear all defects before each test
        defectRepository.deleteAll(); // or clear in-memory if using in-memory DB
    }

    @Test
    public void testSaveAndFindDefect() {
        // Create a new defect
        Defect defect = new Defect("Test Defect", "This is a test defect", "Medium", "Open");

        // Save the defect
        defectRepository.save(defect);

        // Verify the defect was saved
        assertNotNull(defect.getId());

        // Find the defect by ID
        Defect foundDefect = defectRepository.findById(defect.getId()).orElse(null);
        assertNotNull(foundDefect);
        assertEquals("Test Defect", foundDefect.getTitle());
    }

    @Test
    public void testFindAllDefects() {
        // Create multiple defects
        Defect defect1 = new Defect("Defect 1", "Description 1", "Low", "Open");
        Defect defect2 = new Defect("Defect 2", "Description 2", "High", "Closed");

        // Save the defects
        defectRepository.save(defect1);
        defectRepository.save(defect2);

        // Find all defects
        Iterable<Defect> allDefects = defectRepository.findAll();

        // Verify we found the correct number of defects
        assertEquals(2, defectRepository.count());

        // Verify the defects we found match what we saved
        assertTrue(allDefects.iterator().hasNext());
    }
}
