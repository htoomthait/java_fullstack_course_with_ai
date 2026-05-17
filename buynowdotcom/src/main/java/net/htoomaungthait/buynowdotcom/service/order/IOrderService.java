package net.htoomaungthait.buynowdotcom.service.order;

import net.htoomaungthait.buynowdotcom.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);

    List<Order> getUserOrder(Long userId);
}
