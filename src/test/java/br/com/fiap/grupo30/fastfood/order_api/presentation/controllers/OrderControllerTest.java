package br.com.fiap.grupo30.fastfood.order_api.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.customer.CustomerUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.DeliverOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.FinishPreparingOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.GetOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartNewOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order.StartPreparingOrderUseCase;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product.*;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;
import br.com.fiap.grupo30.fastfood.order_api.utils.OrderHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class OrderControllerTest {

    private MockMvc mockMvc;

    private final CustomerUseCase customerUseCase;
    private final ProductUseCase productUseCase;
    private static final String DEFAULT_CUSTOMER_CPF = "29375235017";
    @Mock private GetOrderUseCase getOrderUseCase;
    @Mock private StartNewOrderUseCase startNewOrderUseCase;
    @Mock private StartPreparingOrderUseCase startPreparingOrderUseCase;
    @Mock private DeliverOrderUseCase deliverOrderUseCase;
    @Mock private FinishPreparingOrderUseCase finishPreparingOrderUseCase;

    @InjectMocks private OrderController orderController;

    private static final String PATH_VARIABLE_ID = "/orders/{id}";

    @Autowired
    public OrderControllerTest(CustomerUseCase customerUseCase, ProductUseCase productUseCase) {
        this.customerUseCase = customerUseCase;
        this.productUseCase = productUseCase;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Nested
    class GetProduct {
        @Test
        void shouldGetOrderById() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(1L, customerUseCase, productUseCase);
            when(startNewOrderUseCase.execute(
                            any(OrderGateway.class),
                            any(CustomerUseCase.class),
                            DEFAULT_CUSTOMER_CPF))
                    .thenReturn(orderDTO);

            // Act & Assert
            mockMvc.perform(get(PATH_VARIABLE_ID, orderId).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderId").value(orderId))
                    .andExpect(jsonPath("$.status").value(orderDTO.getStatus()))
                    .andExpect(jsonPath("$.itens").value(orderDTO.getItems()))
                    .andExpect(jsonPath("$.totalPrice").value(orderDTO.getTotalPrice()))
                    .andExpect(jsonPath("$.customer").value(orderDTO.getCustomer()))
                    .andExpect(jsonPath("$.paymentStatus").value(orderDTO.getPayment()));

            // Verify
            verify(getOrderUseCase, times(1)).execute(any(OrderGateway.class), eq(1L));
        }
    }

    @Nested
    class PostOrder {
        @Test
        void shouldStartNewOrderAndReturn201() throws Exception {
            // Arrange
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(1L, customerUseCase, productUseCase);
            when(startNewOrderUseCase.execute(
                            any(OrderGateway.class),
                            any(CustomerUseCase.class),
                            DEFAULT_CUSTOMER_CPF))
                    .thenReturn(orderDTO);

            // Act & Assert
            String jsonContent = new ObjectMapper().writeValueAsString(orderDTO);
            mockMvc.perform(
                            post("/order")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonContent))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value("Burger"));

            // Verify
            verify(startNewOrderUseCase, times(1))
                    .execute(
                            any(OrderGateway.class),
                            any(CustomerUseCase.class),
                            DEFAULT_CUSTOMER_CPF);
        }

        @Test
        void shouldInvokeStartNewOrderUseCase() throws Exception {
            // Arrange
            OrderDTO orderDTO =
                    OrderHelper.createDefaultOrderDTOWithId(1L, customerUseCase, productUseCase);

            // Act & Assert
            String jsonContent = new ObjectMapper().writeValueAsString(orderDTO);
            mockMvc.perform(
                            post("/order")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(jsonContent))
                    .andReturn();

            // Verify
            verify(startNewOrderUseCase, times(1))
                    .execute(
                            any(OrderGateway.class),
                            any(CustomerUseCase.class),
                            DEFAULT_CUSTOMER_CPF);
        }
    }

    @Nested
    class PutOrder {
        @Test
        void shouldStartPreperingOrderAndReturn200() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderItemDTO[] orderItem = {OrderHelper.createOrderItem(productUseCase).toDTO()};
            OrderStatus orderStatus = OrderStatus.PREPARING;
            OrderDTO updatedOrderDTO =
                    OrderHelper.createUpdatedOrderDTO(
                            orderId,
                            orderStatus,
                            orderItem,
                            100.00,
                            OrderHelper.getDefaultCustomer(customerUseCase, DEFAULT_CUSTOMER_CPF)
                                    .toDTO(),
                            new PaymentDTO(PaymentStatus.COLLECTED, 100.00));

            when(startPreparingOrderUseCase.execute(any(OrderGateway.class), 1L))
                    .thenReturn(updatedOrderDTO);

            // Verify
            verify(startPreparingOrderUseCase, times(1)).execute(any(OrderGateway.class), 1L);
        }

        @Test
        void shouldDeliverOrderAndReturn200() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderItemDTO[] orderItem = {OrderHelper.createOrderItem(productUseCase).toDTO()};
            OrderStatus orderStatus = OrderStatus.DELIVERED;
            OrderDTO updatedOrderDTO =
                    OrderHelper.createUpdatedOrderDTO(
                            orderId,
                            orderStatus,
                            orderItem,
                            100.00,
                            OrderHelper.getDefaultCustomer(customerUseCase, DEFAULT_CUSTOMER_CPF)
                                    .toDTO(),
                            new PaymentDTO(PaymentStatus.COLLECTED, 100.00));

            when(deliverOrderUseCase.execute(any(OrderGateway.class), 1L))
                    .thenReturn(updatedOrderDTO);

            // Verify
            verify(deliverOrderUseCase, times(1)).execute(any(OrderGateway.class), 1L);
        }

        @Test
        void shouldFinishPreparingOrderAndReturn200() throws Exception {
            // Arrange
            Long orderId = 1L;
            OrderItemDTO[] orderItem = {OrderHelper.createOrderItem(productUseCase).toDTO()};
            OrderStatus orderStatus = OrderStatus.READY;
            OrderDTO updatedOrderDTO =
                    OrderHelper.createUpdatedOrderDTO(
                            orderId,
                            orderStatus,
                            orderItem,
                            100.00,
                            OrderHelper.getDefaultCustomer(customerUseCase, DEFAULT_CUSTOMER_CPF)
                                    .toDTO(),
                            new PaymentDTO(PaymentStatus.COLLECTED, 100.00));

            when(finishPreparingOrderUseCase.execute(any(OrderGateway.class), 1L))
                    .thenReturn(updatedOrderDTO);

            // Verify
            verify(finishPreparingOrderUseCase, times(1)).execute(any(OrderGateway.class), 1L);
        }
    }
}
