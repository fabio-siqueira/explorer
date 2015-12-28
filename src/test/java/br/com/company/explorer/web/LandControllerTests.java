package br.com.company.explorer.web;

import br.com.company.explorer.ExplorerApplication;
import br.com.company.explorer.domain.Land;
import br.com.company.explorer.domain.LandRepository;
import br.com.company.explorer.domain.ProbeRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Fábio Siqueira on 12/23/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExplorerApplication.class)
@WebAppConfiguration
public class LandControllerTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Land land;

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

        this.land = landRepository.save(new Land(3, 3));
    }

    @Test
    public void probeNotFound() throws Exception {
        mockMvc.perform(get("/lands/1/50"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readLand() throws Exception {
        mockMvc.perform(get("/lands/" + land.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.land.getId().intValue())))
                .andExpect(jsonPath("$.topLimit", is(this.land.getTopLimit().intValue())))
                .andExpect(jsonPath("$.rightLimit", is(this.land.getRightLimit())));
    }


    @Test
    public void listLands() throws Exception {
        mockMvc.perform(get("/lands"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(this.land.getId().intValue())))
                .andExpect(jsonPath("$[0].topLimit", is(this.land.getTopLimit().intValue())))
                .andExpect(jsonPath("$[0].rightLimit", is(this.land.getRightLimit())));
    }

    @Test
    public void updateLand() throws Exception {
        String json = json(new Land(10, 12));
        this.mockMvc.perform(put("/lands/" + land.getId())
                .contentType(contentType)
                .content(json))
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.land.getId().intValue())))
                .andExpect(jsonPath("$.topLimit", is(10)))
                .andExpect(jsonPath("$.rightLimit", is(12)))
                .andExpect(status().isOk());
    }

    @Test
    public void createLand() throws Exception {
        String land = json(new Land(10, 12));
        this.mockMvc.perform(post("/lands")
                .contentType(contentType)
                .content(land))
                .andExpect(status().isCreated());
    }

    @Test
    public void removeLand() throws Exception {
        this.mockMvc.perform(delete("/lands/" + land.getId()))
                .andExpect(status().isNoContent());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
