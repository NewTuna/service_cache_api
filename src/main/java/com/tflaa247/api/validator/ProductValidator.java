package com.tflaa247.api.validator;

import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tflaa247.api.exception.ApiException;
import com.tflaa247.api.model.Product;
import com.tflaa247.api.exception.ExceptionMessages;

public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    /**
     * 상품 파라미터 Validator
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        Product params = (Product) target;

        if (ObjectUtils.isEmpty(params.getCategoryNo())) {
            throw new ApiException(ExceptionMessages.PRODUCT_INVALID_PARAMETER_CATEGORY_NO.getMessage());
        }
    }
}
