package net.htoomaungthait.buynowdotcom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Cart;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private Long id;

    private BigDecimal totalAmount;

    private Long userId;



    public static CartDto of(Cart cart){

        return CartDto.builder()
                .id(cart.getId())
                .totalAmount(cart.getTotalAmount())
                .userId(cart.getUser().getId())
                .build();

    }
}
