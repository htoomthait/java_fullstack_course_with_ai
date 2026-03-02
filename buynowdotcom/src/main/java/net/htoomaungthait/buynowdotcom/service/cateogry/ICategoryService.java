package net.htoomaungthait.buynowdotcom.service.cateogry;

import net.htoomaungthait.buynowdotcom.dto.request.CategoryRequest;
import net.htoomaungthait.buynowdotcom.model.Category;

import java.util.List;

public interface ICategoryService {

    Category addCategory(CategoryRequest category);

    Category updateCategory(CategoryRequest category, Long categoryId);

    void deleteCategory(Long categoryId);

    Category findCategoryById(Long categoryId);

    List<Category> getAllCategories();

    Category findCategoryByName(String name);


}
