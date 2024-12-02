package br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.order_api.utils.FieldErrorHelper;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ResourceExceptionHandlerTest {

    private static final String PATH_VARIABLE = "/orders";
    private static final String PATH_VARIABLE_ID = "/orders/{id}";

    @Nested
    class ResourceNotFoundExceptionHandler {
        @Test
        void shouldHandleResourceNotFoundExceptionAndReturn404() {
            // Arrange
            ResourceNotFoundException exception =
                    new ResourceNotFoundException("Resource not found");
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE_ID);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.entityNotFound(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.NOT_FOUND,
                    response.getStatusCode(),
                    "Expected HTTP status NOT_FOUND (404)");
        }

        @Test
        void shouldReturnCorrectErrorMessageForResourceNotFoundException() {
            // Arrange
            ResourceNotFoundException exception =
                    new ResourceNotFoundException("Resource not found");
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE_ID);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.entityNotFound(exception, request);

            // Assert
            assertEquals(
                    "Resource not found",
                    Objects.requireNonNull(response.getBody()).getError(),
                    "Error message should match exception message");
        }
    }

    @Nested
    class DatabaseExceptionHandler {
        @Test
        void shouldHandleDatabaseExceptionAndReturn400() {
            // Arrange
            DatabaseException exception = new DatabaseException("Database exception");
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.database(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Expected HTTP status BAD_REQUEST (400)");
        }

        @Test
        void shouldReturnCorrectErrorMessageForDatabaseException() {
            // Arrange
            DatabaseException exception = new DatabaseException("Database exception");
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.database(exception, request);

            // Assert
            assertEquals(
                    "Database exception",
                    Objects.requireNonNull(response.getBody()).getError(),
                    "Error message should match exception message");
        }
    }

    @Nested
    class MethodArgumentNotValidExceptionHandler {
        @Test
        void shouldHandleValidationExceptionAndReturn422() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors())
                    .thenReturn(List.of(FieldErrorHelper.createDefaultFieldError()));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    response.getStatusCode(),
                    "Expected HTTP status UNPROCESSABLE_ENTITY (422)");
        }

        @Test
        void shouldReturnValidationErrorDetails_exceptionMessage() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            FieldError fieldError = FieldErrorHelper.createDefaultFieldError();
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    "Validation exception",
                    Objects.requireNonNull(response.getBody()).getError(),
                    "Error message should match exception message");
        }

        @Test
        void shouldReturnValidationErrorDetails_singleError() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            FieldError fieldError = FieldErrorHelper.createDefaultFieldError();
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    1,
                    Objects.requireNonNull(response.getBody()).getErrors().size(),
                    "ValidationError should contain exactly one error");
        }

        @Test
        void shouldReturnValidationErrorDetails_errorFieldName() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            FieldError fieldError = FieldErrorHelper.createDefaultFieldError();
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    "name",
                    Objects.requireNonNull(response.getBody()).getErrors().get(0).getFieldName(),
                    "Field name in ValidationError should match expected value");
        }

        @Test
        void shouldReturnValidationErrorDetails_errorMessage() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            FieldError fieldError = FieldErrorHelper.createDefaultFieldError();
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    "Name is required",
                    Objects.requireNonNull(response.getBody()).getErrors().get(0).getMessage(),
                    "Error message in ValidationError should match expected value");
        }
    }
}
