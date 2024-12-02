package br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ValidationError extends StandardError {
    private final List<FieldMessage> errors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}
