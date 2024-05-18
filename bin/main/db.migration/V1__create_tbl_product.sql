create table tbm_user(
    user_id UUID DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    email varchar(200),
    first_name varchar,
    last_name varchar,
    CONSTRAINT tbm_user_pk PRIMARY KEY (user_id)
);

create table tbm_product(
    product_id UUID DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
    product_name varchar(200),
    product_description varchar(200),
    price_per_unit int,
    quantity int,
    unit varchar(200),
    user_id UUID references tbm_user(user_id),
    CONSTRAINT tbm_product_pk PRIMARY KEY (product_id),
    expiry_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

