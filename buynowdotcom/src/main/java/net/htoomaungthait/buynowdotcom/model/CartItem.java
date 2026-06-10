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
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id",
        foreignKey = @ForeignKey(name = "fk_cart_item_product"))
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id",
        foreignKey = @ForeignKey(name = "fk_cart_item_cart")
    )
    private Cart cart;



    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }

    public static CartItem of(Product product, int quantity, Cart cart) {
        BigDecimal unitPrice = product.getPrice();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return CartItem.builder()
                .product(product)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .totalPrice(totalPrice)
                .cart(cart)
                .build();
    }
}
