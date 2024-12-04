package br.com.fiap.grupo30.fastfood.order_api.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.GetOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.OrderController;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.AddOrderProductRequest;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetOrderUseCaseTest {

    private JpaOrderRepository jpaOrderRepository;
    private OrderGateway orderGateway;
    @Mock private CustomerUseCase customerUseCase;
    @Mock private OrderController orderController;
    @Mock private GetOrderUseCase getOrderUseCase;

    private static final Long DEFAULT_ORDERID = 1L;
    private static final Long DEFAULT_PRODUCTID = 1L;
    private static final OrderStatus DEFAULT_ORDERSTATUS = OrderStatus.DRAFT;
    private static final Double DEFAULT_TOTALPRICE = 10.5;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jpaOrderRepository = mock(JpaOrderRepository.class);
        orderGateway = new OrderGateway(jpaOrderRepository);
    }

    @Test
    void shouldReturnOrderDTOWithCorrectStatus() throws Exception {

        OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
        when(jpaOrderRepository.findById(DEFAULT_ORDERID)).thenReturn(Optional.of(entity));

        // Act
        Order result = orderGateway.findById(DEFAULT_ORDERID);

        orderController.addProduct(
                DEFAULT_ORDERID, new AddOrderProductRequest(DEFAULT_PRODUCTID, 100L));

        // Assert
        assertThat(result.getStatus()).isEqualTo(DEFAULT_ORDERSTATUS);
    }

    @Test
    void shouldReturnOrderDTOWithCorrectId() throws Exception {

        OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
        when(jpaOrderRepository.findById(DEFAULT_ORDERID)).thenReturn(Optional.of(entity));

        // Act
        Order result = orderGateway.findById(DEFAULT_ORDERID);

        orderController.addProduct(
                DEFAULT_ORDERID, new AddOrderProductRequest(DEFAULT_PRODUCTID, 100L));

        // Assert
        assertThat(result.getId()).isEqualTo(DEFAULT_ORDERID);
    }

    @Test
    void shouldReturnOrderDTOWithCorrectTotalPrice() throws Exception {

        OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
        when(jpaOrderRepository.findById(DEFAULT_ORDERID)).thenReturn(Optional.of(entity));

        // Act
        Order result = orderGateway.findById(DEFAULT_ORDERID);

        orderController.addProduct(
                DEFAULT_ORDERID, new AddOrderProductRequest(DEFAULT_PRODUCTID, 100L));

        // Assert
        assertThat(result.getTotalPrice()).isEqualTo(DEFAULT_TOTALPRICE);
    }

    @Test
    void shouldReturnOrderDTOWithCorrectCustomerName() throws Exception {
        // Arrange
        OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
        when(jpaOrderRepository.findById(DEFAULT_ORDERID)).thenReturn(Optional.of(entity));

        // Act
        Order result = orderGateway.findById(DEFAULT_ORDERID);

        orderController.addProduct(
                DEFAULT_ORDERID, new AddOrderProductRequest(DEFAULT_PRODUCTID, 100L));

        // Assert
        assertThat(result.getCustomerId()).isEqualTo(entity.getCustomerId());
    }

    @Test
    void shouldReturnOrderDTOWithCorrectPayment() throws Exception {

        OrderEntity entity = OrderHelper.createDefaultOrder().toPersistence();
        when(jpaOrderRepository.findById(DEFAULT_ORDERID)).thenReturn(Optional.of(entity));

        // Act
        Order result = orderGateway.findById(DEFAULT_ORDERID);

        orderController.addProduct(
                DEFAULT_ORDERID, new AddOrderProductRequest(DEFAULT_PRODUCTID, 100L));

        // Assert
        assertThat(result.getPayment().getStatus()).isEqualTo(entity.getPayment().getStatus());
    }
}
