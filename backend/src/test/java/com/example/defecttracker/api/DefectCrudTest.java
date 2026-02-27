package com.example.defecttracker.api;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.equalTo;

public class DefectCrudTest {

    @Test
    public void testCreateDefectItem() {
        // Setup base URL and request specification
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification request = RestAssured.given();

        ObjectMapper mapper = new ObjectMapper();

        // Build JSON as an ObjectNode instead of raw String
        ObjectNode defectJson = mapper.createObjectNode();
        defectJson.put("title", "Sample Defect");
        defectJson.put("description", "This is a test defect created via API");
        defectJson.put("severity", "Medium");
        defectJson.put("priority", "High");
        defectJson.put("assignedTo", "test-user@example.com");
        defectJson.put("status", "Open");

        // Set request headers and body
        request.header("Content-Type", "application/json");
        request.body(defectJson);

        // Perform POST request to /defects endpoint
        Response postResponse = request.post("/api/defects");

        postResponse.then().assertThat()
                .statusCode(201)
                .contentType("application/json");
        postResponse.then().assertThat().body("title", equalTo("Sample Defect"));

        // Perform GET request to /defects endpoint
        Response getResponse = request.get("/api/defects");

        getResponse.then().assertThat()
                .statusCode(200)
                .contentType("application/json");

        // Perform PUT request to /defects endpoint        
        defectJson.put("title", "New Title");
        Long id = postResponse.jsonPath().getLong("id");

        Response putResponse = request
                .header("Content-Type", "application/json")
                .body(defectJson.toString())
                .put("/api/defects/" + id);

        putResponse.then()
                .statusCode(200)
                .contentType("application/json")
                .body("title", equalTo("New Title"));   
        
        // Perform DEL request to /defects endpoint
        Response delResponse = request
                .header("Content-Type", "application/json")
                .body(defectJson.toString())
                .delete("/api/defects/" + id);

        delResponse.then()
                .statusCode(204)
                .contentType("");
        
        Response getNewResponse = request.get("/api/defects/1");

        getNewResponse.then().assertThat()
                .statusCode(405)
                .contentType("");
    }
}