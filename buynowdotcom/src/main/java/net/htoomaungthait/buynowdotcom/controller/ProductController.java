package net.htoomaungthait.buynowdotcom.controller;

import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.resp.ProductDto;
import net.htoomaungthait.buynowdotcom.service.product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController extends BaseController {

    private final IProductService iProductService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts(){

        int countOfProducts = iProductService.getAllProducts().size();
        String statusCode = countOfProducts > 0 ? "PROD_005" : "PROD_006";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success search",
                getStatusMessageByCode(statusCode),
                iProductService.getAllProducts()
        );
    }


}
