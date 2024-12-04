package br.com.fiap.grupo30.fastfood.order_api.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartNewOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.OrderController;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.CustomerHelper;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StartNewOrderUseCaseTest {

    @Mock private OrderGateway orderGateway;
    @Mock private CustomerUseCase customerUseCase;

    @InjectMocks private StartNewOrderUseCase startNewOrderUseCase;
    @InjectMocks private OrderController orderController;

    private static final Long DEFAULT_ORDERID = 1L;
    private static final String DEFAULT_CUSTOMER_CPF = "29375235017";
    private static final OrderStatus DEFAULT_ORDERSTATUS = OrderStatus.DRAFT;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOrderDTOWithCorrectStatus() throws Exception {
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
    void shouldReturnOrderDTOWithCorrectCustomerName() throws Exception {
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

    @Test
    void shouldReturnOrderDTOWithCorrectTotalPrice() throws Exception {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, DEFAULT_ORDERSTATUS);

        when(orderGateway.save(any(Order.class))).thenReturn(order);
        when(customerUseCase.findCustomerByCpf(any(String.class)))
                .thenReturn(CustomerHelper.createDefaultCustomerWithId(order.getCustomerId()));

        // Act
        OrderDTO result =
                startNewOrderUseCase.execute(orderGateway, customerUseCase, DEFAULT_CUSTOMER_CPF);

        // Assert
        assertThat(result.getTotalPrice()).isEqualTo(order.getTotalPrice());
    }
}
