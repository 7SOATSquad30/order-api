package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.payment.PaymentUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderStatusPreparingOtherThanSubmittedException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.OrderPaymentIsStillPendingException;
import org.springframework.stereotype.Component;

@Component
public class StartPreparingOrderUseCase {

    public OrderDTO execute(
            OrderGateway orderGateway, PaymentUseCase paymentUseCase, Long orderId) {
        Order order = orderGateway.findById(orderId);

        if (order.getStatus() != OrderStatus.SUBMITTED) {
            throw new CantChangeOrderStatusPreparingOtherThanSubmittedException();
        }

        Payment orderPayment = paymentUseCase.findPaymentStateByOrderId(orderId);

        if (!PaymentStatus.COLLECTED.equals(orderPayment.getStatus())) {
            throw new OrderPaymentIsStillPendingException();
        }

        order.setStatus(OrderStatus.PREPARING);
        return orderGateway.save(order).toDTO();
    }
}
