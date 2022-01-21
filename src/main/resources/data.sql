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
        'ROLE_ADMIN', '2022-12-31 23:59:59', now())
on conflict do nothing;

insert into engineer.public.task (id, registration_number, title, customer_id, comment)
values (1, 5, 'Реконструкция верхних ножниц АПР-4', -1, null),
       (2, 7, 'Замена гидропривода ленточного захлестывателя', -1, null),
       (3, 10, 'Ремонт хранилища маточного раствора', -1, null),
       (4, 15, 'Реконструкция воронки центрального питателя, АГЦ-1', -1, null),
       (5, 27, 'Расширение конвейера №3, АГЦ-2', -1, null)
on conflict do nothing;