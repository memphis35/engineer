insert into engineer.public.authority
values (1, 'Наблюдатель'),
       (2, 'Специалист отдела'),
       (3, 'Менеджер отдела'),
       (4, 'Администратор'),
       (5, 'Superuser');

insert into engineer.public.department
values (1, 'Металлургический отдел'),
       (2, 'Крановый отдел'),
       (3, 'Электротехнический отдел'),
       (4, 'Технологический отдел'),
       (5, 'Отдел сервисного обслуживания');

insert into engineer.public.users (id, email, password, name, department_id, expiration_date)
values (1, 'asmirnov@engineer.com', '$2a$04$vJhnEi8i20u5u3jTKd2hhetwLU0c23k.9n/9vEyWC367UHq86Q7Ny', 'Smirnov A.', 4,
        '2021-12-31 12:00:00');

insert into engineer.public.users_authority (user_id, authority_id)
values (1, 1),
       (1, 2),
       (1, 3);