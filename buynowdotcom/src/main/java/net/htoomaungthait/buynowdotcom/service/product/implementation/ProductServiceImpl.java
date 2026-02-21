package net.htoomaungthait.buynowdotcom.service.product.implementation;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.dto.request.AddProductRequest;
import net.htoomaungthait.buynowdotcom.model.Category;
import net.htoomaungthait.buynowdotcom.model.Product;
import net.htoomaungthait.buynowdotcom.repository.CategoryRepository;
import net.htoomaungthait.buynowdotcom.repository.ProductRepository;
import net.htoomaungthait.buynowdotcom.service.product.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {

        if(productExists(request.getName(), request.getBrand())){
            throw new EntityExistsException("Product with the same name and brand already exists.");
        }



        return productRepository.save(createProduct(request));
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

    private boolean productExists(String name, String brand) {
        List<Product> products = productRepository.findByBrandAndCategoryName(brand, name);
        return !products.isEmpty();
    }

    private Product createProduct(AddProductRequest request){
        Product productToAdd = AddProductRequest.toProduct(request);

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory());
                    return categoryRepository.save(newCategory);
                });

        productToAdd.setCategory(category);

        return productToAdd;
    }


}
