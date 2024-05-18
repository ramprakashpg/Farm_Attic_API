create table tbm_user_details(
    user_id uuid references tbm_user(user_id),
    postal_address varchar(200),
    contact int,
    zip_code int,
    constraint tbl_user_details_pk primary key (user_id)
)
