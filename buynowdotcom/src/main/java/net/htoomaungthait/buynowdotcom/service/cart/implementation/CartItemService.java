package net.htoomaungthait.buynowdotcom.service.cart.implementation;

import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.model.Cart;
import net.htoomaungthait.buynowdotcom.model.CartItem;
import net.htoomaungthait.buynowdotcom.model.Product;
import net.htoomaungthait.buynowdotcom.repository.CartItemRepository;
import net.htoomaungthait.buynowdotcom.repository.CartRepository;
import net.htoomaungthait.buynowdotcom.service.cart.ICartItemService;
import net.htoomaungthait.buynowdotcom.service.cart.ICartService;
import net.htoomaungthait.buynowdotcom.service.product.IProductService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;
    private final IProductService productService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart  = cartService.getCart(cartId);

        Product product = productService.findProductById(productId);

        CartItem cartItem =  cart.getItems()
                .stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }


        cartItem.setTotalPrice();
        cart.addItem(cartItem);

         cartItemRepository.save(cartItem);
         cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart =  cartService.getCart(cartId);

        CartItem itemToBeRemoved = getCartItem(cartId, productId);
        cart.removeItem(itemToBeRemoved);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Cart not found exception", "CART_001"));

    }
}
