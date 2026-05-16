package net.htoomaungthait.buynowdotcom.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Category;
import net.htoomaungthait.buynowdotcom.model.Product;

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


    private CategoryDtoMin category;


    private List<ImageDto> images;

    public static ProductDto of(Long id, String name, String brand, BigDecimal price, int inventory, String description, Category category, List<ImageDto> images) {
        return ProductDto.builder()
                .id(id)
                .name(name)
                .brand(brand)
                .price(price)
                .inventory(inventory)
                .description(description)
                .category(CategoryDtoMin.fromEntity(category))
                .images(images)
                .build();
    }

    public static ProductDto fromEntity(Product product) {
        List<ImageDto> imageDtos = product.getImages() != null ? product.getImages().stream()
                .map(image -> ImageDto.of(image.getId(), image.getFileName(), image.getFileType(), image.getDownloadUrl()))
                .toList() : List.of();

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .inventory(product.getInventory())
                .description(product.getDescription())
                .category(CategoryDtoMin.fromEntity(product.getCategory()))
                .images(imageDtos)
                .build();
    }





}
