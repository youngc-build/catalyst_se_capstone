package com.example.defecttracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DefectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllDefects() throws Exception {
        mockMvc.perform(get("/api/defects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // @Test
    // public void testCreateDefect() throws Exception {
    //     mockMvc.perform(post("/api/defects").contentType(MediaType.APPLICATION_JSON)
    //             .content("{""title"": "Test Defect", ""description"": "This is a test defect", ""severity"": "Medium", ""status"": "Open"}"))
    //             .andExpect(status().isCreated());
    // }

    // @Test
    // public void testUpdateDefect() throws Exception {
    //     mockMvc.perform(put("/api/defects/1").contentType(MediaType.APPLICATION_JSON)
    //             .content("{""title"": "Updated Defect", ""description"": "This is an updated defect", ""severity"": "High", ""status"": "Closed"}"))
    //             .andExpect(status().isOk());
    // }

    @Test
    public void testDeleteDefect() throws Exception {
        mockMvc.perform(delete("/api/defects/1"))
                .andExpect(status().isNoContent());
    }
}
