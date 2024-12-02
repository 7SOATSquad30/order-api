package br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.repositories;

import br.com.fiap.grupo30.fastfood.order_api.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(
            "SELECT obj FROM OrderEntity obj "
                    + "WHERE obj.status IN ('READY', 'PREPARING', 'SUBMITTED') "
                    + "ORDER BY CASE obj.status "
                    + "WHEN 'READY' THEN 1 "
                    + "WHEN 'PREPARING' THEN 2 "
                    + "WHEN 'SUBMITTED' THEN 3 "
                    + "END ASC, obj.createdAt ASC")
    List<OrderEntity> findOrdersWithSpecificStatuses();

    @Query(
            "SELECT obj FROM OrderEntity obj "
                    + "WHERE (:status IS NULL OR obj.status = :status) "
                    + "ORDER BY obj.createdAt ASC")
    List<OrderEntity> findOrdersByStatus(OrderStatus status);
}
