package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;

public class InvalidCpfException extends DomainValidationException {
    @Serial private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "Invalid CPF: %s";

    public InvalidCpfException(String cpf) {
        super(String.format(MESSAGE_TEMPLATE, cpf));
    }

    public InvalidCpfException(String cpf, Throwable exception) {
        super(String.format(MESSAGE_TEMPLATE, cpf), exception);
    }
}
