package com.tflaa247.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tflaa247.api.model.Category;

@Mapper
public interface CategoryMapper {

    List<Category> selectCategoryList();

    List<Category> selectCategoryListByCategoryNos(List<Long> categoryNos);

    Category selectCategory(long categoryNo);

    int insertCategory(Category category);

    int updateCategory(Category category);

    int deleteCategory(long categoryNo);
}
