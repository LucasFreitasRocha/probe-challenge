package com.ta2.probechallenge.probe.controller;

import com.ta2.probechallenge.ProbechallengeApplication;
import com.ta2.probechallenge.enums.ResourceName;
import com.ta2.probechallenge.exception.CodeExceptionEnum;
import com.ta2.probechallenge.planet.entity.PlanetEntity;
import com.ta2.probechallenge.planet.repository.PlanetRepositorySql;
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

import java.util.UUID;

import static com.ta2.probechallenge.exception.CodeExceptionEnum.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProbechallengeApplication.class)
@ExtendWith(MockitoExtension.class)
class ProbeApiTestIntegration {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Autowired
    ProbeRepositorySql probeRepositorySql;
    @Autowired
    PlanetRepositorySql planetRepositorySql;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void setDown() {
        probeRepositorySql.deleteAll();
        planetRepositorySql.deleteAll();
    }


    @Test
    void create() throws Exception {
        PlanetEntity planet = getPlanet("mars");
        mockMvc.perform(post("/probe/v1")
                        .content("""
                                {
                                  "planet_id": "%s",
                                  "name": "mars rover"
                                }
                                """.formatted(planet.getId()))
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
    void createProbeOnPlanetThatNotExist() throws Exception {
        mockMvc.perform(post("/probe/v1")
                        .content("""
                                {
                                  "planet_id": "%s",
                                  "name": "mars rover"
                                }
                                """.formatted(UUID.randomUUID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value("/probe/v1"))
                .andExpect(jsonPath("$.errors[0].code").value(NOT_FOUND.code))
                .andExpect(jsonPath("$.errors[0].message").value(NOT_FOUND.message.formatted(ResourceName.PLANET.getValue())));
    }

    @Test
    void createProbeOnPlanetThatHasReachedMaxProbesOn() throws Exception {
        PlanetEntity planetEntity =planetRepositorySql.save( PlanetEntity.builder()
                .name("teste")
                .maxProbesIn(0)
                .build());
        mockMvc.perform(post("/probe/v1")
                        .content("""
                                {
                                  "planet_id": "%s",
                                 "name": "mars rover"
                                }
                                """.formatted(planetEntity.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value("/probe/v1"))
                .andExpect(jsonPath("$.errors[0].message").value(CodeExceptionEnum.MAX_PROBES_ON_PLANET.message))
                .andExpect(jsonPath("$.errors[0].code").value(CodeExceptionEnum.MAX_PROBES_ON_PLANET.code));
    }


    @Test
    void validationCreateNotUUID() throws Exception {
        mockMvc.perform(post("/probe/v1")
                        .content("""
                                {
                                  "planet_id": "teste",
                                  "name": "teste"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value("/probe/v1"))
                .andExpect(jsonPath("$.errors[0].code").value("Pattern"))
                .andExpect(jsonPath("$.errors[0].message").value("Send a valid UUID"));
    }

    @Test
    void createUnavalible() throws Exception {
        PlanetEntity planetEntity = getPlanet("MARS");
        probeRepositorySql.save(ProbeEntity.builder()
                .name("test unavalible")
                .planet(planetEntity)
                .code("TU")
                .build());
        mockMvc.perform(post("/probe/v1")
                        .content("""
                                {
                                  "planet_id": "%s",
                                  "name": "teste unavalible"
                                }
                                """.formatted(planetEntity.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value("/probe/v1"))
                .andExpect(jsonPath("$.errors[0].code").value(CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE.code))
                .andExpect(jsonPath("$.errors[0].message").value(CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE.message.formatted(ResourceName.PROBE.getValue())));
    }


    @Test
    public void NotFound() throws Exception {
        mockMvc.perform(get("/probe/v1/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.path").value("/probe/v1/99"))
                .andExpect(jsonPath("$.errors[0].code").value(NOT_FOUND.code))
                .andExpect(jsonPath("$.errors[0].message").value(NOT_FOUND.message.formatted(ResourceName.PROBE.getValue())));
    }

    @Test
    public void deleteTest() throws Exception {
        ProbeEntity probeEntity = probeRepositorySql.save(ProbeEntity.builder()
                .name("test delete")
                .planet(getPlanet("mars"))
                .code("TD")
                .build());
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/probe/v1/");
        pathBuilder.append(probeEntity.getId());
        mockMvc.perform(delete(pathBuilder.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get(pathBuilder.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void probe1() throws Exception {
        ProbeEntity probeEntity = probeRepositorySql.save(ProbeEntity.builder()
                .name("test probe 1")
                .x(1)
                .y(2)
                .position("N")
                .planet(getPlanet("MARS"))
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
                .planet(getPlanet("MARS"))
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
                .planet(getPlanet("MARS"))
                .x(0)
                .y(0)
                .position("N")
                .code("TP1")
                .build());

        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/probe/v1/");
        pathBuilder.append(probeEntity.getId());
        mockMvc.perform(patch(pathBuilder.toString())
                        .content("""
                                {
                                  "name": "mars rover"
                                }
                                """)
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
        PlanetEntity planetEntity = getPlanet("MARS");
        probeRepositorySql.save(ProbeEntity.builder()
                .name("test probe 1")
                .x(1)
                .y(2)
                .position("N")
                .planet(planetEntity)
                .code("TP1")
                .build());
        probeRepositorySql.save(ProbeEntity.builder()
                .name("test probe 2")
                .x(3)
                .y(3)
                .position("E")
                .planet(planetEntity)
                .code("TP2")
                .build());
        mockMvc.perform(get("/probe/v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }

    private PlanetEntity getPlanet(String name) {
        return planetRepositorySql.save(
                PlanetEntity.builder()
                        .area(5)
                        .maxProbesIn(5)
                        .name(name)
                        .build()
        );
    }
}