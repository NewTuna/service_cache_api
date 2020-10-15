package com.tflaa247.api.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tflaa247.api.model.Product;

@Mapper
public interface ProductMapper {

    List<Product> selectProductListByCategoryNo(Long categoryNo);

    List<Product> selectProductListByProductNos(List<Long> productNos);

    Product selectProductByProductNo(long productNo);

    int insertProduct(Product product);

    int updateProduct(Product product);

    int deleteProduct(long productNo);
}
