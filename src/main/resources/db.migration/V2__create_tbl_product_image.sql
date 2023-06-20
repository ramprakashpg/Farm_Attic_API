create table tbl_product_image(
                                  image_id uuid primary key DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
                                  image_data BYTEA,
                                  product uuid references tbm_product(product_id)
);
