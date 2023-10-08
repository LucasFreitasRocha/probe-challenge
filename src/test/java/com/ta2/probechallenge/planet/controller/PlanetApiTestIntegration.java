package com.ta2.probechallenge.planet.controller;

import com.ta2.probechallenge.ProbechallengeApplication;
import com.ta2.probechallenge.enums.ResourceName;
import com.ta2.probechallenge.exception.CodeExceptionEnum;
import com.ta2.probechallenge.planet.entity.PlanetEntity;
import com.ta2.probechallenge.planet.repository.PlanetRepositorySql;
import com.ta2.probechallenge.probe.entity.ProbeEntity;
import com.ta2.probechallenge.probe.repository.ProbeRepositorySql;
import org.jetbrains.annotations.NotNull;
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

import static com.ta2.probechallenge.UtilHelper.getContentFile;
import static com.ta2.probechallenge.exception.CodeExceptionEnum.NOT_FOUND;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProbechallengeApplication.class)
@ExtendWith(MockitoExtension.class)
class PlanetApiTestIntegration {

    public static final String MARS = "MARS";
    public static final String VENUS ="VENUS";
    @Autowired
    private PlanetApi planetApi;
    @Autowired
    private PlanetRepositorySql planetRepositorySql;

    @Autowired
    private ProbeRepositorySql probeRepositorySql;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

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
    public void createPlanet() throws Exception {
        mockMvc.perform(post("/planet/v1")
                        .content(getContentFile("mock/createPlanetMars.json"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(MARS))
                .andExpect(jsonPath("$.max_probes_in").value(5))
                .andExpect(jsonPath("$.area").value(5))
                .andExpect(jsonPath("$.probes.length()").value(0));
    }

    @Test
    public void validationCreate() throws Exception {
        mockMvc.perform(post("/planet/v1")
                        .content("""
                                {
                                  "max_probes_in": 0,
                                  "area": 4
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void createPlanetUnavailabe() throws Exception {
        getPlanet(MARS, 5, 5);
        mockMvc.perform(post("/planet/v1")
                        .content("""
                                {
                                  "max_probes_in": 5,
                                  "name": "mars",
                                  "area": 5
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value("/planet/v1"))
                .andExpect(jsonPath("$.errors[0].code").value(CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE.code))
                .andExpect(jsonPath("$.errors[0].message").value(CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE.message.formatted(ResourceName.PLANET.getValue())));
    }

    @Test
    public void dontDelete() throws Exception {
        PlanetEntity entity = getPlanet(MARS, 5, 5);
        ProbeEntity entityProbe = probeRepositorySql.save(ProbeEntity.builder()
                .name("teste")
                .position("N")
                .y(0)
                .x(0)
                .code("t")
                .planet(entity)
                .build());
        entity.getProbes().add(entityProbe);
        planetRepositorySql.save(entity);
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/planet/v1/");
        pathBuilder.append(entity.getId());
        mockMvc.perform(delete(pathBuilder.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value(pathBuilder.toString()))
                .andExpect(jsonPath("$.errors[0].code").value(CodeExceptionEnum.DELETE_UNAVAILABLE.code))
                .andExpect(jsonPath("$.errors[0].message").value(CodeExceptionEnum.DELETE_UNAVAILABLE.message.formatted(ResourceName.PLANET.getValue())));

        mockMvc.perform(get(pathBuilder.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MARS"))
                .andExpect(jsonPath("$.max_probes_in").value(5))
                .andExpect(jsonPath("$.area").value(5))
                .andExpect(jsonPath("$.probes.length()").value(1))
                .andExpect(jsonPath("$.probes[0].id").value(1))
                .andExpect(jsonPath("$.probes[0].name").value("teste"))
                .andExpect(jsonPath("$.probes[0].code").value("t"));


    }

    @Test
    public void deleteOk() throws Exception {

        PlanetEntity entity = getPlanet(MARS, 5, 5);
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/planet/v1/");
        pathBuilder.append(entity.getId());
        mockMvc.perform(delete(pathBuilder.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get(pathBuilder.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.path").value(pathBuilder.toString()))
                .andExpect(jsonPath("$.errors[0].code").value(NOT_FOUND.code))
                .andExpect(jsonPath("$.errors[0].message").value(NOT_FOUND.message.formatted(ResourceName.PLANET.getValue())));

    }

    @Test
    public void list() throws Exception {
        getPlanet(MARS, 5, 5);
        getPlanet(VENUS, 5, 5);

        mockMvc.perform(get("/planet/v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].name").value(MARS))
                .andExpect(jsonPath("$.[1].name").value(VENUS));
    }


    @Test
    public void updateAreaAndMaxProbesIn() throws Exception {
        PlanetEntity entity =  getPlanet(MARS, 5, 5);
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/planet/v1/");
        pathBuilder.append(entity.getId());
        mockMvc.perform(get(pathBuilder.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MARS))
                .andExpect(jsonPath("$.max_probes_in").value(5))
                .andExpect(jsonPath("$.area").value(5));
        mockMvc.perform(patch(pathBuilder.toString())
                        .content("""
                                {
                                  "max_probes_in": 10,
                                  "name": "MARS",
                                  "area": 10
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MARS))
                .andExpect(jsonPath("$.max_probes_in").value(10))
                .andExpect(jsonPath("$.area").value(10));
    }


    @Test
    public void updateBadRequestExceededLimitMaxProbesIn() throws Exception {
        PlanetEntity entity =  getPlanet(MARS, 5, 5);
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/planet/v1/");
        pathBuilder.append(entity.getId());
        System.out.println(entity.toString());
        System.out.println(pathBuilder.toString());
        mockMvc.perform(patch(pathBuilder.toString())
                        .content("""
                                {
                                  "max_probes_in": 11,
                                  "name": "MARS",
                                  "area": 10
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value(pathBuilder.toString()))
                .andExpect(jsonPath("$.errors[0].code").value("Max"))
                .andExpect(jsonPath("$.errors[0].message").value("Max probes in a planet is ten"));
    }

    @Test
    public void updateBadRequestChangeName() throws Exception {
        getPlanet(MARS, 5, 5);
        PlanetEntity venus = getPlanet(VENUS, 5, 5);
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/planet/v1/");
        pathBuilder.append(venus.getId());
        mockMvc.perform(patch(pathBuilder.toString())
                        .content("""
                                {
                                  "max_probes_in": 10,
                                  "name": "MARS",
                                  "area": 10
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.path").value(pathBuilder.toString()))
                .andExpect(jsonPath("$.errors[0].code").value(CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE.code))
                .andExpect(jsonPath("$.errors[0].message").value(CodeExceptionEnum.CREATION_OR_UPDATE_UNAVAILABLE.message.formatted(ResourceName.PLANET.getValue())));
    }


    private PlanetEntity getPlanet(String name, Integer maxProbesIn, Integer area) {
        return planetRepositorySql.save(PlanetEntity.builder()
                .name(name)
                .maxProbesIn(maxProbesIn)
                .area(area)
                .build());
    }
}