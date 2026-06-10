package net.htoomaungthait.buynowdotcom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.CartItem;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private Long id;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;


    private ProductDto product;

    private CartDto cart;


    public static CartItemDto of(CartItem cartItem){
        return CartItemDto.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .totalPrice(cartItem.getTotalPrice())
                .product(ProductDto.fromEntity(cartItem.getProduct()))
                .cart(CartDto.of(cartItem.getCart()))
                .build();
    }


}


