package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public class CantChangeOrderStatusReadyOtherThanPreparingException
        extends DomainValidationException {

    @Serial private static final long serialVersionUID = 1L;
    private static final String MESSAGE =
            "Can not to finish the prepare an order that does not have the preparing status";

    public CantChangeOrderStatusReadyOtherThanPreparingException() {
        super(MESSAGE);
    }

    public CantChangeOrderStatusReadyOtherThanPreparingException(Throwable exception) {
        super(MESSAGE, exception);
    }
}
