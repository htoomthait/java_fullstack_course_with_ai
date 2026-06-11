package net.htoomaungthait.buynowdotcom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.OrderItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRespDto {
    private Long id;

    private Long productId;

    private String productName;

    private int quantity;

    private String price;

    private String totalPrice;

    public static OrderItemRespDto fromOrderItem(OrderItem orderItem){

        return OrderItemRespDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice().toString())
                .totalPrice(orderItem.getTotalPrice() == null ? null : orderItem.getTotalPrice().toString())
                .build();
    }
}
