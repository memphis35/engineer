drop table if exists engineer.public.users_authority;
drop table if exists engineer.public.users;
drop table if exists engineer.public.department;
drop table if exists engineer.public.authority;

create table engineer.public.department
(
    id   bigserial primary key,
    name varchar(256) not null unique
);

create table engineer.public.users
(
    id                         bigserial primary key,
    email                      varchar(256) not null unique,
    password                   varchar(256) not null,
    name                       varchar(256) not null,
    position                   varchar(256) not null default 'none',
    department_id              bigint references engineer.public.department (id),
    created                    timestamp    not null default now(),
    expiration_date            timestamp    not null,
    password_changed           timestamp    not null default now(),
    is_enabled                 bool                  default true,
    is_non_locked              bool                  default true,
    is_account_non_expired     bool                  default true,
    is_credentials_non_expired bool                  default true
);

create table engineer.public.authority
(
    id        bigserial primary key,
    authority varchar(256) not null
);

create table engineer.public.users_authority
(
    id           bigserial primary key,
    user_id      bigint references engineer.public.users (id),
    authority_id bigint references engineer.public.authority (id)
);
