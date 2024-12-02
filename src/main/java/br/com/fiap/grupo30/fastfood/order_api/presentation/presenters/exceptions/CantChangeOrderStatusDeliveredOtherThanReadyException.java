package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public class CantChangeOrderStatusDeliveredOtherThanReadyException
        extends DomainValidationException {

    @Serial private static final long serialVersionUID = 1L;
    private static final String MESSAGE =
            "Can not to finish the delivery an order that does not have the ready status";

    public CantChangeOrderStatusDeliveredOtherThanReadyException() {
        super(MESSAGE);
    }

    public CantChangeOrderStatusDeliveredOtherThanReadyException(Throwable exception) {
        super(MESSAGE, exception);
    }
}
