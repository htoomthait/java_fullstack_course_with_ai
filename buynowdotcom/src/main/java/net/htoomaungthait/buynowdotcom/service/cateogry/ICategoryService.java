package net.htoomaungthait.buynowdotcom.service.cateogry;

import net.htoomaungthait.buynowdotcom.dto.request.CategoryRequest;
import net.htoomaungthait.buynowdotcom.dto.resp.CategoryDto;
import net.htoomaungthait.buynowdotcom.model.Category;

import java.util.List;

public interface ICategoryService {

    CategoryDto addCategory(CategoryRequest category);

    CategoryDto updateCategory(CategoryRequest category, Long categoryId);

    CategoryDto deleteCategory(Long categoryId);

    CategoryDto findCategoryById(Long categoryId);

    List<CategoryDto> getAllCategories();

    List<CategoryDto> findCategoryByName(String name);


}
