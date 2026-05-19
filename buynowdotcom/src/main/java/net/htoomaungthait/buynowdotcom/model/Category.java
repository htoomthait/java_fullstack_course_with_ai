package net.htoomaungthait.buynowdotcom.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(nullable = false, unique = true)
    private  String name;

    private String description;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;


    public static Category of(String name, String description) {
        return Category.builder()
                .name(name)
                .description(description)
                .build();
    }


}
