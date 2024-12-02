package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public class CantChangeOrderProductsAfterSubmitException extends DomainValidationException {

    @Serial private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Can not change products of a submitted order";

    public CantChangeOrderProductsAfterSubmitException() {
        super(MESSAGE);
    }

    public CantChangeOrderProductsAfterSubmitException(Throwable exception) {
        super(MESSAGE, exception);
    }
}
