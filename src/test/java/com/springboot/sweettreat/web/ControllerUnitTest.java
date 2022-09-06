package com.springboot.sweettreat.web;

import com.springboot.sweettreat.service.CourierService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
class ControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CourierService courierService;

    @Test
    public void getCourierListInOrderOfPrice() throws Exception {
        mockMvc.perform(get("/sweettreat/list/11:30/3/false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Martin")));



        verify(courierService, times(1)).listCouriers("11:30", 3, false);
    }

    @Test
    void listCouriers() {
    }

    @Test
    void getCourier() {
    }
}