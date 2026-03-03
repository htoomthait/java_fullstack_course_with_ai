package net.htoomaungthait.buynowdotcom.dto.resp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageDto {

    private Long Id;

    private String fileName;

    private String fileType;

    private String downloadUrl;

        public static ImageDto of(Long id, String fileName, String fileType, String downloadUrl) {
            return ImageDto.builder()
                    .Id(id)
                    .fileName(fileName)
                    .fileType(fileType)
                    .downloadUrl(downloadUrl)
                    .build();
        }
}
