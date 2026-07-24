package net.htoomaungthait.buynowdotcom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Cart;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailDto {


    private Long id;

    private Double total;

    private Long userId;

    private List<CartItemDto> cartItems;

    public static CartDetailDto of(Cart cart, List<CartItemDto> cartItems) {
        return CartDetailDto.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .total(cart.getTotalAmount().doubleValue())
                .cartItems(cartItems)
                .build();
    }
}
