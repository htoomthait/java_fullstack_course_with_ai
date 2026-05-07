package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.request.AddProductRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UpdateProductRequest;
import net.htoomaungthait.buynowdotcom.dto.resp.ProductDto;
import net.htoomaungthait.buynowdotcom.service.product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController extends BaseController {

    private final IProductService iProductService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts(){

        List<ProductDto> productDtos = iProductService.getAllProducts();
        int countOfProducts = productDtos.size();
        String statusCode = countOfProducts > 0 ? "PROD_005" : "PROD_006";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success search",
                getStatusMessageByCode(statusCode),
                productDtos
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(
            @PathVariable
            @Min(value = 1, message = "Id must be greater than or equal to 1")
            Long id){

        ProductDto productDto = iProductService.findProductDtoById(id);
        String statusCode = productDto != null ? "PROD_007" : "PROD_004";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success search",
                getStatusMessageByCode(statusCode),
                productDto
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> deleteProductById(
            @PathVariable
            @Valid
            @Min(value = 1, message = "Id must be greater than or equal to 1")
            Long id){


        return makeResponse(
                HttpStatus.OK.value(),
                "PROD_003",
                "success delete",
                getStatusMessageByCode("PROD_003"),
                iProductService.deleteProductById(id)
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<ProductDto>> createNewProduct(
            @RequestBody AddProductRequest requestBody
            ){

        return makeResponse(
                HttpStatus.CREATED.value(),
                "PROD_001",
                "success created",
                getStatusMessageByCode("PROD_001"),
                iProductService.addProduct(requestBody) );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            @PathVariable
            @Valid
            @Min(value = 1, message = "Id must be greater than or equal to 1")
            Long id,
            @RequestBody UpdateProductRequest requestBody
    ){

        return makeResponse(
                HttpStatus.ACCEPTED.value(),
                "PROD_002",
                "success update",
                getStatusMessageByCode("PROD_002"),
                iProductService.updateProduct(id, requestBody)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategoryId(
            @PathVariable
            @Valid
            @Min(value = 1, message = "Id must be greater than or equal to 1")
            Long categoryId
    ){

        List<ProductDto> productDtos = iProductService.getProductsByCategoryId(categoryId);
        int countOfProducts = productDtos.size();
        String statusCode = countOfProducts > 0 ? "PROD_005" : "PROD_006";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success search",
                getStatusMessageByCode(statusCode),
                productDtos
        );
    }

    @GetMapping("/category-name/{categoryName}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategoryName(
            @PathVariable
            @Valid
            @NotBlank(message = "Category name must be provided")
            String categoryName
    ){

        List<ProductDto> productDtos = iProductService.getProductsByCategoryName(categoryName);
        int countOfProducts = productDtos.size();
        String statusCode = countOfProducts > 0 ? "PROD_005" : "PROD_006";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success search",
                getStatusMessageByCode(statusCode),
                productDtos
        );
    }


    @GetMapping("/brand-name/{brand}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByBrand(
            @PathVariable
            @Valid
            @NotBlank(message = "brand name must be provided")
            String brand
    ){

        List<ProductDto> productDtos = iProductService.getProductsByBrand(brand);
        int countOfProducts = productDtos.size();
        String statusCode = countOfProducts > 0 ? "PROD_005" : "PROD_006";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success search",
                getStatusMessageByCode(statusCode),
                productDtos
        );
    }

    @GetMapping("/search-by-category-and-brand")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategoryAndBrand(
            @RequestParam
            @Valid
            @NotBlank(message = "brand name must be provided")
            String brand,
            @RequestParam
            @Valid
            @NotBlank(message = "Category name must be provided")
            String categoryName
    ){

        List<ProductDto> productDtos = iProductService.getProductsByCategoryAndBrand(brand, categoryName);


        int countOfProducts = productDtos.size();
        String statusCode = countOfProducts > 0 ? "PROD_005" : "PROD_006";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success search",
                getStatusMessageByCode(statusCode),
                productDtos
        );
     }

     @GetMapping("/search-by-name")
     public ResponseEntity<ApiResponse<List<ProductDto>>> searchProductsByName(
             @RequestParam
             @Valid
             @NotBlank(message = "Product name must be provided")
             String name
     ) {

         List<ProductDto> productDtos = iProductService.searchProductsByName(name);


         int countOfProducts = productDtos.size();
         String statusCode = countOfProducts > 0 ? "PROD_005" : "PROD_006";


         return makeResponse(
                 HttpStatus.OK.value(),
                 statusCode,
                 "success search",
                 getStatusMessageByCode(statusCode),
                 productDtos
         );

     }

     @GetMapping("/search-by-name-and-brand")
     public ResponseEntity<ApiResponse<List<ProductDto>>> searchProductsByNameAndBrand(
        @RequestParam
        @Valid
        @NotBlank(message = "Product name must be provided")
        String name,
        @RequestParam
        @Valid
        @NotBlank(message = "Brand name must be provided")
        String brandName
     ){

         List<ProductDto> productDtos = iProductService.searchProductsByNameAndBrand(name, brandName);
         int countOfProducts = productDtos.size();
         String statusCode = countOfProducts > 0 ? "PROD_005" : "PROD_006";

         return makeResponse(
                 HttpStatus.OK.value(),
                 statusCode,
                 "success search",
                 getStatusMessageByCode(statusCode),
                 productDtos
         );
     }






}
