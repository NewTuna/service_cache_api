package com.tflaa247.api.validator;

import static com.tflaa247.api.exception.ExceptionMessages.CATEGORY_INVALID_PARAMETER_DEPTH;

import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tflaa247.api.exception.ApiException;
import com.tflaa247.api.model.Category;

public class CategoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.isAssignableFrom(clazz);
    }

    /**
     * 카테고리 파라미터 Validator
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        Category params = (Category) target;

        if (ObjectUtils.isEmpty(params.getDepth())) {
            throw new ApiException(CATEGORY_INVALID_PARAMETER_DEPTH.getMessage());
        }
    }
}
