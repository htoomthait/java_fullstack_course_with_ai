package net.htoomaungthait.buynowdotcom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Cart;
import net.htoomaungthait.buynowdotcom.model.Order;
import net.htoomaungthait.buynowdotcom.model.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCartOrderRespDto {


    private UserRespDto userInfo;

    private CartDto cartInfo;

    private List<OrderRespDto> orderList;


    public static UserCartOrderRespDto fromModels(User user, Cart cart, List<Order> orders){

        return UserCartOrderRespDto.builder()
                .userInfo(UserRespDto.fromUser(user))
                .cartInfo(cart != null ? CartDto.of(cart) : null)
                .orderList(orders.stream().map(OrderRespDto::fromOrder).toList())
                .build();
    }
}
