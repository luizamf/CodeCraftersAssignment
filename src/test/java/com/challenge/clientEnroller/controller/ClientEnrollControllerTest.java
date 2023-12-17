package com.challenge.clientEnroller.controller;


import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.service.ClientEnrollService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientEnrollController.class)
class ClientEnrollControllerTest {
    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    @MockBean
    ClientEnrollService clientEnrollService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @Test
    public void checkValidClientData() throws Exception {
        ClientDTO client = new ClientDTO("name", "lastName", "XT123456", "1234567891234", LocalDate.parse("2014-12-17"), LocalDate.parse("2024-12-17"));

        mvc.perform(post("/client/enroll/checkClient")
                        .content(objectMapper.writeValueAsString(client))
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkInvalidClientCNP() throws Exception {
        ClientDTO client = new ClientDTO("name", "lastName", "XT123456", "abc", LocalDate.parse("2014-12-17"), LocalDate.parse("2024-12-17"));

        MvcResult mvcResult = mvc.perform(post("/client/enroll/checkClient")
                        .content(objectMapper.writeValueAsString(client))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(), "{\"CNP\":\"Invalid CNP.\"}");
    }

    @Test
    public void checkInvalidClientDocumentId() throws Exception {
        ClientDTO client = new ClientDTO("name", "lastName", "abc", "1234567891234", LocalDate.parse("2014-12-17"), LocalDate.parse("2024-12-17"));

        MvcResult mvcResult = mvc.perform(post("/client/enroll/checkClient")
                        .content(objectMapper.writeValueAsString(client))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(), "{\"documentID\":\"Invalid document number.\"}");
    }

    @Test
    public void checkInvalidClientInformation() throws Exception {
        ClientDTO client = new ClientDTO("", "", "XT123456", "1234567891234", LocalDate.parse("2014-12-17"), LocalDate.parse("2024-12-17"));

        MvcResult mvcResult = mvc.perform(post("/client/enroll/checkClient")
                        .content(objectMapper.writeValueAsString(client))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(), "{\"firstName\":\"must not be blank\",\"lastName\":\"must not be blank\"}");
    }
}
