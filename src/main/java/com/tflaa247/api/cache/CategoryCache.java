package com.tflaa247.api.cache;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tflaa247.api.model.Category;
import com.tflaa247.api.model.Product;
import com.tflaa247.api.repository.CategoryMapper;
import com.tflaa247.api.repository.ProductMapper;

@Component
public class CategoryCache extends AbstractCacheManager<Map<Long, Category>> {

    private final CategoryMapper categoryMapper;

    private final ProductMapper productMapper;

    public CategoryCache(CategoryMapper categoryMapper, ProductMapper productMapper) {
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
    }

    /**
     * 초기 기동시 DB에 저장되어 있는 카테고리 관련 데이터 조회
     * @return
     */
    @Override
    protected Map<Long, Category> initialLoad() {
        List<Category> categoryList = categoryMapper.selectCategoryList();

        categoryList.stream()
                .filter(Objects::nonNull)
                .forEach(category -> {
                    List<Product> productList = productMapper.selectProductListByCategoryNo(category.getCategoryNo());
                    category.setProducts(productList);
                });

        return categoryList.stream()
                .collect(Collectors.toMap(Category::getCategoryNo, Function.identity()));
    }

    /**
     * cache miss가 발생하여 해당 categoryNo로 데이터를 reloading 하기위한 메서드
     * @param categoryNo
     * @return
     */
    @Override
    protected Category reload(long categoryNo) {
        Category category = categoryMapper.selectCategory(categoryNo);
        if (Objects.nonNull(category)) {
            List<Product> productList = productMapper.selectProductListByCategoryNo(category.getCategoryNo());
            category.setProducts(productList);
        }
        return category;
    }

    /**
     * Cache에 저장되어 있는 카테고리 데이터를 갱신하기 위한 메서드
     * @param categoryNos
     * @return
     */
    @Override
    protected Map<Long, Category> reloadAll(List<Long> categoryNos) {
        List<Category> categoryList = categoryMapper.selectCategoryListByCategoryNos(categoryNos);

        categoryList.stream()
                .filter(Objects::nonNull)
                .forEach(category -> {
                    List<Product> productList = productMapper.selectProductListByCategoryNo(category.getCategoryNo());
                    category.setProducts(productList);
                });

        return categoryList.stream()
                .collect(Collectors.toMap(Category::getCategoryNo, Function.identity()));
    }

}
