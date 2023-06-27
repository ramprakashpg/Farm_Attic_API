create table tbl_order_details(
    order_id UUID DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    user_id uuid references tbm_user(user_id),
    status varchar,
    CONSTRAINT tbl_order_details_pk PRIMARY KEY (order_id)
);
