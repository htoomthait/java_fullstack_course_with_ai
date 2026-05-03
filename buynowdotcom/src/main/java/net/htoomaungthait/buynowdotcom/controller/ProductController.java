package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.constraints.Min;
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




}
