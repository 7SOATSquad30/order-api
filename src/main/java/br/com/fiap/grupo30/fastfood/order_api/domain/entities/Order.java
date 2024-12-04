package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {

    private Long id;
    private OrderStatus status;
    private Long customerId;
    private Payment payment;
    private Collection<OrderItem> items;
    private Double totalPrice = 0.0;

    public static Order createFor(Long customerId) {
        return new Order(
                null, OrderStatus.DRAFT, customerId, Payment.create(), new LinkedList<OrderItem>());
    }

    public Order(
            Long id,
            OrderStatus status,
            Long customerId,
            Payment payment,
            Collection<OrderItem> items) {
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.payment = payment;
        this.items = items;
        recalculateTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomer(Customer customer) {
        this.customerId = customer.getId();
    }

    public Payment getPayment() {
        return payment;
    }

    // public Collection<OrderItem> getItems() {
    // return items;
    // }

    public void addProduct(Product product, Long quantity) {
        this.items.stream()
                .filter(orderItem -> orderItem.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(
                        existingItem ->
                                existingItem.setQuantity(existingItem.getQuantity() + quantity),
                        () -> this.items.add(new OrderItem(product, quantity)));

        this.recalculateTotalPrice();
    }

    public void removeProduct(Product product) {
        this.items.removeIf(orderItem -> orderItem.getProduct().equals(product));
        this.recalculateTotalPrice();
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    private void recalculateTotalPrice() {
        this.totalPrice = this.items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public OrderDTO toDTO() {
        return new OrderDTO(
                id,
                status,
                items.stream().map(item -> item.toDTO()).toList(),
                totalPrice,
                customerId,
                payment.toDTO());
    }

    public OrderEntity toPersistence() {
        return new OrderEntity(
                id,
                status,
                customerId,
                payment.toPersistence(),
                items.stream().map(OrderItem::toPersistence).toList());
    }
}
