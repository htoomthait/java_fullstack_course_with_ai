package net.htoomaungthait.buynowdotcom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistinctBrand {

    private String brand;


    public static DistinctBrand from(Product product) {

        return DistinctBrand.builder()
                .brand(product.getBrand())
                .build();
    }
}
