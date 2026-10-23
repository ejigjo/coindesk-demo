package com.example.coindesk.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("查詢全部：初始資料 3 筆")
    void findAll() throws Exception {
        mockMvc.perform(get("/api/currencies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Order(2)
    @DisplayName("查詢單筆 USD")
    void findOne() throws Exception {
        mockMvc.perform(get("/api/currencies/USD"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.chineseName").value("美元"));
    }

    @Test
    @Order(3)
    @DisplayName("新增 JPY")
    void create() throws Exception {
        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"JPY\",\"chineseName\":\"日圓\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("JPY"))
                .andExpect(jsonPath("$.chineseName").value("日圓"));
    }

    @Test
    @Order(4)
    @DisplayName("新增重複幣別回 409")
    void createDuplicate() throws Exception {
        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"USD\",\"chineseName\":\"美金\"}"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    @DisplayName("修改 JPY 中文名稱")
    void update() throws Exception {
        mockMvc.perform(put("/api/currencies/JPY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"JPY\",\"chineseName\":\"日幣\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chineseName").value("日幣"));
    }

    @Test
    @Order(6)
    @DisplayName("刪除 JPY 後查不到")
    void remove() throws Exception {
        mockMvc.perform(delete("/api/currencies/JPY"))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/currencies/JPY"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    @DisplayName("欄位空白回 400")
    void validation() throws Exception {
        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"\",\"chineseName\":\"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
