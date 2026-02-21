package net.htoomaungthait.buynowdotcom.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Product;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddProductRequest {

    @NotBlank(message = "Product name must not be blank")
    private String name;

    private String description;

    @NotBlank(message = "Brand must not be blank")
    private String brand;

    @NotNull(message = "Price must not be null")
    private Double price;

    private Integer quantity;

    @NotBlank(message = "Category  must not be blank")
    private String category;


    public static Product toProduct(AddProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .brand(request.getBrand())
                .price(BigDecimal.valueOf(request.getPrice()))
                .inventory(request.getQuantity())
                .build();
    }



}
