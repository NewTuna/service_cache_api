package com.tflaa247.api.exception;

public enum ExceptionMessages {

    CATEGORY_INVALID_PARAMETER_DEPTH("depth는 필수 파라미터입니다."),
    PRODUCT_INVALID_PARAMETER_CATEGORY_NO("categoryNo는 필수 파라미터입니다.")
    ;

    private String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
