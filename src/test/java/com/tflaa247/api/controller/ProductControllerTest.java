package com.tflaa247.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("특정 상품 조회 테스트")
    @Test
    void selectProduct() throws Exception {
        // 카테고리 번호
        final long productNo = 1;
        mvc.perform(MockMvcRequestBuilders.get("/api/product/{productNo}", productNo)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("상품 등록 테스트")
    @Test
    void addProduct() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"productName\": \"테스트상품\",\n" +
                        "  \"brandName\": \"테스트브랜드\",\n" +
                        "  \"productPrice\": 2000,\n" +
                        "  \"categoryNo\": 2\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void updateProduct() throws Exception {
        // 카테고리 번호
        final long productNo = 4;
        mvc.perform(MockMvcRequestBuilders.put("/api/product/{productNo}", productNo)
                .contentType(MediaType.APPLICATION_JSON)
                .param("productName", "상품명변경")
                .param("brandName", "브랜드명변경")
                .param("productPrice", "200000")
                .param("categoryNo", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void deleteProduct() throws Exception {
        // 카테고리 번호
        final long productNo = 9;
        mvc.perform(MockMvcRequestBuilders.delete("/api/product/{productNo}", productNo)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}