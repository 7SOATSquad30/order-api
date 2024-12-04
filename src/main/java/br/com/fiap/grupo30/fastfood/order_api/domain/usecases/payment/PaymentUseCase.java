package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentUseCase {

    @Value("${external.services.payments-api.endpoint}")
    private String paymentsApiEndpoint;

    public Payment findPaymentStateByOrderId(Long orderId) {
        String uri = this.paymentsApiEndpoint + "/paytments/" + orderId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PaymentDTO> response = restTemplate.getForEntity(uri, PaymentDTO.class);
        PaymentDTO result = response.getBody();
        return Payment.create(result.getStatus(), result.getAmount());
    }
}
