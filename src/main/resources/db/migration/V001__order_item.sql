create table tb_order_item (
    total_price float(53) not null check (total_price>=0),
    id bigint not null,
    order_id bigint not null,
    product_id bigint not null,
    quantity bigint not null check (quantity>=1),
    primary key (id)
);