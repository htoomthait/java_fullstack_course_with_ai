package net.htoomaungthait.buynowdotcom.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal price;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id",
        foreignKey = @ForeignKey(name = "fk_order_item_order")
    )
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
    foreignKey = @ForeignKey(name = "fk_order_item_product"))
    private Product product;

    public static OrderItem of(Product product, int quantity, Order order) {
        BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .price(price)
                .order(order)
                .totalPrice(price.multiply(BigDecimal.valueOf(quantity)))
                .build();
    }





}
