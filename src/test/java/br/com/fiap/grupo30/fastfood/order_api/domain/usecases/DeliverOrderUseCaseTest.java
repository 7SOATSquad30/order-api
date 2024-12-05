package br.com.fiap.grupo30.fastfood.order_api.domain.usecases;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.DeliverOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartNewOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product.ProductUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.OrderController;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

@Component
class DeliverOrderUseCaseTest {

    @Mock private OrderGateway orderGateway;
    @Mock private CustomerUseCase customerUseCase;
    @Mock private JpaOrderRepository jpaOrderRepository;
    @Mock private OrderController orderController;
    @Mock private ProductUseCase productUseCase;

    @InjectMocks private StartNewOrderUseCase startNewOrderUseCase;
    private DeliverOrderUseCase deliverOrderUseCase;

    private static final Long DEFAULT_ORDERID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startNewOrderUseCase = new StartNewOrderUseCase();
        deliverOrderUseCase = new DeliverOrderUseCase();
    }

    @Test
    void shouldReturnOrderDTOWithCorrectId() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, OrderStatus.READY);

        when(orderGateway.findById(order.getId())).thenReturn(order);
        when(orderGateway.save(any(Order.class))).thenReturn(order);

        // Act
        OrderDTO result = deliverOrderUseCase.execute(orderGateway, order.getId());

        // Assert
        assertThat(result.getOrderId()).isEqualTo(order.getId());
    }

    @Test
    void shouldReturnOrderDTOWithCorrectStatus() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, OrderStatus.READY);

        when(orderGateway.findById(order.getId())).thenReturn(order);
        when(orderGateway.save(any(Order.class))).thenReturn(order);

        // Act
        OrderDTO result = deliverOrderUseCase.execute(orderGateway, order.getId());

        // Assert
        assertThat(result.getStatus()).isEqualTo(order.getStatus());
    }

    @Test
    void shouldReturnOrderDTOWithCorrectCustomer() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, OrderStatus.READY);

        when(orderGateway.findById(order.getId())).thenReturn(order);
        when(orderGateway.save(any(Order.class))).thenReturn(order);

        // Act
        OrderDTO result = deliverOrderUseCase.execute(orderGateway, order.getId());

        // Assert
        assertThat(result.getCustomerId()).isEqualTo(order.getCustomerId());
    }
}
