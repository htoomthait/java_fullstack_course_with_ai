package net.htoomaungthait.buynowdotcom.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodeAndMessage;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodesAndMessages;
import net.htoomaungthait.buynowdotcom.dto.request.CategoryRequest;
import net.htoomaungthait.buynowdotcom.dto.resp.CategoryDto;
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
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories(){


        return makeResponse(
                HttpStatus.OK.value() ,
                "CAT_005",
                "success search",
                getStatusMessageByCode("CAT_005"),
                categoryService.getAllCategories()
        );


    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CategoryDto>> addCategory(
            @Valid @RequestBody CategoryRequest categoryRequest
    ){



        return makeResponse(
                HttpStatus.CREATED.value(),
                "CAT_001",
                "success created",
                getStatusMessageByCode("CAT_005"),
                categoryService.addCategory(categoryRequest)
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(
            @PathVariable Long categoryId
    ){



        return makeResponse(
                HttpStatus.OK.value(),
                "CAT_007",
                "success search",
                getStatusMessageByCode("CAT_007"),
                categoryService.findCategoryById(categoryId)
        );
     }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest categoryRequest
    ){



         return makeResponse(
                 HttpStatus.ACCEPTED.value(),
                 "CAT_002",
                 "success update",
                 getStatusMessageByCode("CAT_002"),
                 categoryService.updateCategory(categoryRequest, categoryId)
         );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDto>> deleteCategory(
            @PathVariable Long categoryId
    ){



        return makeResponse(
                HttpStatus.OK.value(),
                "CAT_003",
                "success delete",
                getStatusMessageByCode("CAT_003"),
                categoryService.deleteCategory(categoryId)
        );
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategoryByName(
            @PathVariable String name
    ){


        return makeResponse(
                HttpStatus.CREATED.value(),
                "CAT_009",
                "success  search",
                getStatusMessageByCode("CAT_009"),
                categoryService.findCategoryByName(name)
        );
    }




}
