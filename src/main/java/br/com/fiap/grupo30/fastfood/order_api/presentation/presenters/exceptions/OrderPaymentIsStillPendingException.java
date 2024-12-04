package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public class OrderPaymentIsStillPendingException extends ResourceBadRequestException {
    @Serial private static final long serialVersionUID = 1L;

    public OrderPaymentIsStillPendingException() {
        super("Order payment is still pending");
    }
}
