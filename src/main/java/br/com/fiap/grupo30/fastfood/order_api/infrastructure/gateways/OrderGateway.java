package br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.ResourceNotFoundException;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderGateway implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    @Autowired
    public OrderGateway(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrdersWithSpecificStatuses() {
        return jpaOrderRepository.findOrdersWithSpecificStatuses().stream()
                .map(OrderEntity::toDomainEntity)
                .toList();
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus orderStatus) {
        return jpaOrderRepository.findOrdersByStatus(orderStatus).stream()
                .sorted(Comparator.comparing(OrderEntity::getCreatedAt))
                .map(OrderEntity::toDomainEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Long orderId) {
        OrderEntity entity =
                jpaOrderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return entity.toDomainEntity();
    }

    @Override
    @Transactional()
    public Order findByIdForUpdate(Long orderId) {
        OrderEntity entity =
                jpaOrderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return entity.toDomainEntity();
    }

    @Override
    @Transactional
    public Order save(Order order) {
        OrderEntity entity = jpaOrderRepository.save(order.toPersistence());
        return entity.toDomainEntity();
    }
}
