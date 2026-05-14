package net.htoomaungthait.buynowdotcom.service.cart.implementation;

import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.model.Cart;
import net.htoomaungthait.buynowdotcom.model.User;
import net.htoomaungthait.buynowdotcom.repository.CartItemRepository;
import net.htoomaungthait.buynowdotcom.repository.CartRepository;
import net.htoomaungthait.buynowdotcom.service.cart.ICartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    @Override
    public Cart getCart(Long cartId) {
        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(()-> new EntityNotFoundException("Cart with given ID: "+ cartId + " is not found.", "CART_00"));

//        BigDecimal totalAmount = cart.getTotalAmount();

        BigDecimal totalAmount = cart.getItems()
                .stream()
                .map(item ->
                        item.getUnitPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart with user ID: "+ userId + " is not found.", "CART_00"));
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cartItemRepository.deleteAllByCartId(cartId);
        cart.clearCart();
        cartRepository.deleteById(cartId);

    }

    @Override
    public Cart initializeNewCartForUser(User user) {

        return Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(()->{
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart  cart = getCart(cartId);
        return cart.getTotalAmount();
    }
}
