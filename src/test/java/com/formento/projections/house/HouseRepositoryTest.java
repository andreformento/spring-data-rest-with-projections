package com.formento.projections.house;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseRepositoryTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private FilterChainProxy filterChain;
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(context).addFilters(filterChain).build();

        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldCreateAddress() throws Exception {
        allowsPostRequestForUser("My location house ollie", "ollie", "gierke");
    }

    @Test
    public void shouldShowJustSelfUserHouses() throws Exception {
        allowsPostRequestForUser("My location house ollie", "ollie", "gierke");
        allowsPostRequestForUser("My location house greg", "greg", "turnquist");
    }

    private void allowsPostRequestForUser(final String address, final String user, final String password) throws Exception {
        final String payload = "{\"address\": \"" + address + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode((user + ":" + password).getBytes())));

        final String path = "/houses";
        mvc.perform(get(path).
            headers(headers)).
            andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).
            andExpect(status().isOk()).
            andDo(print());

        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        final String location = mvc.
            perform(post(path).
                content(payload).
                headers(headers)).
            andDo(print()).
            andExpect(status().isCreated()).
            andReturn().getResponse().getHeader(HttpHeaders.LOCATION);

        mvc.
            perform(get(location).
                headers(headers)).
            andExpect(status().isOk()).
            andExpect(jsonPath("$.user").value(is(user))).
            andExpect(jsonPath("$.address").value(is(address)));

        final String locationOfUpdate = mvc.
            perform(put(location).
                content(payload).
                headers(headers)).
            andDo(print()).
            andExpect(status().isOk()).
            andReturn().getResponse().getHeader(HttpHeaders.LOCATION);

        assertThat(locationOfUpdate).isEqualTo(location);

        mvc.
            perform(get(location).
                headers(headers)).
            andExpect(status().isOk()).
            andExpect(jsonPath("$.user").value(is(user))).
            andExpect(jsonPath("$.address").value(is(address)));

        mvc.
            perform(get(path).
                headers(headers)).
            andDo(print()).
            andExpect(status().isOk()).
            andExpect(jsonPath("$._embedded.houses.*").value(hasSize(1))).
            andExpect(jsonPath("$._embedded.houses[0].user").value(is(user))).
            andExpect(jsonPath("$._embedded.houses[0].address").value(is(address)));
    }

}
