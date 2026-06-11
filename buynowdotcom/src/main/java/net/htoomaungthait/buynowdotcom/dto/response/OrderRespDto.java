package net.htoomaungthait.buynowdotcom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.enums.OrderStatus;
import net.htoomaungthait.buynowdotcom.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRespDto {

    private Long id;

    private LocalDate orderDate;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private UserRespDto user;

    private List<OrderItemRespDto> orderItems;


    public static OrderRespDto fromOrder(Order order){
        return OrderRespDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getOrderStatus())
                .user(UserRespDto.fromUser(order.getUser()))
                .orderItems(order.getOrderItems() == null ? null : order.getOrderItems().stream().map(OrderItemRespDto::fromOrderItem).toList())
                .build();
    }
}
