package com.tflaa247.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.tflaa247.api.model.Category;

@AutoConfigureMockMvc
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @DisplayName("카테고리 리스트 조회")
    @Test
    void selectCategoryList() throws Exception {
        // given
        int expectedSize = 10;
        // when
        List<Category> categoryList = categoryService.selectCategoryList();
        // then
        assertThat(categoryList).hasSize(expectedSize);
    }

    @DisplayName("카테고리 조회")
    @Test
    void selectCategory() throws Exception {
        // given
        int categoryNo = 1;
        // when
        Category category = categoryService.selectCategory(categoryNo);
        // then
        assertThat(category.getCategoryNo()).isEqualTo(categoryNo);
    }

    @DisplayName("카테고리 등록")
    @Test
    void addCategory() throws Exception {
        // given
        Category category = Category.builder()
                .categoryName("테스트")
                .parentNo(1L)
                .depth(1)
                .build();
        // when
        Map<String, Object> result = categoryService.addCategory(category);
        // then
        assertThat(result.get("result")).isEqualTo(true);
    }

    @DisplayName("카테고리 수정")
    @Test
    void updateCategory() throws Exception {
        // given
        long categoryNo = 5L;
        String categoryName = "테스트";
        long parentNo = 1L;
        int depth = 1;
        // when
        Map<String, Object> result = categoryService.updateCategory(categoryNo, categoryName, parentNo, depth);
        // then
        assertThat(result.get("result")).isEqualTo(true);
    }

    @DisplayName("카테고리 삭제")
    @Test
    void deleteCategory() throws Exception {
        // given
        long categoryNo = 10L;
        // when
        Map<String, Object> result = categoryService.deleteCategory(categoryNo);
        // then
        assertThat(result.get("result")).isEqualTo(true);
    }
}
