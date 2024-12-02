package br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "tb_order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private ProductEntity product;

    @Column(nullable = false)
    @Min(1L)
    private Long quantity;

    @Column(nullable = false)
    @Min(0)
    private Double totalPrice;

    public OrderItemEntity(ProductEntity product, Long quantity, Double totalPrice) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public void setParentRelation(OrderEntity order) {
        this.order = order;
    }

    public OrderItem toDomainEntity() {
        return new OrderItem(product.toDomainEntity(), quantity);
    }
}
