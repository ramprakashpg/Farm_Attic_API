create table tbl_cart(
    cart_id uuid default uuid_in(md5(random()::text || random()::text)::cstring),
    user_id uuid references tbm_user(user_id),
    constraint tbl_cart_pk primary key (cart_id)
);
