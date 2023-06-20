create table tbl_order_details(
    order_id UUID DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    cart_id UUID references tbl_cart(cart_id),
    status varchar,
    CONSTRAINT tbl_order_details_pk PRIMARY KEY (order_id)
);
