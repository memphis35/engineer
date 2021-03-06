-- delete from engineer.public.task;
-- delete from engineer.public.users;
-- delete from engineer.public.department;

insert into engineer.public.department
values (1, 'Конструкторский центр'),
       (2, 'ДомнаРемонт'),
       (3, 'Северсталь-Проект'),
       (4, 'ВНИИМетМаш'),
       (5, 'УОЗ')
on conflict do nothing;

insert into engineer.public.users (id, email, password, name, department_id, role, expiration_date, created)
values (-1, 'asmirnov@engineer.com', '$2a$12$ed2d32KFaQgvGuT8SxeKY.4sLBJiNfcx1BVavEfUpvCBtZ40J2cWW', 'Александр Смирнов',
        4,
        'ROLE_ADMIN', '2022-12-31 23:59:59', now()),
       (-2, 'readonly@engineer.com', '$2a$12$ed2d32KFaQgvGuT8SxeKY.4sLBJiNfcx1BVavEfUpvCBtZ40J2cWW', 'READ_ONLY',
        4,
        'ROLE_READ_ONLY', '2022-12-31 23:59:59', now()),
       (-3, 'user1@engineer.com', '$2a$12$ed2d32KFaQgvGuT8SxeKY.4sLBJiNfcx1BVavEfUpvCBtZ40J2cWW', 'USER_1',
        4,
        'ROLE_USER', '2022-12-31 23:59:59', now()),
       (-4, 'user2@engineer.com', '$2a$12$ed2d32KFaQgvGuT8SxeKY.4sLBJiNfcx1BVavEfUpvCBtZ40J2cWW', 'USER_2',
        4,
        'ROLE_USER', '2022-12-31 23:59:59', now())
on conflict do nothing;

insert into engineer.public.task (id, registration_number, title, customer_id, expiration_date,comment)
values (1, 5, 'Реконструкция верхних ножниц АПР-4', -1, now() + interval '13 day', null),
       (2, 7, 'Замена гидропривода ленточного захлестывателя', -1, now() - interval '5 day', null),
       (3, 10, 'Ремонт хранилища маточного раствора', -1, now() + interval '29 day', null),
       (4, 15, 'Реконструкция воронки центрального питателя, АГЦ-1', -1, now() + interval '50 day', null),
       (5, 27, 'Расширение конвейера №3, АГЦ-2', -1, now() + interval '2 day', null)
on conflict do nothing;