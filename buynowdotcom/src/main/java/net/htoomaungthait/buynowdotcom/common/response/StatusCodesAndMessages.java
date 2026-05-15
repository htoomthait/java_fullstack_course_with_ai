package net.htoomaungthait.buynowdotcom.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusCodesAndMessages {

    private String categoryCreated = "CAT_001";

    public static StatusCodeAndMessage getByStatusCode(String statusCode) {
        return statusCodesAndMessages.stream()
                .filter(s -> s.getStatusCode().equals(statusCode))
                .findFirst()
                .orElse(null);
    }

    private static final List<StatusCodeAndMessage> statusCodesAndMessages = List.of(
            StatusCodeAndMessage.of("CER_001", "Http Method Not Allowed!"),
            StatusCodeAndMessage.of("CER_002", "Unsupported Media Type!"),
            StatusCodeAndMessage.of("CER_003", "Undefined URL!"),
            StatusCodeAndMessage.of("CER_004", "Input Validation Failed."),
            StatusCodeAndMessage.of("CER_005", "Input variable type doesn't match the required type."),
            StatusCodeAndMessage.of("CER_006", "Internal Server Occurred!"),


            StatusCodeAndMessage.of("CAT_001", "Category created successfully"),
            StatusCodeAndMessage.of("CAT_002", "Category updated successfully"),
            StatusCodeAndMessage.of("CAT_003", "Category deleted successfully"),
            StatusCodeAndMessage.of("CAT_004", "Category not found"),
            StatusCodeAndMessage.of("CAT_005", "Queried Categories found"),
            StatusCodeAndMessage.of("CAT_006", "Empty category list"),
            StatusCodeAndMessage.of("CAT_007", "Category  found"),
            StatusCodeAndMessage.of("CAT_008", "Category Not found"),
            StatusCodeAndMessage.of("CAT_009", "Category found by name"),
            StatusCodeAndMessage.of("CAT_010", "Category name already existed."),


            StatusCodeAndMessage.of("IMG_001", "Images created successfully"),
            StatusCodeAndMessage.of("IMG_002", "Images cannot be created"),
            StatusCodeAndMessage.of("IMG_003", "Image deleted successfully"),
            StatusCodeAndMessage.of("IMG_004", "Image not found"),


            StatusCodeAndMessage.of("PROD_001", "Product created successfully"),
            StatusCodeAndMessage.of("PROD_002", "Product updated successfully"),
            StatusCodeAndMessage.of("PROD_003", "Product deleted successfully"),
            StatusCodeAndMessage.of("PROD_004", "Product not found"),
            StatusCodeAndMessage.of("PROD_005", "Queried Products found"),
            StatusCodeAndMessage.of("PROD_006", "Empty product list"),
            StatusCodeAndMessage.of("PROD_007", "Product  found"),
            StatusCodeAndMessage.of("PROD_008", "Product Already existed."),


            StatusCodeAndMessage.of("EXT_USER_001", "External user(s) have been listed"),
            StatusCodeAndMessage.of("EXT_USER_002", "External user by id have been queried"),
            StatusCodeAndMessage.of("EXT_USER_003", "External user cannot be queried"),
            StatusCodeAndMessage.of("EXT_USER_004", "External user cannot be found"),
            StatusCodeAndMessage.of("EXT_USER_005", "External user query get internal server error at service provider "),
            StatusCodeAndMessage.of("EXT_USER_006", "New external user has been created!"),
            StatusCodeAndMessage.of("EXT_USER_007", "An external user has been deleted!"),
            StatusCodeAndMessage.of("EXT_USER_008", "An external user has been updated!"),

            StatusCodeAndMessage.of("USER_001", "User(s) have been listed"),
            StatusCodeAndMessage.of("USER_002", "User cannot be found."),
            StatusCodeAndMessage.of("USER_003", "New user has been created successfully."),
            StatusCodeAndMessage.of("USER_004", "User has been updated successfully."),
            StatusCodeAndMessage.of("USER_005", "User has found."),
            StatusCodeAndMessage.of("USER_006", "User list is empty."),
            StatusCodeAndMessage.of("USER_007", "User has been deleted successfully."),
            StatusCodeAndMessage.of("USER_008", "User already existed."),


            StatusCodeAndMessage.of("CARTIM_001", "Cart item has been added successfully."),
            StatusCodeAndMessage.of("CARTIM_002", "Cart item has been removed successfully."),
            StatusCodeAndMessage.of("CARTIM_003", "Cart item has been updated successfully."),
            StatusCodeAndMessage.of("CARTIM_004", "Cart item not found.")
    );




}


