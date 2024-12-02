package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public abstract class ResourceBadRequestException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L;

    public ResourceBadRequestException(String msg) {
        super(msg);
    }

    public ResourceBadRequestException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
