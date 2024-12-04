package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.OrderItemEntity;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderItem {
    private Long productId;
    private Long quantity;
    private Double unitPrice;

    @Setter(AccessLevel.NONE)
    private Double totalPrice;

    public OrderItem(Long productId, Long quantity, Double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        recalculateTotalPrice();
    }

    public void setProduct(Long productId) {
        this.productId = productId;
        recalculateTotalPrice();
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        this.totalPrice = this.getUnitPrice() * this.quantity;
    }

    public OrderItemDTO toDTO() {
        return new OrderItemDTO(productId, quantity, totalPrice);
    }

    public OrderItemEntity toPersistence() {
        return new OrderItemEntity(productId, quantity, totalPrice, unitPrice);
    }
}
