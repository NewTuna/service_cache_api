package com.tflaa247.api.controller;

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
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("카테고리 리스트 조회 테스트")
    @Test
    void selectCategoryList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/category/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("특정 카테고리 조회 테스트")
    @Test
    void selectCategory() throws Exception {
        // 카테고리 번호
        final long categoryNo = 1;
        mvc.perform(MockMvcRequestBuilders.get("/api/category/{categoryNo}", categoryNo)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("카테고리 등록 테스트")
    @Test
    void addCategory() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"categoryName\": \"테스트카테고리\",\n" +
                        "  \"parentNo\": 3,\n" +
                        "  \"depth\": 2\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("카테고리 수정 테스트")
    @Test
    void updateCategory() throws Exception {
        // 카테고리 번호
        final long categoryNo = 4;
        mvc.perform(MockMvcRequestBuilders.put("/api/category/{categoryNo}", categoryNo)
                .contentType(MediaType.APPLICATION_JSON)
                .param("categoryName", "카테고리변경")
                .param("parentNo", "1")
                .param("depth", "2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("카테고리 삭제 테스트")
    @Test
    void deleteCategory() throws Exception {
        // 카테고리 번호
        final long categoryNo = 9;
        mvc.perform(MockMvcRequestBuilders.delete("/api/category/{categoryNo}", categoryNo)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}