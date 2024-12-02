package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public class InvalidOrderStatusException extends RuntimeException {

    @Serial private static final long serialVersionUID = 1L;

    private static final String MESSAGE_TEMPLATE = "Invalid order status: %s";

    public InvalidOrderStatusException(String status) {
        super(String.format(MESSAGE_TEMPLATE, status));
    }

    public InvalidOrderStatusException(String status, Throwable exception) {
        super(String.format(MESSAGE_TEMPLATE, status), exception);
    }
}
