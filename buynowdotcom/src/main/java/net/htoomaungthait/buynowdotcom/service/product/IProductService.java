package net.htoomaungthait.buynowdotcom.service.product;

import net.htoomaungthait.buynowdotcom.dto.request.AddProductRequest;
import net.htoomaungthait.buynowdotcom.model.Product;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest request);

    Product updateProduct(Product product);

    Product findProductById(Long id);

    void deleteProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategoryId(Long categoryId);

    List<Product> getProductsByCategoryName(String name);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String brand, String name);

    List<Product> searchProductsByName(String name);

    List<Product> searchProductsByNameAndBrand(String name, String brand);

}
