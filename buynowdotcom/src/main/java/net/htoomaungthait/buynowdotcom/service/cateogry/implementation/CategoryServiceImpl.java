package net.htoomaungthait.buynowdotcom.service.cateogry.implementation;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.dto.request.CategoryRequest;
import net.htoomaungthait.buynowdotcom.model.Category;
import net.htoomaungthait.buynowdotcom.repository.CategoryRepository;
import net.htoomaungthait.buynowdotcom.service.cateogry.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {


    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(CategoryRequest category) {
        Category newCategory = CategoryRequest.of(category);

        Optional.of(category).map(CategoryRequest::getName)
                .ifPresent(name -> {
                    if (categoryRepository.findByNameContainingIgnoreCase(name) != null) {
                        throw new EntityExistsException("Category with the same name already exists.");
                    }
                });

        return categoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategory(CategoryRequest category, Long categoryId) {
        Category existingCategory = this.getCategoryById(categoryId);

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        categoryRepository.save(existingCategory);

        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category categoryToDelete = this.getCategoryById(categoryId);
        categoryRepository.delete(categoryToDelete);

    }

    @Override
    public Category findCategoryById(Long categoryId) {
        return this.getCategoryById(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
    }
}
