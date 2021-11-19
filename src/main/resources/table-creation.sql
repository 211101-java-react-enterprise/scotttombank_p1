drop table if exists accounts;
drop table if exists app_users;

create table app_users (
    user_id varchar check (user_id <> ''),
    first_name varchar(25) not null check (first_name <> ''),
    last_name varchar(25) not null check (last_name <> ''),
    email varchar(255) unique not null check (email <> ''),
    password varchar(255) not null check (password <> ''),

    constraint app_users_pk
    primary key (user_id)
);

create table accounts (
    acct_id varchar check (acct_id <> ''),
    balance numeric(8, 2),
    holder_id varchar not null check (holder_id <> '')

    constraint accounts_pk
    primary key (acct_id),

    constraint acct_holder_fk
    foreign key (holder_id)
    references app_users
);
