package br.com.fiap.grupo30.fastfood.order_api.presentation.controllers.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderProductsAfterSubmitException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderStatusDeliveredOtherThanReadyException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderStatusPreparingOtherThanSubmittedException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderStatusReadyOtherThanPreparingException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CompositeDomainValidationException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.InvalidCpfException;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.InvalidOrderStatusException;
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
                    "Resource not found -Error message should match exception message");
        }
    }

    @Nested
    class DatabaseExceptionHandler {
        /*@Test
        void shouldHandleDatabaseExceptionAndReturn400() {
            // Arrange
            DatabaseException exception = new DatabaseException("Database exception 400");
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
        void shouldHandleDatabaseExceptionAndReturn400Throwable() {
            // Arrange
            DatabaseException exception =
                    new DatabaseException("Database exception 400 - new Throwable", new Throwable());
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
        }*/

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
                    "Database exception - Error message should match exception message");
        }

        @Test
        void shouldReturnCorrectErrorMessageForDatabaseExceptionThrowable() {
            // Arrange
            DatabaseException exception =
                    new DatabaseException("Database exception new Throwable", new Throwable());
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.database(exception, request);

            // Assert
            assertEquals(
                    "Database exception",
                    Objects.requireNonNull(response.getBody()).getError(),
                    "Database exception - Error message should match exception message");
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
                    "Validation exception - Error message should match exception message");
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

    @Nested
    class CantChangeOrderStatusDeliveredOtherThanReadyExceptionHandler {
        @Test
        void shouldHandleCantChangeOrderStatusDeliveredOtherThanReadyExceptionAndReturn400() {
            // Arrange
            CantChangeOrderStatusDeliveredOtherThanReadyException exception =
                    new CantChangeOrderStatusDeliveredOtherThanReadyException();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.cantChangeOrderStatusDeliveredOtherThanReady(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Cant Change Order Status Delivered Other Than Ready Exception");
        }

        @Test
        void
                shouldHandleCantChangeOrderStatusDeliveredOtherThanReadyExceptionAndReturn400Throwable() {
            // Arrange
            CantChangeOrderStatusDeliveredOtherThanReadyException exception =
                    new CantChangeOrderStatusDeliveredOtherThanReadyException(new Throwable());
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.cantChangeOrderStatusDeliveredOtherThanReady(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Cant Change Order Status Delivered Other Than Ready Exception");
        }
    }

    @Nested
    class CantChangeOrderStatusPreparingOtherThanSubmittedExceptionHandler {
        @Test
        void shouldHandleCantChangeOrderStatusPreparingOtherThanSubmittedExceptionAndReturn400() {
            // Arrange
            CantChangeOrderStatusPreparingOtherThanSubmittedException exception =
                    new CantChangeOrderStatusPreparingOtherThanSubmittedException();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.cantChangeOrderStatusPreparingOtherThanSubmitted(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Cant Change Order Status Preparing Other Than Submitted Exception");
        }

        @Test
        void
                shouldHandleCantChangeOrderStatusPreparingOtherThanSubmittedExceptionAndReturn400Throwable() {
            // Arrange
            CantChangeOrderStatusPreparingOtherThanSubmittedException exception =
                    new CantChangeOrderStatusPreparingOtherThanSubmittedException(new Throwable());
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.cantChangeOrderStatusPreparingOtherThanSubmitted(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Cant Change Order Status Delivered Other Than Submitted Exception");
        }
    }

    @Nested
    class CantChangeOrderStatusReadyOtherThanPreparingExceptionHandler {
        @Test
        void shouldHandleCantChangeOrderStatusReadyOtherThanPreparingExceptionAndReturn400() {
            // Arrange
            CantChangeOrderStatusReadyOtherThanPreparingException exception =
                    new CantChangeOrderStatusReadyOtherThanPreparingException();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.cantChangeOrderStatusReadyOtherThanPreparing(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Cant Change Order Status Ready Other Than Preparing Exception");
        }

        @Test
        void
                shouldHandleCantChangeOrderStatusDeliveredOtherThanPreparingExceptionAndReturn400Throwable() {
            // Arrange
            CantChangeOrderStatusReadyOtherThanPreparingException exception =
                    new CantChangeOrderStatusReadyOtherThanPreparingException(new Throwable());
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.cantChangeOrderStatusReadyOtherThanPreparing(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Cant Change Order Status Delivered Other Than Preparing Exception");
        }
    }

    @Nested
    class CantChangeOrderProductsAfterSubmitExceptionHandler {
        @Test
        void shouldHandleCantChangeOrderProductsAfterSubmitExceptionAndReturn400() {
            // Arrange
            CantChangeOrderProductsAfterSubmitException exception =
                    new CantChangeOrderProductsAfterSubmitException();
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.cantChangeOrderProductsAfterSubmit(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Cant Change Order Products After Submit Exception");
        }

        @Test
        void shouldHandleCantChangeOrderProductsAfterSubmitExceptionAndReturn400Throwable() {
            // Arrange
            CantChangeOrderProductsAfterSubmitException exception =
                    new CantChangeOrderProductsAfterSubmitException(new Throwable());
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.cantChangeOrderProductsAfterSubmit(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Cant Change Order Products After Submit");
        }
    }

    @Nested
    class InvalidOrderStatusExceptionHandler {
        @Test
        void shouldHandleInvalidOrderStatusExceptionAndReturn400() {
            // Arrange
            InvalidOrderStatusException exception = new InvalidOrderStatusException("Status");
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.invalidOrderStatus(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Invalid Order Status Exception 400");
        }

        @Test
        void shouldHandleInvalidOrderStatusExceptionAndReturn400Throwable() {
            // Arrange
            InvalidOrderStatusException exception =
                    new InvalidOrderStatusException("Status", new Throwable());
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.invalidOrderStatus(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Invalid Order Status Exception 400 Throwable");
        }
    }

    @Nested
    class CompositeDomainValidationExceptionHandler {
        @Test
        void shouldHandleCompositeDomainValidationExceptionAndReturn400() {
            // Arrange
            CompositeDomainValidationException exception =
                    new CompositeDomainValidationException(List.of("Domain Error"));
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.compositeDomainValidation(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Composite Domain Validation Exception");
        }

        @Test
        void shouldHandleCompositeDomainValidationExceptionAndReturn400Throwable() {
            // Arrange
            CompositeDomainValidationException exception =
                    new CompositeDomainValidationException(
                            List.of("Domain Error"), new Throwable());
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response =
                    handler.compositeDomainValidation(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Composite Domain Validation Exception");
        }
    }

    @Nested
    class CInvalidCpfExceptionHandler {
        @Test
        void shouldHandleInvalidCpfExceptionAndReturn400() {
            // Arrange
            InvalidCpfException exception = new InvalidCpfException("29375235017");
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.invalidCpf(exception, request);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Invalid CPF Exception");
        }

        @Test
        void shouldHandleInvalidOrderStatusExceptionAndReturn400Throwable() {
            // Arrange
            InvalidCpfException exception = new InvalidCpfException("29375235017", new Throwable());
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.invalidCpf(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.BAD_REQUEST,
                    response.getStatusCode(),
                    "Invalid Order Status Exception");
        }
    }
}
