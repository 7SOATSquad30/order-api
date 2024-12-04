package br.com.fiap.grupo30.fastfood.order_api.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.AddProductToOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.DeliverOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.FinishPreparingOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.GetOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.ListOrdersByStatusUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.ListOrdersWithSpecificStatusesUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.RemoveProductFromOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartNewOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartPreparingOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.SubmitOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product.*;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.AddOrderProductRequest;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderProductsAfterSubmitException;
import br.com.fiap.grupo30.fastfood.order_api.utils.CustomerHelper;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import br.com.fiap.grupo30.fastfood.order_api.utils.ProductHelper;
import java.util.ArrayList;
import java.util.List;
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
    @Mock private AddProductToOrderUseCase addProductToOrderUseCase;
    @Mock private RemoveProductFromOrderUseCase removeProductFromOrderUseCase;
    @Mock private ListOrdersWithSpecificStatusesUseCase listOrdersWithSpecificStatusesUseCase;
    @Mock private ListOrdersByStatusUseCase listOrdersByStatusUseCase;

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
                            1L, CustomerHelper.valid(), ProductHelper.valid());
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
        void shouldListOrdersWithSpecificStatusesUseCase() throws Exception {
            Long orderId = 1L;
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(
                            1L, CustomerHelper.valid(), ProductHelper.valid());
            when(submitOrderUseCase.execute(any(OrderGateway.class), eq(orderId)))
                    .thenReturn(orderDTO);

            orderController.submitOrder(orderId);
            // Arrange
            orderController.findOrdersWithSpecificStatuses();

            // Verify
            verify(listOrdersWithSpecificStatusesUseCase, times(1))
                    .execute(any(OrderGateway.class));
        }

        @Test
        void shouldListAllOrdersByStatus() throws Exception {
            // Arrange
            String status = "DRAFT";
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(
                            1L, CustomerHelper.valid(), ProductHelper.valid());
            OrderDTO orderDTO2 =
                    OrderHelper.createDefaultOrderDTOWithId(
                            2L, CustomerHelper.valid(), ProductHelper.valid());
            List<OrderDTO> listOrderDTO = new ArrayList<>();
            listOrderDTO.add(orderDTO);
            listOrderDTO.add(orderDTO2);

            when(listOrdersByStatusUseCase.execute(any(OrderGateway.class), eq(status)))
                    .thenReturn(listOrderDTO);

            orderController.findOrdersByStatus(status);

            // Verify
            verify(listOrdersByStatusUseCase, times(1))
                    .execute(any(OrderGateway.class), eq(status));
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
                            1L, CustomerHelper.valid(), ProductHelper.valid());
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
                            1L, CustomerHelper.valid(), ProductHelper.valid());
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
        void shouldInvokeAddProductToOrderUseCase() throws Exception {
            // Arrange
            Long orderId = 1L;
            Long productId = 1L;
            Long productQuantity = 2L;
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(
                            1L, CustomerHelper.valid(), ProductHelper.valid());
            when(addProductToOrderUseCase.execute(
                            any(OrderGateway.class),
                            any(ProductUseCase.class),
                            eq(orderId),
                            eq(productId),
                            eq(productQuantity)))
                    .thenReturn(orderDTO);

            if (orderDTO.getStatus() != OrderStatus.DRAFT) {
                throw new CantChangeOrderProductsAfterSubmitException();
            }

            orderController.addProduct(
                    orderId, new AddOrderProductRequest(productId, productQuantity));

            // Verify
            verify(addProductToOrderUseCase, times(1))
                    .execute(
                            any(OrderGateway.class),
                            any(ProductUseCase.class),
                            eq(1L),
                            eq(1L),
                            eq(2L));
        }

        @Test
        void shouldInvokeRemoveProductFromOrderUseCase() throws Exception {
            // Arrange
            Long orderId = 1L;
            Long productId = 1L;
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(
                            1L, CustomerHelper.valid(), ProductHelper.valid());
            when(removeProductFromOrderUseCase.execute(
                            any(OrderGateway.class),
                            any(ProductUseCase.class),
                            eq(orderId),
                            eq(productId)))
                    .thenReturn(orderDTO);

            orderController.removeProduct(orderId, productId);

            // Verify
            verify(removeProductFromOrderUseCase, times(1))
                    .execute(
                            any(OrderGateway.class),
                            any(ProductUseCase.class),
                            eq(orderId),
                            eq(productId));
        }

        @Test
        void shouldInvokeSubmitOrderUseCase() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(
                            1L, CustomerHelper.valid(), ProductHelper.valid());
            when(submitOrderUseCase.execute(any(OrderGateway.class), eq(orderId)))
                    .thenReturn(orderDTO);

            orderController.submitOrder(orderId);

            // Verify
            verify(submitOrderUseCase, times(1)).execute(any(OrderGateway.class), eq(orderId));
        }
    }

    @Nested
    class PutOrder {
        @Test
        void shouldStartPreperingOrderAndReturn200() throws Exception {
            // Arrange
            Long orderId = 1L;
            List<OrderItemDTO> orderItem =
                    List.of(OrderHelper.createOrderItem(ProductHelper.valid()).toDTO());
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
            List<OrderItemDTO> orderItem =
                    List.of(OrderHelper.createOrderItem(ProductHelper.valid()).toDTO());
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
            List<OrderItemDTO> orderItem =
                    List.of(OrderHelper.createOrderItem(ProductHelper.valid()).toDTO());
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
