create table if not exists engineer.public.department
(
    id   bigserial primary key,
    name varchar(256) not null unique
);

create table if not exists engineer.public.users
(
    id                         bigserial primary key,
    email                      varchar(256) not null unique,
    password                   varchar(256) not null,
    name                       varchar(256) not null,
    department_id              bigint references engineer.public.department (id),
    created                    timestamp    not null default now(),
    role                       varchar(128) not null default 'ROLE_READ_ONLY',
    expiration_date            date,
    password_changed           timestamp,
    is_enabled                 bool                  default false,
    is_non_locked              bool                  default true,
    is_account_non_expired     bool                  default true,
    is_credentials_non_expired bool                  default true
);

create table if not exists engineer.public.task
(
    id                  bigserial primary key,
    registration_number int          not null,
    registration_date   date         not null default date(now()),
    title               varchar(256) not null,
    customer_id         bigint       not null,
    status              varchar(256) not null default 'CREATED',
    expiration_date     date,
    comment             varchar(256)
);

create table if not exists engineer.public.email_verification
(
    id BIGSERIAL PRIMARY KEY,
    email varchar(256) not null unique,
    uuid varchar(256) not null,
    expired timestamp not null default now() + interval '6 hour'
);