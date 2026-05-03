package net.htoomaungthait.buynowdotcom.repository;

import net.htoomaungthait.buynowdotcom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.name = :name")
    List<Product> findByBrandAndCategoryName(
            @Param("brand") String brand,
            @Param("name") String name);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategoryName(String name);

    List<Product> findByNameContainingIgnoreCaseAndBrand(String name, String brand);

}
