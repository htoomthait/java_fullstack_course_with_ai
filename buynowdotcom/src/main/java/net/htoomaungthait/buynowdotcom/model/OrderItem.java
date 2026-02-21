package net.htoomaungthait.buynowdotcom.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
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

    @ManyToOne
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
                .build();
    }





}
