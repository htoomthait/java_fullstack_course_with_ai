package net.htoomaungthait.buynowdotcom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    private Blob image;

    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "product_id",
        foreignKey = @ForeignKey(name = "fk_image_product")
    )
    private Product product;

    public static Image of(String fileName, String fileType, Blob image, String downloadUrl, Product product) {
        return Image.builder()
                .fileName(fileName)
                .fileType(fileType)
                .image(image)
                .downloadUrl(downloadUrl)
                .product(product)
                .build();
    }



}
