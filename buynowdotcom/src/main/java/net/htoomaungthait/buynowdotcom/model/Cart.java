package net.htoomaungthait.buynowdotcom.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_cart_user")
    )
    private User user;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<CartItem> items = new HashSet<>();

    public void addItem(CartItem cartItem) {
        if (cartItem == null) {
            return;
        }

        items.add(cartItem);
        cartItem.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        if (item == null) {
            return;
        }

        items.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    public void clearCart() {
        items.forEach(item -> item.setCart(null));
        items.clear();
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
