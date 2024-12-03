package br.com.fiap.grupo30.fastfood.order_api.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.DeliverOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.FinishPreparingOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.GetOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartNewOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartPreparingOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.SubmitOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product.*;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.CustomerHelper;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import br.com.fiap.grupo30.fastfood.order_api.utils.ProductHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderControllerTest {

    @Mock private CustomerUseCase customerUseCase;
    @Mock private ProductUseCase productUseCase;
    private static final String DEFAULT_CUSTOMER_CPF = "29375235017";
    @Mock private GetOrderUseCase getOrderUseCase;
    @Mock private StartNewOrderUseCase startNewOrderUseCase;
    @Mock private StartPreparingOrderUseCase startPreparingOrderUseCase;
    @Mock private DeliverOrderUseCase deliverOrderUseCase;
    @Mock private FinishPreparingOrderUseCase finishPreparingOrderUseCase;
    @Mock private SubmitOrderUseCase submitOrderUseCase;

    @InjectMocks private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class GetOrder {
        @Test
        void shouldGetOrderById() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(
                            1L, CustomerHelper.valid(), ProductHelper.validProduct());
            when(startNewOrderUseCase.execute(
                            any(OrderGateway.class),
                            any(CustomerUseCase.class),
                            eq(DEFAULT_CUSTOMER_CPF)))
                    .thenReturn(orderDTO);

            orderController.findById(orderId);

            // Verify
            verify(getOrderUseCase, times(1)).execute(any(OrderGateway.class), eq(1L));
        }
    }

    @Nested
    class PostOrder {
        @Test
        void shouldStartNewOrderAndReturn201() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(
                            1L, CustomerHelper.valid(), ProductHelper.validProduct());
            when(startNewOrderUseCase.execute(
                            any(OrderGateway.class),
                            any(CustomerUseCase.class),
                            eq(DEFAULT_CUSTOMER_CPF)))
                    .thenReturn(orderDTO);

            orderController.findById(orderId);

            // Verify
            verify(getOrderUseCase, times(1)).execute(any(OrderGateway.class), eq(1L));
        }

        @Test
        void shouldInvokeStartNewOrderUseCase() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(
                            1L, CustomerHelper.valid(), ProductHelper.validProduct());
            when(startNewOrderUseCase.execute(
                            any(OrderGateway.class),
                            any(CustomerUseCase.class),
                            eq(DEFAULT_CUSTOMER_CPF)))
                    .thenReturn(orderDTO);

            orderController.findById(orderId);

            // Verify
            verify(getOrderUseCase, times(1)).execute(any(OrderGateway.class), eq(1L));
        }
    }

    @Nested
    class PutOrder {
        @Test
        void shouldStartPreperingOrderAndReturn200() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderItemDTO[] orderItem = {
                OrderHelper.createOrderItem(ProductHelper.validProduct()).toDTO()
            };
            OrderStatus orderStatus = OrderStatus.PREPARING;
            OrderDTO updatedOrderDTO =
                    OrderHelper.createUpdatedOrderDTO(
                            orderId,
                            orderStatus,
                            orderItem,
                            100.00,
                            CustomerHelper.valid().toDTO(),
                            new PaymentDTO(PaymentStatus.COLLECTED, 100.00));

            when(startPreparingOrderUseCase.execute(any(OrderGateway.class), eq(1L)))
                    .thenReturn(updatedOrderDTO);

            orderController.startPreparingOrder(orderId);

            // Verify
            verify(startPreparingOrderUseCase, times(1)).execute(any(OrderGateway.class), eq(1L));
        }

        @Test
        void shouldDeliverOrderAndReturn200() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderItemDTO[] orderItem = {
                OrderHelper.createOrderItem(ProductHelper.validProduct()).toDTO()
            };
            OrderStatus orderStatus = OrderStatus.DELIVERED;
            OrderDTO updatedOrderDTO =
                    OrderHelper.createUpdatedOrderDTO(
                            orderId,
                            orderStatus,
                            orderItem,
                            100.00,
                            CustomerHelper.valid().toDTO(),
                            new PaymentDTO(PaymentStatus.COLLECTED, 100.00));

            when(deliverOrderUseCase.execute(any(OrderGateway.class), eq(1L)))
                    .thenReturn(updatedOrderDTO);

            orderController.deliverOrder(orderId);

            // Verify
            verify(deliverOrderUseCase, times(1)).execute(any(OrderGateway.class), eq(1L));
        }

        @Test
        void shouldFinishPreparingOrderAndReturn200() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderItemDTO[] orderItem = {
                OrderHelper.createOrderItem(ProductHelper.validProduct()).toDTO()
            };
            OrderStatus orderStatus = OrderStatus.READY;
            OrderDTO updatedOrderDTO =
                    OrderHelper.createUpdatedOrderDTO(
                            orderId,
                            orderStatus,
                            orderItem,
                            100.00,
                            CustomerHelper.valid().toDTO(),
                            new PaymentDTO(PaymentStatus.COLLECTED, 100.00));

            when(finishPreparingOrderUseCase.execute(any(OrderGateway.class), eq(1L)))
                    .thenReturn(updatedOrderDTO);

            orderController.finishPreparingOrder(orderId);

            // Verify
            verify(finishPreparingOrderUseCase, times(1)).execute(any(OrderGateway.class), eq(1L));
        }
    }
}
