package br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "tb_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private Long customerId;

    @OneToOne(
            mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private PaymentEntity payment;

    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<OrderItemEntity> items;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant deletedAt;

    public OrderEntity(
            Long orderId,
            OrderStatus status,
            Long customerId,
            PaymentEntity payment,
            Collection<OrderItemEntity> orderItems) {
        this.id = orderId;
        this.status = status;
        this.customerId = customerId;
        this.payment = payment;
        this.items = orderItems;

        this.payment.setParentRelation(this);
        for (OrderItemEntity orderItem : this.items) {
            orderItem.setParentRelation(this);
        }
    }

    public Order toDomainEntity() {
        return new Order(
                id,
                status,
                customerId,
                payment.toDomainEntity(),
                items.stream()
                        .map(OrderItemEntity::toDomainEntity)
                        .collect(Collectors.toCollection(ArrayList::new)));
    }
}
