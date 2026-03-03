package net.htoomaungthait.buynowdotcom.common.response;

import lombok.Data;

import java.util.List;

@Data
public class StatusCodesAndMessages {

    private static final List<StatusCodeAndMessage> statusCodesAndMessages = List.of(
            StatusCodeAndMessage.of("CAT_001", "Category created successfully"),
            StatusCodeAndMessage.of("CAT_002", "Category updated successfully"),
            StatusCodeAndMessage.of("CAT_003", "Category deleted successfully"),
            StatusCodeAndMessage.of("CAT_004", "Category not found"),

            StatusCodeAndMessage.of("PROD_001", "Product created successfully"),
            StatusCodeAndMessage.of("PROD_002", "Product updated successfully"),
            StatusCodeAndMessage.of("PROD_003", "Product deleted successfully"),
            StatusCodeAndMessage.of("PROD_004", "Product not found")
    );




}


