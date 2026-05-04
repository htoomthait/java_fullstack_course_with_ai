package net.htoomaungthait.buynowdotcom.repository;

import net.htoomaungthait.buynowdotcom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.brand = :brand AND p.name = :name")
    List<Product> findByBrandAndProductName(
            @Param("brand") String brand,
            @Param("name") String name);

    @Query("SELECT p FROM Product p " +
            "WHERE p.category.name = :categoryName " +
            "AND p.brand = :brand")
   List<Product> findByProductCategoryNameAndBrand(
           @Param("categoryName")String categoryName, @Param("brand") String brand);

    Product findByNameContainingIgnoreCase(String name);

    List<Product> findByBrand(String brand);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    List<Product> findByCategoryName(String name);

    List<Product> findByNameContainingIgnoreCaseAndBrand(String name, String brand);

}
