package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public abstract class DomainValidationException extends ResourceBadRequestException {
    @Serial private static final long serialVersionUID = 1L;

    public DomainValidationException(String msg) {
        super(msg);
    }

    public DomainValidationException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
