package net.htoomaungthait.buynowdotcom.service.cart.implementation;

import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Cart;
import net.htoomaungthait.buynowdotcom.model.User;
import net.htoomaungthait.buynowdotcom.repository.CartRepository;
import net.htoomaungthait.buynowdotcom.service.cart.ICartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    @Override
    public Cart getCart(Long cartId) {
        return null;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return null;
    }

    @Override
    public void clearCart(Long cartId) {

    }

    @Override
    public Cart initializeNewCartForUser(User user) {
        return null;
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        return null;
    }
}
