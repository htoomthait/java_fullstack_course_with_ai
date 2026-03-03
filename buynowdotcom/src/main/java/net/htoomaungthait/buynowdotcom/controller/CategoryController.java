package net.htoomaungthait.buynowdotcom.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.request.CategoryRequest;
import net.htoomaungthait.buynowdotcom.model.Category;
import net.htoomaungthait.buynowdotcom.service.cateogry.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){

        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(ApiResponse.of("Found categories", categories));


    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(
            @Valid @RequestBody CategoryRequest categoryRequest
    ){

        Category newCategory = categoryService.addCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of("Category added successfully", newCategory));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(
            @PathVariable Long categoryId
    ){

        Category category = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(ApiResponse.of("Found category", category));
     }

     @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest categoryRequest
    ){
        Category updatedCategory = categoryService.updateCategory(categoryRequest, categoryId);
        return ResponseEntity.ok(ApiResponse.of("Category updated successfully", updatedCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long categoryId
    ){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.of("Category deleted successfully", null));
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(
            @PathVariable String name
    ){
        Category category = categoryService.findCategoryByName(name);
        return ResponseEntity.ok(ApiResponse.of("Found category", category));
    }




}
