package net.htoomaungthait.buynowdotcom.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodeAndMessage;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodesAndMessages;
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
public class CategoryController extends BaseController {

    private final ICategoryService categoryService;





    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(){

        List<Category> categories = categoryService.getAllCategories();

        if(categories.isEmpty()){
            this.statusCodeAndMessage = StatusCodesAndMessages.getByStatusCode("CAT_006");
            this.respondStatus = HttpStatus.OK.value();


        }
        else{
            this.respondStatus = HttpStatus.OK.value();
            this.statusCodeAndMessage = StatusCodesAndMessages.getByStatusCode("CAT_005");
        }


        return makeResponse(
                this.respondStatus ,
                statusCodeAndMessage.getStatusCode(),
                "success search",
                statusCodeAndMessage.getMessage(),
                categories
        );


    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Category>> addCategory(
            @Valid @RequestBody CategoryRequest categoryRequest
    ){

        Category newCategory = categoryService.addCategory(categoryRequest);
        this.statusCodeAndMessage = StatusCodesAndMessages.getByStatusCode("CAT_001");

        return makeResponse(
                HttpStatus.CREATED.value(),
                statusCodeAndMessage.getStatusCode(),
                "success created",
                statusCodeAndMessage.getMessage(),
                newCategory
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(
            @PathVariable Long categoryId
    ){

        Category category = categoryService.findCategoryById(categoryId);
        this.statusCodeAndMessage = StatusCodesAndMessages.getByStatusCode("CAT_007");

        return makeResponse(
                HttpStatus.CREATED.value(),
                statusCodeAndMessage.getStatusCode(),
                "success search",
                statusCodeAndMessage.getMessage(),
                category
        );
     }

     @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest categoryRequest
    ){
        Category updatedCategory = categoryService.updateCategory(categoryRequest, categoryId);

        this.statusCodeAndMessage = StatusCodesAndMessages.getByStatusCode("CAT_002");

         return makeResponse(
                 HttpStatus.CREATED.value(),
                 statusCodeAndMessage.getStatusCode(),
                 "success update",
                 statusCodeAndMessage.getMessage(),
                 updatedCategory
         );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> deleteCategory(
            @PathVariable Long categoryId
    ){
        categoryService.deleteCategory(categoryId);
        this.statusCodeAndMessage = StatusCodesAndMessages.getByStatusCode("CAT_002");

        return makeResponse(
                HttpStatus.CREATED.value(),
                statusCodeAndMessage.getStatusCode(),
                "success delete",
                statusCodeAndMessage.getMessage(),
                null
        );
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<ApiResponse<Category>> getCategoryByName(
            @PathVariable String name
    ){
        Category category = categoryService.findCategoryByName(name);
        this.statusCodeAndMessage = StatusCodesAndMessages.getByStatusCode("CAT_002");

        return makeResponse(
                HttpStatus.CREATED.value(),
                statusCodeAndMessage.getStatusCode(),
                "success  search",
                statusCodeAndMessage.getMessage(),
                category
        );
    }




}
