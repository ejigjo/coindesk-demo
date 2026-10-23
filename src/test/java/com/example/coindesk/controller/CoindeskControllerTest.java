package com.example.coindesk.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CoindeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("3. 呼叫 coindesk 原始 API")
    void original() throws Exception {
        mockMvc.perform(get("/api/coindesk"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chartName").value("Bitcoin"))
                .andExpect(jsonPath("$.bpi.USD.rate_float", notNullValue()));
    }

    @Test
    @DisplayName("4. 呼叫轉換後的新 API")
    void transformed() throws Exception {
        mockMvc.perform(get("/api/coindesk/transformed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updateTime",
                        matchesPattern("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")))
                .andExpect(jsonPath("$.currencies[?(@.code=='USD')].chineseName").value("美元"))
                .andExpect(jsonPath("$.currencies[?(@.code=='USD')].rate", notNullValue()));
    }
}
