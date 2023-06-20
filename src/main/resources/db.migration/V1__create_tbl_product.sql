create table tbl_user(
    user_id UUID DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    email varchar(200),
    address varchar,
    contact int,
    CONSTRAINT tbl_user_pk PRIMARY KEY (user_id)
);

create table tbm_product(
    product_id UUID DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    product_name varchar(200),
    product_description varchar(200),
    price int,
    quantity int,
    user_id UUID references tbl_user(user_id),
    CONSTRAINT tbm_product_pk PRIMARY KEY (product_id)
);

