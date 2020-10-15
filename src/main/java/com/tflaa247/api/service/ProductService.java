package com.tflaa247.api.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tflaa247.api.cache.ProductCache;
import com.tflaa247.api.model.Product;
import com.tflaa247.api.repository.ProductMapper;

@Service
public class ProductService {

    private final ProductCache productCache;

    private final ProductMapper productMapper;

    public ProductService(ProductCache productCache, ProductMapper productMapper) {
        this.productCache = productCache;
        this.productMapper = productMapper;
    }

    /**
     * 상품 조회
     * @param productNo
     * @return
     */
    public Product selectProduct(long productNo) {
        Product product = (Product) productCache.get(productNo);
        return product;
    }

    /**
     * 상품 등록
     * @param product
     * @return
     */
    public Map<String, Object> addProduct(Product product) {
        Map<String, Object> response = new HashMap<>();

        int result = productMapper.insertProduct(product);
        if (result > 0) {
            response.put("result", true);
        } else {
            response.put("result", false);
        }
        return response;
    }

    /**
     * 상품 수정
     * @param productNo
     * @param productName
     * @param brandName
     * @param productPrice
     * @param categoryNo
     * @return
     */
    public Map<String, Object> updateProduct(long productNo, String productName, String brandName,
                                             Double productPrice, Long categoryNo) {
        Map<String, Object> response = new HashMap<>();

        Product product = Product.builder()
                .productNo(productNo)
                .productName(productName)
                .brandName(brandName)
                .productPrice(ObjectUtils.isEmpty(productPrice) ? null : new BigDecimal(productPrice))
                .categoryNo(categoryNo)
                .build();

        int result = productMapper.updateProduct(product);
        if (result > 0) {
            response.put("result", true);
        } else {
            response.put("result", false);
        }
        return response;
    }

    /**
     * 상품 삭제
     * @param productNo
     * @return
     */
    public Map<String, Object> deleteProduct(long productNo) {
        Map<String, Object> response = new HashMap<>();

        int result = productMapper.deleteProduct(productNo);
        if (result > 0) {
            response.put("result", true);
        } else {
            response.put("result", false);
        }
        return response;
    }
}
