package com.tflaa247.api.controller;

import static com.tflaa247.api.exception.ExceptionMessages.CATEGORY_INVALID_PARAMETER_DEPTH;

import java.util.List;
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
import com.tflaa247.api.model.Category;
import com.tflaa247.api.service.CategoryService;
import com.tflaa247.api.validator.CategoryValidator;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    private static final String CATEGORY_NO = "categoryNo";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    // AOP 적용해서 조회되기전 미리 실행
    @GetMapping(value = "/list")
    public List<Category> getCategory() {
        List<Category> categoryList = categoryService.selectCategoryList();
        return categoryList;
    }

    @GetMapping(value = "/{categoryNo}")
    public Category getCategory(@PathVariable(value = "categoryNo") long categoryNo) {
        Category category = categoryService.selectCategory(categoryNo);
        return category;
    }

    @PostMapping
    public Map<String, Object> addCategory(@Validated @RequestBody Category category) {
        Map<String, Object> response = categoryService.addCategory(category);
        return response;
    }

    @PutMapping("/{categoryNo}")
    public Map<String, Object> updateCategory(
            @PathVariable(value = "categoryNo") long categoryNo,
            @RequestParam(value = "categoryName") String categoryName,
            @RequestParam(value = "parentNo") Long parentNo,
            @RequestParam(value = "depth") Integer depth
    ) {
        if (ObjectUtils.isEmpty(depth)) {
            throw new ApiException(CATEGORY_INVALID_PARAMETER_DEPTH.getMessage());
        }
        Map<String, Object> response = categoryService.updateCategory(categoryNo, categoryName, parentNo, depth);
        return response;
    }

    @DeleteMapping("/{categoryNo}")
    public Map<String, Object> deleteCategory(@PathVariable(value = "categoryNo") long categoryNo) {
        Map<String, Object> response = categoryService.deleteCategory(categoryNo);
        return response;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields(CATEGORY_NO);
        webDataBinder.setValidator(new CategoryValidator());
    }
}
