create table tbl_product_image(
                                  image_id serial primary key ,
                                  image_data BYTEA,
                                  product int references tbm_product(product_id)
);