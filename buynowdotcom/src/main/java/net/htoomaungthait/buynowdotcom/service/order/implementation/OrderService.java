package net.htoomaungthait.buynowdotcom.service.order.implementation;

import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.enums.OrderStatus;
import net.htoomaungthait.buynowdotcom.model.Cart;
import net.htoomaungthait.buynowdotcom.model.Order;
import net.htoomaungthait.buynowdotcom.model.OrderItem;
import net.htoomaungthait.buynowdotcom.model.Product;
import net.htoomaungthait.buynowdotcom.repository.OrderRepository;
import net.htoomaungthait.buynowdotcom.repository.ProductRepository;
import net.htoomaungthait.buynowdotcom.service.cart.ICartService;
import net.htoomaungthait.buynowdotcom.service.order.IOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList =  createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    @Override
    public List<Order> getUserOrder(Long userId) {
        return orderRepository.findByUserId(userId);
    }


    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList){
        return orderItemList.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add );
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());

                    productRepository.save(product);

                    return OrderItem.of(product, cartItem.getQuantity(), order);

                }).toList();
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;
    }
}
