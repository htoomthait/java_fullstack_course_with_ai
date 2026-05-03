package net.htoomaungthait.buynowdotcom.service.product;

import net.htoomaungthait.buynowdotcom.dto.request.AddProductRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UpdateProductRequest;
import net.htoomaungthait.buynowdotcom.dto.resp.ProductDto;
import net.htoomaungthait.buynowdotcom.model.Product;

import java.util.List;

public interface IProductService {

    ProductDto addProduct(AddProductRequest request);

    ProductDto updateProduct(Long productId, UpdateProductRequest request);

    Product findProductById(Long id);

    ProductDto findProductDtoById(Long id);

    ProductDto deleteProductById(Long id);

    List<ProductDto> getAllProducts();

    List<Product> getProductsByCategoryId(Long categoryId);

    List<Product> getProductsByCategoryName(String name);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String brand, String name);

    List<Product> searchProductsByName(String name);

    List<Product> searchProductsByNameAndBrand(String name, String brand);

}
