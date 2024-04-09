package it.adesso.awspizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.adesso.awspizza.dto.CreaOrdineRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
class ClienteControllerTest {

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
    void testMenu() throws Exception {
        String uri = "/cliente/menu";
        mockMvc.perform(get(uri)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testStatoOrdine() throws Exception {
        String uri = "/cliente/stato/1";
        mockMvc.perform(get(uri)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testStatoOrdineKO() throws Exception {
        String uri = "/cliente/stato/18";
        mockMvc.perform(get(uri)).andExpect(status().is4xxClientError());
    }

    @Test
    void testStatoOrdineKO2() throws Exception {
        String uri = "/cliente/stato/a";
        mockMvc.perform(get(uri)).andExpect(status().is4xxClientError());
    }

    @Test
    void testInfoOrdine() throws Exception {
        String uri = "/cliente/info/1";
        mockMvc.perform(get(uri)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testInfoOrdineKO() throws Exception {
        String uri = "/cliente/info/18";
        mockMvc.perform(get(uri)).andExpect(status().is4xxClientError());
    }

    @Test
    void testInfoOrdineKO2() throws Exception {
        String uri = "/cliente/info/a";
        mockMvc.perform(get(uri)).andExpect(status().is4xxClientError());
    }

    @Test
    void testCreaOrdine() throws Exception {
        String uri = "/cliente/crea";
        CreaOrdineRequestDto dto = new CreaOrdineRequestDto();
        dto.setNote("Test");
        List<Integer> piatti = Arrays.asList(1,2,3);
        dto.setPiatti(piatti);
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testCreaOrdineKO() throws Exception {
        String uri = "/cliente/crea";
        CreaOrdineRequestDto dto = new CreaOrdineRequestDto();
        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void testCancellaOrdineKo() throws Exception {
        String uri = "/cliente/cancella/1";
        mockMvc.perform(delete(uri)).andExpect(status().is5xxServerError());
    }

    @Test
    void testCancellaOrdine() throws Exception {
        String uri = "/cliente/cancella/2";
        mockMvc.perform(delete(uri)).andExpect(status().is2xxSuccessful());
    }

}
