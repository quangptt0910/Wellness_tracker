package com.example.wellnesstracker.model;

import com.example.wellnesstracker.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String userId;

    /**
     * Embedded list of items; each has its own supplementId, quantity, unitPrice
     */
    private List<OrderItem> items = new ArrayList<>();

    /**
     * Cached total; should be recalculated whenever items change
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;

    private OrderStatus status = OrderStatus.PENDING;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    /**
     * Add an item to the order and recalculate totalPrice.
     */
    public void addItem(OrderItem item) {
        this.items.add(item);
        recalculateTotal();
    }

    /**
     * Remove an item and recalculate.
     */
    public void removeItem(OrderItem item) {
        this.items.remove(item);
        recalculateTotal();
    }

    /**
     * Recomputes totalPrice from all line-items.
     */
    public void recalculateTotal() {
        this.totalPrice = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.updatedAt = Instant.now();
    }
}