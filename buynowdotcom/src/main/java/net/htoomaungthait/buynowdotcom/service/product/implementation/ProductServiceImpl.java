package net.htoomaungthait.buynowdotcom.service.product.implementation;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.dto.request.AddProductRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UpdateProductRequest;
import net.htoomaungthait.buynowdotcom.model.*;
import net.htoomaungthait.buynowdotcom.repository.CartItemRepository;
import net.htoomaungthait.buynowdotcom.repository.CategoryRepository;
import net.htoomaungthait.buynowdotcom.repository.OrderItemRepository;
import net.htoomaungthait.buynowdotcom.repository.ProductRepository;
import net.htoomaungthait.buynowdotcom.service.product.IProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Product addProduct(AddProductRequest request) {

        if(productExists(request.getName(), request.getBrand())){
            throw new EntityExistsException("Product with the same name and brand already exists.");
        }



        return productRepository.save(createProduct(request));
    }

    @Override
    public Product updateProduct(Long productId, UpdateProductRequest request) {

            Product existingProduct = getProductById(productId);

            return productRepository.save(updateExistingProduct(existingProduct, request));


    }

    @Override
    public Product findProductById(Long id) {
        return getProductById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        // Check if the product exists before attempting to delete
        Product product = getProductById(id);

        // get all cart items associated with the product and delete them
        List<CartItem> cartItems = cartItemRepository.findByProductId(id);

        cartItems.forEach(item -> {
            Cart cart = item.getCart();
            cart.removeItem(item);
            cartItemRepository.delete(item);
        });

        // get all order items associated with the product and delete them
        List<OrderItem> orderItems = orderItemRepository.findByProductId(id);

        orderItems.forEach(item -> {
            item.setProduct(null);
            orderItemRepository.save(item);
        });


        // Remove the product from its category's product list
        Optional.ofNullable(product.getCategory())
                .ifPresent(category -> {
                    category.getProducts().remove(product);
                    categoryRepository.save(category);
                });



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
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> searchProductsByNameAndBrand(String name, String brand) {
        return productRepository.findByNameContainingIgnoreCaseAndBrand(name, brand);
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

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){

        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(BigDecimal.valueOf(request.getPrice()));
        existingProduct.setInventory(request.getQuantity());
        existingProduct.setDescription(request.getDescription());

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory());
                    return categoryRepository.save(newCategory);
                });

        existingProduct.setCategory(category);


        return existingProduct;

    }


}
