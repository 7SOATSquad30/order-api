package br.com.fiap.grupo30.fastfood.order_api.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartNewOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.OrderController;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.CustomerHelper;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

@Component
class FinishPreparingOrderUseCaseTest {

    @Mock private OrderGateway orderGateway;
    @Mock private CustomerUseCase customerUseCase;
    @Mock private JpaOrderRepository jpaOrderRepository;
    @Mock private OrderController orderController;

    @InjectMocks private StartNewOrderUseCase startNewOrderUseCase;

    private static final Long DEFAULT_ORDERID = 1L;
    private static final String DEFAULT_CUSTOMER_CPF = "29375235017";
    private static final OrderStatus DEFAULT_ORDERSTATUS = OrderStatus.READY;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startNewOrderUseCase = new StartNewOrderUseCase();
    }

    @Test
    void shouldReturnOrderDTOWithCorrectId() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, DEFAULT_ORDERSTATUS);

        when(orderGateway.save(any(Order.class))).thenReturn(order);
        when(customerUseCase.findCustomerByCpf(any(String.class)))
                .thenReturn(CustomerHelper.createDefaultCustomerWithId(order.getCustomerId()));

        // Act
        OrderDTO result =
                startNewOrderUseCase.execute(orderGateway, customerUseCase, DEFAULT_CUSTOMER_CPF);

        // Assert
        assertThat(result.getOrderId()).isEqualTo(order.getId());
    }

    @Test
    void shouldReturnOrderDTOWithCorrectStatus() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, DEFAULT_ORDERSTATUS);

        when(orderGateway.save(any(Order.class))).thenReturn(order);
        when(customerUseCase.findCustomerByCpf(any(String.class)))
                .thenReturn(CustomerHelper.createDefaultCustomerWithId(order.getCustomerId()));

        // Act
        OrderDTO result =
                startNewOrderUseCase.execute(orderGateway, customerUseCase, DEFAULT_CUSTOMER_CPF);

        // Assert
        assertThat(result.getStatus()).isEqualTo(order.getStatus());
    }

    @Test
    void shouldReturnOrderDTOWithCorrectCustomerName() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, DEFAULT_ORDERSTATUS);

        when(orderGateway.save(any(Order.class))).thenReturn(order);
        when(customerUseCase.findCustomerByCpf(any(String.class)))
                .thenReturn(CustomerHelper.createDefaultCustomerWithId(order.getCustomerId()));

        // Act
        OrderDTO result =
                startNewOrderUseCase.execute(orderGateway, customerUseCase, DEFAULT_CUSTOMER_CPF);

        // Assert
        assertThat(result.getCustomerId()).isEqualTo(order.getCustomerId());
    }
}
