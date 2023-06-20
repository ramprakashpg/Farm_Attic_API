create table tbl_user(
    user_id UUID PRIMARY KEY DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    email varchar(200),
    address varchar,
    contact int
);

create table tbm_product(
    product_id UUID PRIMARY KEY DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    product_name varchar(200),
    product_description varchar(200),
    price int,
    quantity int,
    user_id UUID references tbl_user(user_id)
);

