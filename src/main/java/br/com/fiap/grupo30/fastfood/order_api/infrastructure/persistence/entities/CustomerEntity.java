package br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.order_api.domain.valueobjects.CPF;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "tb_customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders = new ArrayList<>();

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant deletedAt;

    public CustomerEntity(Long customerId, String name, String cpf, String email) {
        this.id = customerId;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    @PreRemove
    public void preRemove() {
        deletedAt = Instant.now();
    }

    public Customer toDomainEntity() {
        return new Customer(id, name, new CPF(cpf), email);
    }
}
