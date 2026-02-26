package com.example.defecttracker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DefectDTO {
    @NotBlank
    private String title;

    @Size(max = 500)
    private String description;

    @NotBlank
    private String severity;

    @NotBlank
    private String status;

    // Constructors
    public DefectDTO() {}

    public DefectDTO(String title, String description, String severity, String status) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
