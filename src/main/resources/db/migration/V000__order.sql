create table tb_order (
    created_at TIMESTAMP WITHOUT TIME ZONE,
    customer_id bigint,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    id bigint  not null,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    status varchar(255) check (status in ('DRAFT','SUBMITTED','PREPARING','READY','DELIVERED','CANCELED')),
    primary key (id)
);