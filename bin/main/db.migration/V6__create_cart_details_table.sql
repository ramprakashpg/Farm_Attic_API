create table tbl_cart_details(
    cart_id UUID references tbl_cart(cart_id),
    product_id UUID references tbm_product(product_id),
    quantity int,
    price int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key (cart_id,product_id)
);