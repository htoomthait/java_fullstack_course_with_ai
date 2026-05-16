package net.htoomaungthait.buynowdotcom.service.product.implementation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityExistsException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.dto.request.AddProductRequest;
import net.htoomaungthait.buynowdotcom.dto.request.UpdateProductRequest;
import net.htoomaungthait.buynowdotcom.dto.response.ProductDto;
import net.htoomaungthait.buynowdotcom.model.*;
import net.htoomaungthait.buynowdotcom.repository.*;
import net.htoomaungthait.buynowdotcom.service.product.IProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final ImageRepository imageRepository;



    @Override
    public ProductDto addProduct(AddProductRequest request) {


        if(productExists(request.getName(), request.getBrand())){
            throw new EntityExistsException("Product with the same name and brand already exists.", "PROD_008");
        }



        return ProductDto
                .fromEntity(productRepository.save(createProduct(request)));
    }

    @Override
    public ProductDto updateProduct(Long productId, UpdateProductRequest request) {

            Product existingProduct = getProductById(productId);

            return ProductDto
                    .fromEntity(updateExistingProduct(existingProduct, request));


    }

    @Override
    public Product findProductById(Long id) {
        return getProductById(id);
    }

    @Override
    public ProductDto findProductDtoById(Long id) {
        Product product = getProductById(id);

        List<Image> images = imageRepository.findByProductId(id);
        product.setImages(images);

        return ProductDto.fromEntity(product);
    }

    @Override
    public ProductDto deleteProductById(Long id) {
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

        return ProductDto.fromEntity(product);

    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products =  productRepository.findAll();

        return products.stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryName(String name) {

        List<Product> products =  productRepository.findByCategoryName(name);

        return products.stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        List<Product> products =  productRepository.findByBrand(brand);

        return products
                .stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String brand, String name) {

        List<Product> products = productRepository.findByProductCategoryNameAndBrand(name, brand);

        return products
                .stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductDto> searchProductsByName(String name) {

       List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream()
                .map(ProductDto::fromEntity)
                .toList();

    }

    @Override
    public List<ProductDto> searchProductsByNameAndBrand(String name, String brand) {
        List<Product> products =  productRepository.findByNameContainingIgnoreCaseAndBrand(name, brand);

        return products
                .stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id:"+ id, "PROD_004") );
    }

    private boolean productExists(String name, String brand) {
        List<Product> products = productRepository.findByBrandAndProductName(brand, name);
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
        existingProduct.setDescription(request.getDescription());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(BigDecimal.valueOf(request.getPrice()));
        existingProduct.setInventory(request.getQuantity());



        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory());
                    return categoryRepository.save(newCategory);
                });

        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);




    }


}
