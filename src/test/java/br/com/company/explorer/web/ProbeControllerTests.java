package br.com.company.explorer.web;

import br.com.company.explorer.ExplorerApplication;
import br.com.company.explorer.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Fábio Siqueira on 12/23/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExplorerApplication.class)
@WebAppConfiguration
public class ProbeControllerTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Land land;

    private Probe probe;

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProbeRepository probeRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        for (HttpMessageConverter<?> converter : Arrays.asList(converters)) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                this.mappingJackson2HttpMessageConverter = converter;
            }
        }
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.probeRepository.deleteAllInBatch();
        this.landRepository.deleteAllInBatch();

        this.land = landRepository.save(new Land(5, 5));
        this.probe = probeRepository.save(new Probe(1, 2, CardinalDirection.NORTH, land));
    }


    @Test
    public void landNotFound() throws Exception {
        mockMvc.perform(get("/lands/50/probes/" + probe.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void probeNotFound() throws Exception {
        mockMvc.perform(get("/lands/" + land.getId() + "/probes/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void invalidCommand() throws Exception {
        String json = json(new RequestWrapper(Arrays.asList("Y")));
        this.mockMvc.perform(post("/lands/" + land.getId() + "/probes/" + probe.getId() + "/move")
                .contentType(contentType)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void readProbe() throws Exception {
        mockMvc.perform(get("/lands/" + land.getId() + "/probes/" + probe.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.probe.getId().intValue())))
                .andExpect(jsonPath("$.latitude", is(this.probe.getLatitude())))
                .andExpect(jsonPath("$.longitude", is(this.probe.getLongitude())))
                .andExpect(jsonPath("$.direction", is(this.probe.getDirection().name())));
    }


    @Test
    public void listProbes() throws Exception {
        mockMvc.perform(get("/lands/" + land.getId() + "/probes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(this.probe.getId().intValue())))
                .andExpect(jsonPath("$[0].latitude", is(this.probe.getLatitude())))
                .andExpect(jsonPath("$[0].longitude", is(this.probe.getLongitude())))
                .andExpect(jsonPath("$[0].direction", is(this.probe.getDirection().name())));
    }

    @Test
    public void moveProve() throws Exception {
        String json = json(new RequestWrapper(Arrays.asList("L", "M", "L", "M", "L", "M", "L", "M", "M")));
        this.mockMvc.perform(post("/lands/" + land.getId() + "/probes/" + probe.getId() + "/move")
                .contentType(contentType)
                .content(json))
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.probe.getId().intValue())))
                .andExpect(jsonPath("$.latitude", is(1)))
                .andExpect(jsonPath("$.longitude", is(3)))
                .andExpect(jsonPath("$.direction", is(CardinalDirection.NORTH.name())))
                .andExpect(status().isOk());
    }



    @Test
    public void update() throws Exception {
        String json = json(new Probe(10, 10, CardinalDirection.SOUTH));
        this.mockMvc.perform(put("/lands/" + land.getId() + "/probes/" + probe.getId())
                .contentType(contentType)
                .content(json))
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.probe.getId().intValue())))
                .andExpect(jsonPath("$.latitude", is(10)))
                .andExpect(jsonPath("$.longitude", is(10)))
                .andExpect(jsonPath("$.direction", is(CardinalDirection.SOUTH.name())))
                .andExpect(status().isOk());
    }

    @Test
    public void createProbe() throws Exception {
        String json = json(new Probe(10, 10, CardinalDirection.SOUTH));
        this.mockMvc.perform(post("/lands/" + land.getId() + "/probes" )
                .contentType(contentType)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void removeProbe() throws Exception {
        this.mockMvc.perform(delete("/lands/" + land.getId() + "/probes/" + probe.getId()))
                .andExpect(status().isNoContent());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}

