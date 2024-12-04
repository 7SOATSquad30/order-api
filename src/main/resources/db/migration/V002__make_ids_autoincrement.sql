ALTER TABLE tb_order DROP CONSTRAINT IF EXISTS tb_order_pkey;

CREATE SEQUENCE tb_order_id_seq START 1;

ALTER TABLE tb_order 
    ALTER COLUMN id SET DEFAULT nextval('tb_order_id_seq');

ALTER TABLE tb_order 
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE tb_order 
    ADD PRIMARY KEY (id);



ALTER TABLE tb_order_item DROP CONSTRAINT IF EXISTS tb_order_item_pkey;

CREATE SEQUENCE tb_order_item_id_seq START 1;

ALTER TABLE tb_order_item 
    ALTER COLUMN id SET DEFAULT nextval('tb_order_item_id_seq');

ALTER TABLE tb_order_item 
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE tb_order_item 
    ADD PRIMARY KEY (id);