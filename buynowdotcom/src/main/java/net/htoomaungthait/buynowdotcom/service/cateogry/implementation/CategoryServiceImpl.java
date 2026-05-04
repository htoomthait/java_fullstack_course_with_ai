package net.htoomaungthait.buynowdotcom.service.cateogry.implementation;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityExistsException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.dto.request.CategoryRequest;
import net.htoomaungthait.buynowdotcom.dto.resp.CategoryDto;
import net.htoomaungthait.buynowdotcom.model.Category;
import net.htoomaungthait.buynowdotcom.model.Product;
import net.htoomaungthait.buynowdotcom.repository.CategoryRepository;
import net.htoomaungthait.buynowdotcom.repository.ProductRepository;
import net.htoomaungthait.buynowdotcom.service.cateogry.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements ICategoryService {


    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Override
    public CategoryDto addCategory(CategoryRequest category) {
        Category newCategory = CategoryRequest.of(category);

        Optional.of(category).map(CategoryRequest::getName)
                .ifPresent(name -> {
                    if (categoryRepository.findByName(name) != null) {
                        throw new EntityExistsException("Category with the same name already exists.", "CAT_010");
                    }
                });

        return CategoryDto.from(categoryRepository.save(newCategory));
    }

    @Override
    public CategoryDto updateCategory(CategoryRequest category, Long categoryId) {
        Category existingCategory = this.getCategoryById(categoryId);

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return CategoryDto.from(categoryRepository.save(existingCategory));
    }

    @Override
    public CategoryDto deleteCategory(Long categoryId) {

        // find category to delete and throw exception if not found
        Category categoryToDelete = this.getCategoryById(categoryId);

        // Set category to null for all products
        List<Product> products = productRepository.findByCategoryName(categoryToDelete.getName());

        products.forEach(product -> product.setCategory(null));

        productRepository.saveAll(products);


        // delete category and return deleted category as DTO
        categoryRepository.delete(categoryToDelete);

        return CategoryDto.from(categoryToDelete);

    }

    @Override
    public CategoryDto findCategoryById(Long categoryId) {
        log.debug("this is find category by id function");
        return CategoryDto.from(this.getCategoryById(categoryId));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        try {

            // find all and throw exception if empty
            List<Category> searchCategories = categoryRepository.findAll();
            if (searchCategories.isEmpty()) {
                throw new EntityNotFoundException("Categories not found", "CAT_006");
            }

            // Map to DTO and return
            return searchCategories.stream()
                    .map(CategoryDto::from)
                    .toList();

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CategoryDto> findCategoryByName(String name) {
        List<CategoryDto> categoryDtoList = categoryRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(CategoryDto::from)
                .toList();

        if (categoryDtoList.isEmpty()) {
            throw new EntityNotFoundException("Categories not found with name: " +name, "CAT_006");
        }

        return categoryDtoList;
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId, "CAT_004"));
    }
}
