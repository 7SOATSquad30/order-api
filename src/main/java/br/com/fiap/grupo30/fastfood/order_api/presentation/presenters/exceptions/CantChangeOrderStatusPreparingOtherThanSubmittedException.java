package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public class CantChangeOrderStatusPreparingOtherThanSubmittedException
        extends DomainValidationException {

    @Serial private static final long serialVersionUID = 1L;
    private static final String MESSAGE =
            "Can not to prepare an order that does not have the submitted status";

    public CantChangeOrderStatusPreparingOtherThanSubmittedException() {
        super(MESSAGE);
    }

    public CantChangeOrderStatusPreparingOtherThanSubmittedException(Throwable exception) {
        super(MESSAGE, exception);
    }
}
