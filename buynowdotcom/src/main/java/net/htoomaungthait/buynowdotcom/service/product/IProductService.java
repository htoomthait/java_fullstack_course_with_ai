package net.htoomaungthait.buynowdotcom.service.product;

import net.htoomaungthait.buynowdotcom.dto.request.AddProductRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UpdateProductRequest;
import net.htoomaungthait.buynowdotcom.dto.response.ProductDto;
import net.htoomaungthait.buynowdotcom.model.Product;

import java.util.List;

public interface IProductService {

    ProductDto addProduct(AddProductRequest request);

    ProductDto updateProduct(Long productId, UpdateProductRequest request);

    Product findProductById(Long id);

    ProductDto findProductDtoById(Long id);

    ProductDto deleteProductById(Long id);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategoryId(Long categoryId);

    List<ProductDto> getProductsByCategoryName(String name);

    List<ProductDto> getProductsByBrand(String brand);

    List<ProductDto> getProductsByCategoryAndBrand(String brand, String name);

    List<ProductDto> searchProductsByName(String name);

    List<ProductDto> searchProductsByNameAndBrand(String name, String brand);

}
