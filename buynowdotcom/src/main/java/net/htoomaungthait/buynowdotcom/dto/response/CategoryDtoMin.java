package net.htoomaungthait.buynowdotcom.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryDtoMin {

    private Long id;

    private String name;

     public static CategoryDtoMin of(Long id, String name) {
         return CategoryDtoMin.builder()
                 .id(id)
                 .name(name)
                 .build();
     }

    public static CategoryDtoMin fromEntity(Category category) {
        return CategoryDtoMin.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
