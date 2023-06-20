create table tbl_order_history(
    order_id uuid references tbl_order_details(order_id),
    product_id uuid references tbm_product(product_id),
    user_id uuid references tbl_user(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT tbl_order_history_pkey PRIMARY KEY (order_id,product_id)
)
