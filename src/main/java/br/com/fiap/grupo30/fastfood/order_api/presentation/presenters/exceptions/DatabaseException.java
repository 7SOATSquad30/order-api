package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public class DatabaseException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L;

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
