package br.com.fiap.grupo30.fastfood.order_api.domain.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.RemoveProductFromOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product.ProductUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.OrderController;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import br.com.fiap.grupo30.fastfood.order_api.utils.ProductHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

@Component
class RemoveProductUseCaseTest {

    @Mock private OrderGateway orderGateway;
    @Mock private ProductUseCase productUseCase;
    @Mock private JpaOrderRepository jpaOrderRepository;
    @Mock private OrderController orderController;

    private RemoveProductFromOrderUseCase useCase;

    private static final Long DEFAULT_ORDERID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new RemoveProductFromOrderUseCase();
    }

    @Test
    void shouldReturnOrderDTOWithCorrectStatus() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, OrderStatus.DRAFT);
        Long productId = ProductHelper.valid();
        ProductDTO product = ProductHelper.validProduct(productId);

        when(orderGateway.findById(order.getId())).thenReturn(order);
        when(orderGateway.save(any(Order.class))).thenReturn(order);
        when(productUseCase.findProductById(product.getProductId())).thenReturn(product);

        // Act
        OrderDTO result = useCase.execute(orderGateway, productUseCase, order.getId(), productId);

        // Assert
        assertThat(result.getStatus()).isEqualTo(order.getStatus());
    }

    @Test
    void shouldReturnOrderDTOWithCorrectCustomerName() {
        // Arrange
        Order order = OrderHelper.createDefaultOrderStatus(DEFAULT_ORDERID, OrderStatus.DRAFT);
        Long productId = ProductHelper.valid();
        ProductDTO product = ProductHelper.validProduct(productId);

        when(orderGateway.findById(order.getId())).thenReturn(order);
        when(orderGateway.save(any(Order.class))).thenReturn(order);
        when(productUseCase.findProductById(product.getProductId())).thenReturn(product);

        // Act
        OrderDTO result = useCase.execute(orderGateway, productUseCase, order.getId(), productId);

        // Assert
        assertThat(result.getCustomerId()).isEqualTo(order.getCustomerId());
    }
}
