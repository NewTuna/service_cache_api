package com.tflaa247.api.model;


import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Product {

    private long productNo;

    private String productName;

    private String brandName;

    private BigDecimal productPrice;

    private long categoryNo;

}
