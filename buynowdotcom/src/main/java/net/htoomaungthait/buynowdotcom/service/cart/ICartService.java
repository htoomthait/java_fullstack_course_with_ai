package net.htoomaungthait.buynowdotcom.service.cart;

import net.htoomaungthait.buynowdotcom.model.Cart;
import net.htoomaungthait.buynowdotcom.model.User;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long cartId);

    Cart getCartByUserId(Long userId);

    void  clearCart(Long cartId);

    Cart initializeNewCartForUser(User user);

    BigDecimal getTotalPrice(Long cartId);
}
