package swe.poliwagexample.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import swe.poliwagexample.service.PopulateGraphService;


@WebMvcTest(PopulateGraphController.class)
class PopulateGraphControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PopulateGraphService populateGraphService;

    @Test
    void populateGraphShouldReturnSuccessMessageWhenGraphIsPopulated() throws Exception {

        when(populateGraphService.populateGraph()).thenReturn(true);

        mockMvc.perform(post("/populate-neo4j-graph"))
                .andExpectAll(
                        status().isOk(),
                        content().string("Graph populated successfully")
                );
    }

    @Test
    void populateGraphShouldReturnErrorMessageWhenGraphIsNotPopulated() throws Exception {

        when(populateGraphService.populateGraph()).thenReturn(false);

        mockMvc.perform(post("/populate-neo4j-graph"))
                .andExpectAll(
                        status().isInternalServerError(),
                        content().string("Failed to populate graph")
                );
    }
}