package net.htoomaungthait.buynowdotcom.repository;

import net.htoomaungthait.buynowdotcom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByBrandAndCategoryName(String brand, String name);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategoryName(String name);

    List<Product> findByNameContainingIgnoreCaseAndBrand(String name, String brand);

}
