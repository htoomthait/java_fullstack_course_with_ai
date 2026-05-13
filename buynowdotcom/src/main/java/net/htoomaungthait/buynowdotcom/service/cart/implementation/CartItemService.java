package net.htoomaungthait.buynowdotcom.service.cart.implementation;

import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.CartItem;
import net.htoomaungthait.buynowdotcom.repository.CartItemRepository;
import net.htoomaungthait.buynowdotcom.service.cart.ICartItemService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        return null;
    }
}
