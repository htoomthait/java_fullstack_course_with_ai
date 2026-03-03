package net.htoomaungthait.buynowdotcom.dto.resp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Category;
import net.htoomaungthait.buynowdotcom.model.Image;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDto {

    private  Long id;



    private String name;


    private  String brand;


    private BigDecimal price;

    private  int inventory;

    private String description;


    private Category category;


    private List<ImageDto> images;

    public static ProductDto of(Long id, String name, String brand, BigDecimal price, int inventory, String description, Category category, List<ImageDto> images) {
        return ProductDto.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .price(price)
                .inventory(inventory)
                .description(description)
                .category(category)
                .images(images)
                .build();
    }





}
