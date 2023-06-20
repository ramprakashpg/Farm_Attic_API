create table tbl_product_image(
                                  image_id uuid DEFAULT uuid_in(md5(random()::text || random()::text)::cstring),
                                  image_data BYTEA,
                                  product uuid references tbm_product(product_id),
                                  constraint tbl_product_image_pk primary key (image_id)
);
