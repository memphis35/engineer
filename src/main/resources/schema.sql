drop table if exists engineer.public.users;
drop table if exists engineer.public.department;

create table if not exists engineer.public.department
(
    id   bigserial primary key,
    name varchar(256) not null unique
);

insert into engineer.public.department
values (1, 'Металлургический отдел'),
       (2, 'Крановый отдел'),
       (3, 'Электротехнический отдел'),
       (4, 'Технологический отдел'),
       (5, 'Отдел сервисного обслуживания');

create table engineer.public.users
(
    id                         bigserial primary key,
    email                      varchar(256) not null unique,
    password                   varchar(256) not null,
    name                       varchar(256) not null,
    position                   varchar(256) not null default 'none',
    department_id              bigint references engineer.public.department (id),
    created                    timestamp    not null default now(),
    role                       varchar(128) not null default 'ROLE_READ_ONLY',
    expiration_date            timestamp    not null,
    password_changed           timestamp    not null default now(),
    is_enabled                 bool                  default true,
    is_non_locked              bool                  default true,
    is_account_non_expired     bool                  default true,
    is_credentials_non_expired bool                  default true
);

insert into engineer.public.users (id, email, password, name, department_id, role, expiration_date)
values (1, 'asmirnov@engineer.com', '$2a$12$Poyi86C1NuAop4hu4KtZr.IXW.IhYjIWlmu.Y/Ych3Ay9T9deB/yS', 'Smirnov A.', 4,
        'ROLE_ADMIN', '2022-12-31 23:59:59');
