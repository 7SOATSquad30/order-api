package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions;

import java.io.Serial;
import java.util.Collection;

public class CompositeDomainValidationException extends ResourceBadRequestException {

    @Serial private static final long serialVersionUID = 1L;

    public CompositeDomainValidationException(Collection<String> domainErrors) {
        super(String.join(", ", domainErrors));
    }

    public CompositeDomainValidationException(
            Collection<String> domainErrors, Throwable exception) {
        super(String.join(", ", domainErrors), exception);
    }
}
