package it.adesso.awspizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
class PizzeriaControllerTest {

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetOrdine() throws Exception {
        String uri = "/pizzeria/ordine";
        mockMvc.perform(get(uri)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testPutOrdine() throws Exception {
        String uri = "/pizzeria/ordine/3";
        mockMvc.perform(put(uri)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testPutOrdineKO() throws Exception {
        String uri = "/pizzeria/ordine/1";
        mockMvc.perform(put(uri)).andExpect(status().is4xxClientError());
    }

    @Test
    void testNextOrder() throws Exception {
        String uri = "/pizzeria/nextOrder";
        mockMvc.perform(put(uri)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testCompletaOrdine() throws Exception {
        String uri = "/pizzeria/completa/2";
        mockMvc.perform(patch(uri)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testCompletaOrdineKO() throws Exception {
        String uri = "/pizzeria/completa/1";
        mockMvc.perform(patch(uri)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testCancellaOrdine() throws Exception {
        String uri = "/pizzeria/cancella/1";
        mockMvc.perform(delete(uri)).andExpect(status().is2xxSuccessful());
    }

}
