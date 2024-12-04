package br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderGatewayTest {

    private JpaOrderRepository jpaOrderRepository;
    private OrderGateway orderGateway;

    @BeforeEach
    void setUp() {
        jpaOrderRepository = mock(JpaOrderRepository.class);
        orderGateway = new OrderGateway(jpaOrderRepository);
    }

    @Nested
    class FindOrdersByStatusId {
        @Test
        void shouldReturnOrdersByCategoryIdWithCorrectSize() {
            // Arrange
            OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
            when(jpaOrderRepository.findOrdersByStatus(OrderStatus.DRAFT))
                    .thenReturn(List.of(entity));

            // Act
            List<Order> result = orderGateway.findOrdersByStatus(OrderStatus.DRAFT);

            // Assert
            assertThat(result).hasSize(1);
        }

        @Test
        void shouldReturnOrdersByCategoryIdWithCorrectName() {
            // Arrange
            OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
            when(jpaOrderRepository.findOrdersByStatus(OrderStatus.DRAFT))
                    .thenReturn(List.of(entity));

            // Act
            List<Order> result = orderGateway.findOrdersByStatus(OrderStatus.DRAFT);

            // Assert
            assertThat(result.get(0).getStatus()).isEqualTo(OrderStatus.DRAFT);
        }

        @Test
        void shouldReturnEmptyListWhenNoOrdersForStatusId() {
            // Arrange
            when(jpaOrderRepository.findOrdersByStatus(OrderStatus.DRAFT)).thenReturn(List.of());

            // Act
            List<Order> result = orderGateway.findOrdersByStatus(OrderStatus.DRAFT);

            // Assert
            assertThat(result).isEmpty();
        }
    }

    @Nested
    class FindOrderById {
        @Test
        void shouldReturnOrderById() {
            // Arrange
            OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
            when(jpaOrderRepository.findById(1L)).thenReturn(Optional.of(entity));

            // Act
            Order result = orderGateway.findById(1L);
            // Assert
            assertThat(result.getCustomerId()).isNotNull();
        }

        @Test
        void shouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
            // Arrange
            when(jpaOrderRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> orderGateway.findById(1L))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("Order not found");
        }
    }

    @Nested
    class SaveOrder {
        @Test
        void shouldSaveOrder() {
            // Arrange
            OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
            when(jpaOrderRepository.save(Mockito.any())).thenReturn(entity);

            // Act
            Order result = orderGateway.save(OrderHelper.createDefaultOrder());

            // Assert
            assertThat(result.getCustomerId()).isNotNull();
        }
    }
}
