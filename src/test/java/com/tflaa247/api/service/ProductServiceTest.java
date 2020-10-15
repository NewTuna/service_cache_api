package com.tflaa247.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.tflaa247.api.model.Product;

@AutoConfigureMockMvc
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품 조회")
    @Test
    void selectProduct() throws Exception {
        // given
        long productNo = 1L;
        // when
        Product product = productService.selectProduct(productNo);
        // then
        assertThat(product.getCategoryNo()).isEqualTo(productNo);
    }

    @DisplayName("상품 등록")
    @Test
    void addProduct() throws Exception {
        // given
        Product product = Product.builder()
                .productName("테스트")
                .brandName("테스트브랜드")
                .productPrice(new BigDecimal(10000L))
                .categoryNo(1)
                .build();
        // when
        Map<String, Object> result = productService.addProduct(product);
        // then
        assertThat(result.get("result")).isEqualTo(true);
    }

    @DisplayName("상품 수정")
    @Test
    void updateProduct() throws Exception {
        // given
        long productNo = 500L;
        String productName = "테스트";
        String brandName = "테스트브랜드";
        double productPrice = 299999;
        long categoryNo = 3L;

        // when
        Map<String, Object> result = productService.updateProduct(productNo, productName, brandName, productPrice, categoryNo);
        // then
        assertThat(result.get("result")).isEqualTo(true);
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() throws Exception {
        // given
        long productNo = 10L;
        // when
        Map<String, Object> result = productService.deleteProduct(productNo);
        // then
        assertThat(result.get("result")).isEqualTo(true);
    }
}
