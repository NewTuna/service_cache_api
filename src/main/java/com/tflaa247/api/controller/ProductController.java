package com.tflaa247.api.controller;

import static com.tflaa247.api.exception.ExceptionMessages.PRODUCT_INVALID_PARAMETER_CATEGORY_NO;

import java.util.Map;

import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tflaa247.api.exception.ApiException;
import com.tflaa247.api.model.Product;
import com.tflaa247.api.service.ProductService;
import com.tflaa247.api.validator.ProductValidator;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    private static final String PRODUCT_NO = "productNo";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{productNo}")
    public Product getProduct(@PathVariable(value = "productNo") long productNo) {
        Product product = productService.selectProduct(productNo);
        return product;
    }

    @PostMapping
    public Map<String, Object> addProduct(@Validated @RequestBody Product product) {
        Map<String, Object> response = productService.addProduct(product);
        return response;
    }

    @PutMapping("/{productNo}")
    public Map<String, Object> updateProduct(
            @PathVariable(value = "productNo") long productNo,
            @RequestParam(value = "productName") String productName,
            @RequestParam(value = "brandName") String brandName,
            @RequestParam(value = "productPrice") Double productPrice,
            @RequestParam(value = "categoryNo") Long categoryNo
    ) {
        if (ObjectUtils.isEmpty(categoryNo)) {
            throw new ApiException(PRODUCT_INVALID_PARAMETER_CATEGORY_NO.getMessage());
        }
        Map<String, Object> response = productService.updateProduct(productNo, productName, brandName, productPrice, categoryNo);
        return response;
    }

    @DeleteMapping("/{productNo}")
    public Map<String, Object> deleteProduct(@PathVariable(value = "productNo") long productNo) {
        Map<String, Object> response = productService.deleteProduct(productNo);
        return response;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields(PRODUCT_NO);
        webDataBinder.setValidator(new ProductValidator());
    }
}
