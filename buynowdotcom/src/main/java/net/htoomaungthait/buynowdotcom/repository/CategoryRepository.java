package net.htoomaungthait.buynowdotcom.repository;


import net.htoomaungthait.buynowdotcom.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName( String name);

    Category findByNameContainingIgnoreCase(String name);
}
