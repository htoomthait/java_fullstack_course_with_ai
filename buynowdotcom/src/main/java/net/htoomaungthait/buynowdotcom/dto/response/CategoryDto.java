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
public class CategoryDto {

    private Long id;
    private String name;
    private String description;

        public static CategoryDto of(Long id, String name, String description) {
            return CategoryDto.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .build();
        }



        public  static CategoryDto from(Category category){
            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();
        }
}
