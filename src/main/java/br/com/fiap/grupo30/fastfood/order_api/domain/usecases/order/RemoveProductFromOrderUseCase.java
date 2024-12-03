package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product.ProductUseCase;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.exceptions.CantChangeOrderProductsAfterSubmitException;
import org.springframework.stereotype.Component;

@Component
public class RemoveProductFromOrderUseCase {

    public OrderDTO execute(
            OrderGateway orderGateway,
            ProductUseCase productUseCase,
            Long orderId,
            Long productId) {
        Order order = orderGateway.findById(orderId);
        Product product = productUseCase.findProductById(productId);

        if (order.getStatus() != OrderStatus.DRAFT) {
            throw new CantChangeOrderProductsAfterSubmitException();
        }

        order.removeProduct(product);

        return orderGateway.save(order).toDTO();
    }
}
