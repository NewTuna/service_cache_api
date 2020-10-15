package com.tflaa247.api.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tflaa247.api.cache.CategoryCache;
import com.tflaa247.api.model.Category;
import com.tflaa247.api.repository.CategoryMapper;

@Service
public class CategoryService {

    private final CategoryCache categoryCache;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryCache categoryCache, CategoryMapper categoryMapper) {
        this.categoryCache = categoryCache;
        this.categoryMapper = categoryMapper;
    }

    /**
     * 카테고리 리스트 조회
     * @return
     */
    public List<Category> selectCategoryList() {
        Map<Long, Category> categoryMap = Optional.ofNullable(categoryCache.getList()).orElseGet(Collections::emptyMap);
        List<Category> categoryList = categoryMap.entrySet().stream()
                .filter(Objects::nonNull)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return categoryList;
    }

    /**
     * 카테고리 조회 (캐시 조회)
     * @param categoryNo
     * @return
     */
    public Category selectCategory(long categoryNo) {
        Category category = (Category) categoryCache.get(categoryNo);
        return category;
    }

    /**
     * 카테고리 등록
     * @param category
     * @return
     */
    public Map<String, Object> addCategory(Category category) {
        Map<String, Object> response = new HashMap<>();

        int result = categoryMapper.insertCategory(category);
        if (result > 0) {
            response.put("result", true);
        } else {
            response.put("result", false);
        }
        return response;
    }

    /**
     * 카테고리 수정
     * @param categoryNo
     * @param categoryName
     * @param parentNo
     * @param depth
     * @return
     */
    public Map<String, Object> updateCategory(long categoryNo, String categoryName, Long parentNo, Integer depth) {
        Map<String, Object> response = new HashMap<>();

        Category category = Category.builder()
                .categoryNo(categoryNo)
                .categoryName(categoryName)
                .parentNo(parentNo)
                .depth(depth)
                .build();

        int result = categoryMapper.updateCategory(category);
        if (result > 0) {
            response.put("result", true);
        } else {
            response.put("result", false);
        }
        return response;
    }

    /**
     * 카테고리 삭제
     * @param categoryNo
     * @return
     */
    public Map<String, Object> deleteCategory(long categoryNo) {
        Map<String, Object> response = new HashMap<>();

        int result = categoryMapper.deleteCategory(categoryNo);
        if (result > 0) {
            response.put("result", true);
        } else {
            response.put("result", false);
        }
        return response;
    }
}
