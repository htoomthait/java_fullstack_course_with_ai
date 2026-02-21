package net.htoomaungthait.buynowdotcom.service.product.implementation;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Product;
import net.htoomaungthait.buynowdotcom.repository.ProductRepository;
import net.htoomaungthait.buynowdotcom.service.product.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public Product findProductById(Long id) {
        return getProductById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        // Check if the product exists before attempting to delete
        Product product = getProductById(id);

        // If the product exists, delete it
        productRepository.delete(product);

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByCategoryName(String name) {
        return productRepository.findByCategoryName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String brand, String name) {

        return productRepository.findByBrandAndCategoryName(brand, name);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> searchProductsByNameAndBrand(String name, String brand) {
        return productRepository.findByProductNameContainingIgnoreCaseAndBrand(name, brand);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }


}
