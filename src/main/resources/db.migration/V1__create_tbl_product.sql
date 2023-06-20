create table tbl_user(
    user_id UUID PRIMARY KEY DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    email varchar(200),
    address varchar,
    contact int
);

create table tbm_product(
    product_id serial primary key ,
    product_name varchar(200),
    product_description varchar(200),
    price int,
    quantity int,
    "user" uuid references tbl_user(user_id)
);

