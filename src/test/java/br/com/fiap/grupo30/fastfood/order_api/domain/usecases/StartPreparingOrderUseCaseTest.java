package br.com.fiap.grupo30.fastfood.order_api.domain.usecases;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartPreparingOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.payment.PaymentUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.OrderController;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

@Component
class StartPreparingOrderUseCaseTest {

    @Mock private OrderGateway orderGateway;
    @Mock private PaymentUseCase paymentUseCase;
    @Mock private JpaOrderRepository jpaOrderRepository;
    @Mock private OrderController orderController;

    private StartPreparingOrderUseCase startPreparingOrderUseCase;

    private static final Long DEFAULT_ORDERID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPreparingOrderUseCase = new StartPreparingOrderUseCase();
    }

    @Test
    void shouldReturnOrderDTOWithCorrectStatus() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, OrderStatus.SUBMITTED);

        when(orderGateway.findById(order.getId())).thenReturn(order);
        when(orderGateway.save(any(Order.class))).thenReturn(order);
        when(paymentUseCase.findPaymentStateByOrderId(order.getId()))
                .thenReturn(Payment.create(PaymentStatus.COLLECTED, order.getTotalPrice()));

        // Act
        OrderDTO result =
                startPreparingOrderUseCase.execute(orderGateway, paymentUseCase, order.getId());

        // Assert
        assertThat(result.getStatus()).isEqualTo(order.getStatus());
    }

    @Test
    void shouldReturnOrderDTOWithCorrectCustomerName() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, OrderStatus.SUBMITTED);

        when(orderGateway.findById(order.getId())).thenReturn(order);
        when(orderGateway.save(any(Order.class))).thenReturn(order);
        when(paymentUseCase.findPaymentStateByOrderId(order.getId()))
                .thenReturn(Payment.create(PaymentStatus.COLLECTED, order.getTotalPrice()));

        // Act
        OrderDTO result =
                startPreparingOrderUseCase.execute(orderGateway, paymentUseCase, order.getId());

        // Assert
        assertThat(result.getCustomerId()).isEqualTo(order.getCustomerId());
    }
}
