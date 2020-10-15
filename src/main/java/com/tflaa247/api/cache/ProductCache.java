package com.tflaa247.api.cache;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tflaa247.api.model.Product;
import com.tflaa247.api.repository.ProductMapper;

@Component
public class ProductCache extends AbstractCacheManager<Map<Long, Product>> {

    private final ProductMapper productMapper;

    public ProductCache(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * 초기 기동시 DB에 저장되어 있는 상품 관련 데이터 조회
     * @return
     */
    @Override
    protected Map<Long, Product> initialLoad() {
        List<Product> productList = productMapper.selectProductListByCategoryNo(null);

        return productList.stream()
                .collect(Collectors.toMap(Product::getProductNo, Function.identity()));
    }

    /**
     * cache miss가 발생하여 해당 productNo로 데이터를 reloading 하기위한 메서드
     * @param productNo
     * @return
     */
    @Override
    protected Product reload(long productNo) {
        Product product = productMapper.selectProductByProductNo(productNo);
        return product;
    }

    /**
     * Cache에 저장되어 있는 상품 데이터를 갱신하기 위한 메서드
     * @param productNos
     * @return
     */
    @Override
    protected Map<Long, Product> reloadAll(List<Long> productNos) {
        List<Product> productList = productMapper.selectProductListByProductNos(productNos);

        return productList.stream()
                .collect(Collectors.toMap(Product::getProductNo, Function.identity()));
    }
}
