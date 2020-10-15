package com.tflaa247.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private long categoryNo;

    private String categoryName;

    private Long parentNo;

    private Integer depth;

    List<Product> products;

}

