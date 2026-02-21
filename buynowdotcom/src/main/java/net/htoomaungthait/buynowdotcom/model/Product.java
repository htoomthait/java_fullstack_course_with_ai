package net.htoomaungthait.buynowdotcom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.dto.request.UpdateProductRequest;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Builder
public class Product  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private  Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private  String brand;


    private BigDecimal price;

    private  int inventory;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "fk_products_category"))
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;







}
