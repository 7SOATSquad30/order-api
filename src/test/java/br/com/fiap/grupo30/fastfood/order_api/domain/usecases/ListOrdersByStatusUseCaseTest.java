package br.com.fiap.grupo30.fastfood.order_api.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.ListOrdersByStatusUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartNewOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ListOrdersByStatusUseCaseTest {

    @Mock private OrderGateway orderGateway;
    @Mock private CustomerUseCase customerUseCase;

    private ListOrdersByStatusUseCase listOrdersByStatusUseCase;

    @InjectMocks private StartNewOrderUseCase startNewOrderUseCase;

    private static final Long DEFAULT_ORDERID = 1L;
    private static final OrderStatus DEFAULT_ORDERSTATUS = OrderStatus.DRAFT;
    private static final String DEFAULT_ORDERSTATUS_STRING = "DRAFT";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listOrdersByStatusUseCase = new ListOrdersByStatusUseCase();
    }

    @Test
    void shouldReturnOrderDTOWithCorrectStatus() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, DEFAULT_ORDERSTATUS);
        Order order2 = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, DEFAULT_ORDERSTATUS);
        List<Order> orders = List.of(order, order2);

        when(orderGateway.findOrdersByStatus(any(OrderStatus.class))).thenReturn(orders);

        List<OrderDTO> result =
                listOrdersByStatusUseCase.execute(orderGateway, DEFAULT_ORDERSTATUS_STRING);

        // Assert
        assertThat(result.get(0).getStatus()).isEqualTo(DEFAULT_ORDERSTATUS);
    }
}
