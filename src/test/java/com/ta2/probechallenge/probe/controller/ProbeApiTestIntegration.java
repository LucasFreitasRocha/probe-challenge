package com.ta2.probechallenge.probe.controller;

import com.ta2.probechallenge.ProbechallengeApplication;
import com.ta2.probechallenge.exception.CodeExceptionEnum;
import com.ta2.probechallenge.probe.dto.out.ProbeDto;
import com.ta2.probechallenge.probe.entity.ProbeEntity;
import com.ta2.probechallenge.probe.repository.ProbeRepositorySql;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.ta2.probechallenge.UtilHelper.getContentFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProbechallengeApplication.class)
@ExtendWith(MockitoExtension.class)
class ProbeApiTestIntegration {

    @Autowired
    WebApplicationContext webApplicationContext;


    @Autowired
    ProbeRepositorySql probeRepositorySql;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void setDown(){
        probeRepositorySql.deleteAll();
    }


    @Test
    void create() throws Exception {
        mockMvc.perform(post("/probe/v1")
                        .content(getContentFile("mock/createProbeMarsRover.json"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("mars rover"))
                .andExpect(jsonPath("$.code").value("MR"))
                .andExpect(jsonPath("$.x").value(0))
                .andExpect(jsonPath("$.y").value(0))
                .andExpect(jsonPath("$.position").value("N"));
    }

    @Test
    void createUnavalible() throws Exception {
        probeRepositorySql.save(ProbeEntity.builder()
                .name("test unavalible")
                .code("TU")
                .build());
        mockMvc.perform(post("/probe/v1")
                        .content("""
                                {
                                  "name": "teste unavalible"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value("/probe/v1"))
                .andExpect(jsonPath("$.errors[0].code").value(CodeExceptionEnum.CREATION_UNAVAILABLE.toString()))
                .andExpect(jsonPath("$.errors[0].message").value(CodeExceptionEnum.CREATION_UNAVAILABLE.message));
    }


    @Test
    public void NotFound() throws Exception {
        mockMvc.perform(get("/probe/v1/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.path").value("/probe/v1/99"))
                .andExpect(jsonPath("$.errors[0].code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.errors[0].message").value("Not found probe"));
    }

    @Test
    public void delete() throws Exception {
        ProbeEntity probeEntity = probeRepositorySql.save(ProbeEntity.builder()
                .name("test delete")
                .code("TD")
                .build());
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/probe/v1/");
        pathBuilder.append(probeEntity.getId());
        mockMvc.perform(get(pathBuilder.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    public void probe1() throws Exception {
        ProbeEntity probeEntity = probeRepositorySql.save(ProbeEntity.builder()
                .name("test probe 1")
                .x(1)
                .y(2)
                .position("N")
                .code("TP1")
                .build());
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/probe/v1/");
        pathBuilder.append(probeEntity.getId());
        mockMvc.perform(post(pathBuilder.toString())
                        .content("""
                                {
                                  "command": "LMLMLMLMM"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test probe 1"))
                .andExpect(jsonPath("$.code").value("TP1"))
                .andExpect(jsonPath("$.x").value(1))
                .andExpect(jsonPath("$.y").value(3))
                .andExpect(jsonPath("$.position").value("N"));
    }


    @Test
    public void probe2() throws Exception {
        ProbeEntity probeEntity = probeRepositorySql.save(ProbeEntity.builder()
                .name("test probe 2")
                .x(3)
                .y(3)
                .position("E")
                .code("TP2")
                .build());
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/probe/v1/");
        pathBuilder.append(probeEntity.getId());
        mockMvc.perform(post(pathBuilder.toString())
                        .content("""
                                {
                                  "command": "MMRMMRMRRML"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test probe 2"))
                .andExpect(jsonPath("$.code").value("TP2"))
                .andExpect(jsonPath("$.x").value(5))
                .andExpect(jsonPath("$.y").value(1))
                .andExpect(jsonPath("$.position").value("N"));
    }


    @Test
    void updateName() throws Exception {
        ProbeEntity probeEntity = probeRepositorySql.save(ProbeEntity.builder()
                .name("test probe 1")
                .x(0)
                .y(0)
                .position("N")
                .code("TP1")
                .build());

        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/probe/v1/");
        pathBuilder.append(probeEntity.getId());
        mockMvc.perform(patch(pathBuilder.toString())
                        .content(getContentFile("mock/createProbeMarsRover.json"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mars rover"))
                .andExpect(jsonPath("$.code").value("MR"))
                .andExpect(jsonPath("$.x").value(0))
                .andExpect(jsonPath("$.y").value(0))
                .andExpect(jsonPath("$.position").value("N"));

    }
    @Test
    void size2() throws Exception {
        probeRepositorySql.save(ProbeEntity.builder()
                .name("test probe 1")
                .x(1)
                .y(2)
                .position("N")
                .code("TP1")
                .build());
        probeRepositorySql.save(ProbeEntity.builder()
                .name("test probe 2")
                .x(3)
                .y(3)
                .position("E")
                .code("TP2")
                .build());
         mockMvc.perform(get("/probe/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$.length()").value(2));

    }


}