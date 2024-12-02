package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.OrderItemEntity;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderItem {
    private Product product;
    private Long quantity;

    @Setter(AccessLevel.NONE)
    private Double totalPrice;

    public OrderItem(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
        recalculateTotalPrice();
    }

    public void setProduct(Product product) {
        this.product = product;
        recalculateTotalPrice();
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        this.totalPrice = this.product.getPrice() * this.quantity;
    }

    public OrderItemDTO toDTO() {
        return new OrderItemDTO(product.toDTO(), quantity, totalPrice);
    }

    public OrderItemEntity toPersistence() {
        return new OrderItemEntity(product.toPersistence(), quantity, totalPrice);
    }
}
